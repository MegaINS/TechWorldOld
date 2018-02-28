package ru.megains.techworld.client


import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11
import ru.megains.techworld.client.render._
import ru.megains.techworld.client.render.font.FontRender
import ru.megains.techworld.client.render.gui.{GuiInGameMenu, GuiManager}
import ru.megains.techworld.client.render.item.RenderItem
import ru.megains.techworld.client.render.periphery.{Keyboard, Mouse, Window}
import ru.megains.techworld.client.render.texture.TextureManager
import ru.megains.techworld.client.render.world.{RenderChunk, WorldRenderer}
import ru.megains.techworld.common.EnumActionResult.EnumActionResult
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.entity.item.EntityItem
import ru.megains.techworld.common.entity.mob.EntityCube
import ru.megains.techworld.common.entity.player.EntityPlayerSP
import ru.megains.techworld.common.item.ItemBlock
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.utils.{Logger, Timer, Vec3f}
import ru.megains.techworld.common.world.{AnvilSaveFormat, World}
import ru.megains.techworld.common.{EnumActionResult, PlayerControllerMP, RayTraceResult}
import ru.megains.techworld.register.{Bootstrap, GameRegister}

import scala.reflect.io.Directory
import scala.util.Random



class TechWorldClient(clientDir: Directory) extends Logger[TechWorldClient]{

    var frames: Int = 0
    var lastFrames: Int = 0
    val MB: Double = 1024 * 1024
    val TARGET_FPS: Float = 60
    var tick: Int = 0
    var camera: Camera = _
    var cameraInc: Vec3f = _
    var rightClickDelayTimer = 0


    val timer: Timer = new Timer(20)
    val window: Window = new Window()
    var running = true
    var world:World = _
    var worldRenderer: WorldRenderer = _
    var renderer:Renderer  = _
    var textureManager:TextureManager  = _
    var player: EntityPlayerSP = _
    var guiManager: GuiManager = _
    var fontRender: FontRender = _
    var objectMouseOver: RayTraceResult = _
    var itemRender: RenderItem = _
    var blockSelectPosition: BlockState = _
    var playerController: PlayerControllerMP = _
   val saveLoader = new AnvilSaveFormat(clientDir)
    def startGame(): Unit = {


        log.info("Start Game")
        log.info("TechWorld v0.1.2")
        try {
            log.info("Display creating...")
            window.create()
            Mouse.init(window, this)
            Keyboard.init(window, this)
            GL11.glClearColor(0.5f, 0.6f, 0.7f, 0.0F)
            log.info("Display create successful")
        } catch {
            case e: RuntimeException =>
                e.printStackTrace()
                System.exit(-1000)
        }


        Bootstrap.init()

       // saveLoader = new AnvilSaveFormat(Path(clientDir + "saves").toDirectory)

        log.info("Renderer creating...")
        renderer = new Renderer(this)

        log.info("Camera creating...")
        camera = new Camera
        cameraInc = new Vec3f()
        log.info("GuiManager creating...")
        guiManager = new GuiManager(this)

        log.info("TextureManager creating...")
        textureManager = new TextureManager
        log.info("FontRender creating...")
        fontRender = new FontRender(this)

        renderer.init(textureManager)
        log.info("TextureManager loadTexture...")
        textureManager.loadTexture(TextureManager.locationBlockTexture, textureManager.textureMapBlock)
        GameRegister.tileEntityData.idRender.values.foreach(_.init())

        log.info("RenderItem creating...")
        itemRender = new RenderItem(this)


        log.info("GuiManager init...")
        guiManager.init()

        world = new World(saveLoader.getSaveLoader("world"))
        worldRenderer = new WorldRenderer(world, textureManager)
        renderer.worldRenderer = worldRenderer
        player = new EntityPlayerSP(this, world/*, connection*/)
        playerController = new PlayerControllerMP(this)
        world.spawnEntityInWorld(player)
       // guiManager.setGuiScreen(new GuiPlayerSelect())
        camera.setPosition(player.posX toFloat, player.posY + player.levelView toFloat, player.posZ toFloat)
        camera.setRotation(player.rotationPitch, player.rotationYaw, 0)



    }

    def run()={
        try {
            startGame()
        } catch {
            case e: Exception => log.fatal(e.printStackTrace())
                running = false

        }

        var lastTime: Long = System.currentTimeMillis
        timer.init()
        try {
            while (running) {


                runGameLoop()
                //  sync()
                while (System.currentTimeMillis >= lastTime + 1000L) {
                    log.info(s"$frames fps, $tick tick, ${RenderChunk.chunkRender / (if (frames == 0) 1 else frames)} chunkRender, ${RenderChunk.chunkUpdate} chunkUpdate")

                    RenderChunk.chunkRender = 0
                    RenderChunk.chunkUpdate = 0
                    lastTime += 1000L
                    lastFrames = frames
                    frames = 0
                    tick = 0
                    // printMemoryUsage()
                }
            }
        } catch {
            case e: Exception => log.fatal("Crash", e)
        } finally {
            cleanup()
        }
    }
    def runGameLoop(): Unit = {

        if (window.isClose) running = false


        timer.update()


//        scheduledTasks synchronized {
//            while (scheduledTasks.nonEmpty) scheduledTasks.dequeue()()
//        }


        for (_ <- 0 until timer.getTick) {
            update()
            tick += 1
        }


        render()
        frames += 1

        window.update()

    }

    private def update(): Unit = {
        Mouse.update(window)

        if (rightClickDelayTimer > 0) rightClickDelayTimer -= 1


        cameraInc.set(0, 0, 0)

        if (glfwGetKey(window.id, GLFW_KEY_W) == GLFW_PRESS) cameraInc.z = -1
        if (glfwGetKey(window.id, GLFW_KEY_S) == GLFW_PRESS) cameraInc.z = 1
        if (glfwGetKey(window.id, GLFW_KEY_A) == GLFW_PRESS) cameraInc.x = -1
        if (glfwGetKey(window.id, GLFW_KEY_D) == GLFW_PRESS) cameraInc.x = 1
        if (glfwGetKey(window.id, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) cameraInc.y = -1
        if (glfwGetKey(window.id, GLFW_KEY_SPACE) == GLFW_PRESS) cameraInc.y = 1

        if (glfwGetKey(window.id, GLFW_KEY_O) == GLFW_PRESS){
            val stack: ItemStack = player.inventory.getStackSelect
            if(stack ne null){

                val entityItem = new EntityItem()
                entityItem.setItem(new ItemStack(stack.item))
                entityItem.setPosition(player.posX +Random.nextInt(10)-5,player.posY+Random.nextInt(10),player.posZ+Random.nextInt(10)-5)

                world.spawnEntityInWorld(entityItem)

            }


        }
        if (glfwGetKey(window.id, GLFW_KEY_U) == GLFW_PRESS){
            val entityCube = new EntityCube()
            entityCube.setPosition(player.posX + Random.nextInt(50)-25,player.posY+Random.nextInt(50),player.posZ+Random.nextInt(50)-25)
            world.spawnEntityInWorld(entityCube)
        }


        guiManager.tick()



        if (world ne null) {
            world.update()
            if (!guiManager.isGuiScreen) player.turn(Mouse.getDX, Mouse.getDY)

            player.update(cameraInc.x, cameraInc.y, cameraInc.z)
            camera.setPosition(player.posX toFloat, player.posY + player.levelView toFloat, player.posZ toFloat)
            camera.setRotation(player.rotationPitch, player.rotationYaw, 0)
            player.inventory.changeStackSelect(Mouse.getDWheel * -1)


            objectMouseOver = player.rayTrace(20, 0.1f)





            if (objectMouseOver != null) {
                worldRenderer.updateBlockMouseOver(objectMouseOver.blockPos, world.getBlock(objectMouseOver.blockPos))

                val stack: ItemStack = player.inventory.getStackSelect
                if(stack ne null){
                    blockSelectPosition = stack.item match {
                        case itemBlock:ItemBlock =>itemBlock.block.getSelectPosition(world,player, objectMouseOver)
                        case _ => null
                    }
                } else  blockSelectPosition = null



            }else blockSelectPosition = null



            if (blockSelectPosition != null) {
                worldRenderer.updateBlockSelect(blockSelectPosition)
            }

        }


    }


    def runTickMouse(button: Int, buttonState: Boolean): Unit = {


        if (button == 1 && buttonState) {
            rightClickMouse()
        }



        if (button == 0 && buttonState && objectMouseOver != null) {

           // playerController.clickBlock(objectMouseOver.blockPos, BlockDirection.DOWN)
            world.setAirBlock(objectMouseOver.blockPos)
        }

    }
    def rightClickMouse() {
        if (!playerController.isHittingBlock) {
            rightClickDelayTimer = 4
            val itemstack: ItemStack = player.getHeldItem
//            if (blockSelectPosition == null) {
//                // log.warn("Null returned as \'hitResult\', this shouldn\'t happen!")
//            } else {
                objectMouseOver.typeOfHit match {
                    case RayTraceResult.Type.ENTITY =>
                    // if (playerController.interactWithEntity(player, objectMouseOver.entityHit, objectMouseOver, player.getHeldItem(enumhand), enumhand) eq EnumActionResult.SUCCESS) return
                    // if (playerController.interactWithEntity(player, objectMouseOver.entityHit, player.getHeldItem(enumhand), enumhand) eq EnumActionResult.SUCCESS) return

                    case RayTraceResult.Type.BLOCK =>
                        val blockpos: BlockPos = objectMouseOver.blockPos
                        if (!world.isAirBlock(blockpos)) {
                           // val i: Int = if (itemstack != null) itemstack.stackSize
                            //else 0

                            val enumactionresult: EnumActionResult = playerController.processRightClickBlock(player, world, itemstack, blockpos, objectMouseOver.sideHit, objectMouseOver.hitVec)
                            if (enumactionresult eq EnumActionResult.SUCCESS) {
                                //   player.swingArm()
                                if (itemstack != null) if (itemstack.stackSize == 0) player.setHeldItem(null)
                                //    else if (itemstack.stackSize != i || playerController.isInCreativeMode) entityRenderer.itemRenderer.resetEquippedProgress()
                                return
                            }
                        }
                    case _ =>
               // }
            }
            val itemstack1: ItemStack = player.getHeldItem
            if (itemstack1 != null && (playerController.processRightClick(player, world, itemstack1) eq EnumActionResult.SUCCESS)) {
                //   entityRenderer.itemRenderer.resetEquippedProgress()
                return
            }

        }
    }


    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            key match {
                case GLFW_KEY_E => player.openInventory()
                case GLFW_KEY_ESCAPE => guiManager.setGuiScreen(new GuiInGameMenu())
                case GLFW_KEY_R => worldRenderer.reRenderWorld()
                case GLFW_KEY_N => grabMouseCursor()
                case GLFW_KEY_M => ungrabMouseCursor()
                case _ =>
            }
        }
    }



    def grabMouseCursor(): Unit = {
        Mouse.setGrabbed(true)
    }

    def ungrabMouseCursor(): Unit = {
        //TODO  Mouse.setCursorPosition(Display.getWidth / 2, Display.getHeight / 2)
        Mouse.setGrabbed(false)
    }

    private def render(): Unit = {

        log.debug("RENDER START")
        renderer.render(camera)
        log.debug("RENDER STOP")
    }

    private def cleanup(): Unit = {
        log.info("Game stopped...")
        running = false
        window.destroy()
        if (world != null) world.save()
        if (renderer != null) renderer.cleanup()
        if (guiManager ne null) guiManager.cleanup()
        if (worldRenderer != null) worldRenderer.cleanUp()

    }
}
object TechWorldClient{
    var techWorld:TechWorldClient =_
}

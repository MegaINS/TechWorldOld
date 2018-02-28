package ru.megains.techworld.common.entity.player

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.gui.GuiPlayerInventory
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.container.{Container, ContainerPlayerInventory}
import ru.megains.techworld.common.entity.EntityLivingBase
import ru.megains.techworld.common.inventory.InventoryPlayer
import ru.megains.techworld.common.item.{ItemBlock, ItemType}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.tileentity.ATileEntityInventory
import ru.megains.techworld.common.world.World
import ru.megains.techworld.register.{Blocks, GameRegister}

import scala.util.Random


class EntityPlayer() extends EntityLivingBase() {



    var openContainer: Container = _
    val inventory = new InventoryPlayer(this)
    val inventoryContainer: Container = new ContainerPlayerInventory(inventory)
    var name: String = _





    setPosition(10, 10, 0)
    setSize( 1.8f*4, 0.6f*4, 1.6f*4)


    GameRegister.getItems.filter {
        case block: ItemBlock => block.block != Blocks.air
        case _ => true
    }.foreach{ item =>
            item.itemType match {
                case ItemType.single =>
                    for (_<-0 to Random.nextInt(4)) inventory.addItemStackToInventory(new ItemStack(item))
                case ItemType.mass =>
                    inventory.addItemStackToInventory(new ItemStack(item,1245+Random.nextInt(500)))
                case ItemType.base =>
                    inventory.addItemStackToInventory(new ItemStack(item,Random.nextInt(50)))
            }

    }

    def setName(nameIn:String): Unit ={
        name = nameIn
    }

    def turn(xo: Double, yo: Double) {
        rotationYaw += xo.toFloat * 0.15f
        rotationPitch -= yo.toFloat * 0.15f
        if (rotationPitch < -90.0F) {
            rotationPitch = -90.0F
        }
        if (rotationPitch > 90.0F) {
            rotationPitch = 90.0F
        }
        if (rotationYaw > 360.0F) {
            rotationYaw -= 360.0F

        }
        if (rotationYaw < 0.0F) {
            rotationYaw += 360.0F

        }
    }

    def openInventory(): Unit = {
        openContainer = inventoryContainer
        TechWorldClient.techWorld.guiManager.setGuiScreen(new GuiPlayerInventory(this))
    }
    def openGui(world: World, pos: BlockPos): Unit = {
        val tileEntity = world.getTileEntity(pos)
        tileEntity match {
            case inv:ATileEntityInventory =>
                val gui = inv.getGui(this)
                openContainer = gui.inventorySlots
                TechWorldClient.techWorld.guiManager.setGuiScreen(gui)
            case _=>
        }

    }
    def update(xo: Float, yo: Float, zo: Float) {





        // lastTickPosition.set(position)
        //   prevPosition.set(position)
        if (yo > 0) {

        }
        //  ydRot = yRot
        //xdRot = xRot
        // rotation.set(xRot, yRot, 0)
        motionY = yo / 5

        moveFlying(xo, zo, if (onGround) 0.04f else 0.02f)
        move(motionX, motionY, motionZ)
        motionX *= 0.8f
        if (motionY > 0.0f) {
            motionY *= 0.90f
        }
        else {
            motionY *= 0.98f
        }
        motionZ *= 0.8f
        motionY -= 0.04f
        if (onGround) {
            motionX *= 0.9f
            motionZ *= 0.9f
        }
        pickUpItem()
    }

    override def update(): Unit = {
        rotationYaw match {
            case y if y > 315 || y <45 => side = BlockDirection.NORTH
            case y if y <135 => side = BlockDirection.EAST
            case y if y <225 => side = BlockDirection.SOUTH
            case y if y <315 => side = BlockDirection.WEST
            case _ => side = BlockDirection.UP
        }

    }


    def pickUpItem(): Unit ={

        world.getEntityItemsNearEntity(this, 4).foreach{
            entity =>
               if(inventory.addItemStackToInventory(entity.itemStack)) {
                   world.deleteEntityInWorld(entity)
               }
        }
    }


    override def getItemStackFromSlot: ItemStack = inventory.getStackSelect

    def setItemStackToSlot(stack: ItemStack) {

        // playEquipSound(stack)
        inventory.mainInventory(inventory.stackSelect) = stack


    }

//    override def readEntityFromNBT(compound: NBTCompound): Unit = {
//
//        //inventory.readFromNBT(compound)
//
//        inventory.addItemStackToInventory(new ItemStack(Blocks.micro1))
//        inventory.addItemStackToInventory(new ItemStack(Blocks.micro2))
//        inventory.addItemStackToInventory(new ItemStack(Blocks.micro3))
//        inventory.addItemStackToInventory(new ItemStack(Blocks.micro4))
//    }

//    override def writeEntityToNBT(compound: NBTCompound): Unit = {
//        inventory.writeToNBT(compound)
//    }

}

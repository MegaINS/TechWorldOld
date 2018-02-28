package ru.megains.techworld.register

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.block.{RenderBlockDoor, RenderBlockVoid}
import ru.megains.techworld.client.render.tileentity.RenderChest
import ru.megains.techworld.common.block._
import ru.megains.techworld.common.entity.item.EntityItem
import ru.megains.techworld.common.entity.mob.EntityCube
import ru.megains.techworld.common.item.{Item, ItemArmor, ItemRock}
import ru.megains.techworld.common.tileentity.TileEntityChest
import ru.megains.techworld.common.utils.Logger

object Bootstrap extends Logger[TechWorldClient] {

    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks()
            log.info("Items init...")
            initItems()
            log.info("TileEntity init...")
            initTileEntity()
            log.info("Entity init...")
            initEntity()
        }

    }



    def initBlocks(): Unit = {

        GameRegister.registerBlock(0, new BlockAir())
        GameRegister.registerBlock(1, new BlockWG("stone"))
        GameRegister.registerBlock(2, new Block("dirt"))
        GameRegister.registerBlock(3, new BlockTest("test0",0))
        GameRegister.registerBlock(4, new BlockTest("test1",1))
        GameRegister.registerBlock(5, new BlockTest("test2",2))
        GameRegister.registerBlock(6, new BlockTest("test3",3))
        GameRegister.registerBlock(7, new BlockTest("test4",4))
        GameRegister.registerBlock(8, new BlockGlass("glass"))
        GameRegister.registerBlock(9, new BlockGrass("grass"))
        GameRegister.registerBlock(10, new BlockChest("tileEntityTest"))
        GameRegister.registerBlock(11, new BlockDoor("door"))

        GameRegister.registerBlockRender(Blocks.door,RenderBlockDoor)
        GameRegister.registerBlockRender(Blocks.chect,RenderBlockVoid)

    }

    def initItems(): Unit = {
        GameRegister.registerItem(1000, new Item("stick"))
        GameRegister.registerItem(1001, new ItemRock("ironRock"))
        GameRegister.registerItem(1002, new ItemArmor("helmet"))
    }

    def initTileEntity(): Unit = {



        GameRegister.registerTileEntity(0,classOf[TileEntityChest])
        GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
    }

    def initEntity(): Unit = {



        GameRegister.registerEntity(0,classOf[EntityItem])
        GameRegister.registerEntity(1,classOf[EntityCube])
       // GameRegister.registerEntityRender(classOf[TileEntityChest],RenderChest)
    }
}

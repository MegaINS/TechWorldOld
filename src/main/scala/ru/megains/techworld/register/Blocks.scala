package ru.megains.techworld.register

import ru.megains.techworld.common.block._
import ru.megains.techworld.common.item.Item

object Blocks {


    lazy val air: Block = GameRegister.getBlockByName("air")
    lazy val dirt: Block = GameRegister.getBlockByName("dirt")
    lazy val stone: Block = GameRegister.getBlockByName("stone")
    lazy val glass: Block = GameRegister.getBlockByName("glass")
    lazy val grass: Block = GameRegister.getBlockByName("grass")
    lazy val door:Block = GameRegister.getBlockByName("door")


    lazy val chect: Block = GameRegister.getBlockByName("tileEntityTest")



    def getIdByBlock(block: Block): Int = {
        GameRegister.getIdByBlock(block)
    }

    def getBlockFromItem(item: Item): Block = {
        GameRegister.getBlockFromItem(item)
    }

    def getBlockById(id: Int): Block = {
        val block: Block = GameRegister.getBlockById(id)
        if (block == null) {
            Blocks.air
        } else {
            block
        }
    }



}

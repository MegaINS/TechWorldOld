package ru.megains.techworld.common.world.chunk.data

import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.physics.AxisAlignedBB
import ru.megains.techworld.register.Blocks

import scala.collection.mutable

class BlockCell(chunk:AxisAlignedBB) {



    val blocks = new Array[BlockState](64)
    var blocksVal = 0

    def getBlock(x: Int, y: Int, z: Int): BlockState = {
       blocks(getIndex(x,y,z)) match {
           case null => Blocks.air.blockState
           case blockState => blockState
       }
    }

    def setBlock(x: Int, y: Int,z: Int, value: BlockState): Unit = {
        if(value.block == Blocks.air){
            blocks(getIndex(x,y,z)) = null
            blocksVal -=1
        }else{
            blocks(getIndex(x,y,z)) = value
            blocksVal +=1
        }
    }

    def getIndex(x: Int, y: Int, z: Int):Int = x<<4 | y <<2 | z


    def addBlocks(set: mutable.HashSet[BlockState]): Unit = {
        blocks.filter(blockState =>

            if(blockState ne null) {
                val pos = blockState.pos
                chunk.checkCollision(new AxisAlignedBB(pos.x,pos.y,pos.z,pos.x+1,pos.y+1,pos.z+1))
            }
            else false
        ).foreach(set += _)
    }
}

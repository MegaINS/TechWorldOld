package ru.megains.techworld.common.world.chunk.data

import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.block.{Block, BlockState}
import ru.megains.techworld.register.Blocks

import scala.collection.mutable

class BlockStorage(position: ChunkPosition) {



    var blocksId: Array[Short] = new Array[Short](4096)
    val containers = new mutable.HashMap[Short,BlockCell]()


    def setBlock(x: Int, y: Int, z: Int, value: BlockState): Unit = {

        val index = getIndex(x>>2,y>>2,z>>2)
        containers.getOrElseUpdate(index,defaultValue ={
            blocksId(index) = -1
            new BlockCell(position.aabb)
        }).setBlock(x&3, y&3, z&3,value)
        if(containers(index).blocksVal==0){
            containers -= index
            blocksId(index) = 0
        }
    }


    def setWorldGet(x: Int, y: Int, z: Int, block: Block): Unit = {
        val index = getIndex(x,y,z)
        blocksId(index) = Blocks.getIdByBlock(block) toShort
    }

    def getIndex(x: Int, y: Int, z: Int): Short = x << 8 | y << 4 | z toShort

    def get(x: Int, y: Int, z: Int): BlockState = {

        val x1 = x>>2
        val y1 = y>>2
        val z1 = z>>2

        val index = getIndex(x1,y1,z1)
        val id =  blocksId(index)
        id match {
            case -1 => containers(index).getBlock(x&3, y&3, z&3)
            case 0 => Blocks.air.blockState
            case _ => new BlockState(Blocks.getBlockById(id),new BlockPos(position.minX +(x1<<2),position.minY  +(y1<<2),position.minZ +(z1<<2)),BlockDirection.EAST)
        }
    }

    def getBlocks: mutable.HashSet[BlockState] ={
        val blocks = new  mutable.HashSet[BlockState]()

        for (x <- 0 until 16;
             y <- 0 until 16;
             z <- 0 until 16) {

            val index = getIndex(x,y,z)
            val id =  blocksId(index)
            id match {
                case -1 => containers(index).addBlocks(blocks)
                case 0 =>
                case _ => blocks+= new BlockState(Blocks.getBlockById(id),new BlockPos(position.minX +(x<<2),position.minY  +(y<<2),position.minZ +(z<<2)))
            }

        }
        blocks
    }
}

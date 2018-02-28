package ru.megains.techworld.common.block

import ru.megains.techworld.common.block.blockdata.BlockSize
import ru.megains.techworld.common.physics.AxisAlignedBB

class BlockTest(name:String,size:Int) extends Block(name){



    override val blockBody: AxisAlignedBB =  BlockTest.boundingBoxs(size)
    override val blockSize: BlockSize = BlockTest.blockSizes(size)

    override  def getSelectedBoundingBox(blockState: BlockState): AxisAlignedBB =BlockTest.boundingBoxs(size).rotate(blockState.blockDirection)
}
object BlockTest{
    val blockSizes:Array[BlockSize] =  Array(BlockSize(1,2,1),BlockSize(2,1,1),BlockSize(8,2,4),BlockSize(2,2,2),BlockSize(2,6,2))
    val boundingBoxs:Array[AxisAlignedBB] =  Array(new AxisAlignedBB(0,0,0,1,2,1),new AxisAlignedBB(0,0,0,2,1,1),new AxisAlignedBB(0,0,0,8,2,4),new AxisAlignedBB(0,0,0,2,2,2),new AxisAlignedBB(0,0,0,2,6,2))

}
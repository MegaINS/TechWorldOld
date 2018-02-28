package ru.megains.techworld.common.block

import ru.megains.techworld.common.block.blockdata.BlockSize
import ru.megains.techworld.common.physics.AxisAlignedBB

class BlockWG(name: String) extends Block(name){


    override val blockBody:AxisAlignedBB =  new AxisAlignedBB(0,0,0,4,4,4)
    override val blockSize: BlockSize = Block.baseBlockSize
    override def getSelectedBoundingBox(blockState: BlockState): AxisAlignedBB = blockBody.rotate(blockState.blockDirection)
}

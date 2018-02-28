package ru.megains.techworld.common.block
import ru.megains.techworld.common.block.blockdata.BlockSize
import ru.megains.techworld.common.physics.AxisAlignedBB

class BlockDoor(name:String) extends Block(name){

    override val blockBody: AxisAlignedBB = new AxisAlignedBB(0,0,0,1,8,4)
    override val blockSize: BlockSize = BlockSize(1,8,4)
}

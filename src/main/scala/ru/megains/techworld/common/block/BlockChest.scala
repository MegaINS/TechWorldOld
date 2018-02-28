package ru.megains.techworld.common.block
import ru.megains.techworld.common.block.blockdata.BlockSize
import ru.megains.techworld.common.physics.AxisAlignedBB
import ru.megains.techworld.common.tileentity.{TileEntity, TileEntityChest}
import ru.megains.techworld.common.world.World

class BlockChest(name:String) extends BlockContainer(name){

    override val blockBody: AxisAlignedBB =  new AxisAlignedBB(0,0,0,4,4,6)
    override val blockSize: BlockSize = new BlockSize(4,4,6)

    override  def getSelectedBoundingBox(blockState: BlockState): AxisAlignedBB = blockBody.rotate(blockState.blockDirection)


    override def createNewTileEntity(worldIn: World, blockState: BlockState): TileEntity = new TileEntityChest(blockState.pos,worldIn)
}

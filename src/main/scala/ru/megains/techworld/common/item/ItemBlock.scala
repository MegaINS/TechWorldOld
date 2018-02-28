package ru.megains.techworld.common.item

import ru.megains.techworld.client.render.texture.TTextureRegister
import ru.megains.techworld.common.EnumActionResult
import ru.megains.techworld.common.EnumActionResult.EnumActionResult
import ru.megains.techworld.common.block.blockdata.BlockDirection
import ru.megains.techworld.common.block.{Block, BlockState}
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.world.World

class ItemBlock(val block: Block) extends Item(block.name) {


    override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {


         //  val block: Block = worldIn.getBlock(pos)
       //  if (!block.isReplaceable(worldIn, pos)) pos = pos.offset(facing)
     //   if (stack.stackSize != 0 && /* playerIn.canPlayerEdit(pos, facing, stack)*//* &&*/ worldIn.canBlockBePlaced(this.block, pos, false, facing, null, stack)) {
            //  val i: Int = getMetadata(stack.getMetadata)
            // val iblockstate1: IBlockState = block.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, i, playerIn)
            if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ)) {
                //   val soundtype: SoundType = block.getSoundType
                //  worldIn.playSound(playerIn, pos, soundtype.getPlaceSound, SoundCategory.BLOCKS, (soundtype.getVolume + 1.0F) / 2.0F, soundtype.getPitch * 0.8F)
                stack.stackSize -= 1
            }
            EnumActionResult.SUCCESS
      //  }
      //  else
       //     EnumActionResult.FAIL
    }

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    def placeBlockAt(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
        if (!world.setBlock(pos)) return false

        // setTileEntityNBT(world, player, pos, stack)
      //  block.onBlockPlacedBy(world, pos, player, stack)
        true
    }

//    def canPlaceBlockOnSide(worldIn: World, pos: BlockPos, side: BlockDirection, player: EntityPlayer, stack: ItemStack): Boolean = {
//        val block: Block = worldIn.getBlock(pos)
//        //  if ((block eq Blocks.SNOW_LAYER) && block.isReplaceable(worldIn, pos)) side = EnumFacing.UP
//        //  else if (!block.isReplaceable(worldIn, pos)) pos = pos.offset(side)
//        worldIn.canBlockBePlaced(this.block, pos, false, side, null, stack)
//        true
//    }
}

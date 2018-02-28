package ru.megains.techworld.common.block

import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.tileentity.TileEntityContainer
import ru.megains.techworld.common.world.World

abstract class BlockContainer(name:String) extends Block(name) with TileEntityContainer{

    override def isOpaqueCube: Boolean = false


   override def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, p_onBlockActivated_6:ItemStack, ponBlockActivated7:BlockDirection, p_onBlockActivated_8: Float, p_onBlockActivated_9: Float): Boolean = {
        if (world.getTileEntity(pos) == null) {
            false
        }else{
            player.openGui(world, pos)
            true
        }
   }


}

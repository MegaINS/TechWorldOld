package ru.megains.techworld.common.item.itemstack

import ru.megains.techworld.common.ActionResult
import ru.megains.techworld.common.EnumActionResult.EnumActionResult
import ru.megains.techworld.common.block.{Block, BlockState}
import ru.megains.techworld.common.block.blockdata.BlockDirection
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.item.{Item, ItemType}
import ru.megains.techworld.common.world.World


class ItemStack private(val item: Item,var stackSize:Int,var stackMass:Int) {


    def this(item: Item,sizeOrMass:Int) ={
        this(item,
            if(item.itemType == ItemType.base ) sizeOrMass else 1,
            if(item.itemType == ItemType.base ) item.mass * sizeOrMass else sizeOrMass)
    }




    def this(item: Item) = this(item,1,item.mass)



    def this(block: Block, size:Int) = this(Item.getItemFromBlock(block), size,Item.getItemFromBlock(block).mass * size )

    def this(block: Block) = this(block,1)


    def splitStack(size: Int): ItemStack = {

        item.itemType match {
            case ItemType.base | ItemType.single  =>
                stackSize -= size
                stackMass -= item.mass * size
                new ItemStack(item, size)
            case ItemType.mass =>
                stackMass -= item.mass * size
                new ItemStack(item, size)
        }
    }



    def onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
        // if (!worldIn.isRemote) return net.minecraftforge.common.ForgeHooks.onPlaceItemIntoWorld(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
        val enumactionresult: EnumActionResult = item.onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
        //  if (enumactionresult eq EnumActionResult.SUCCESS) playerIn.addStat(StatList.getObjectUseStats(this.item))
        enumactionresult
    }

    def useItemRightClick(worldIn: World, playerIn: EntityPlayer): ActionResult[ItemStack] = item.onItemRightClick(this, worldIn, playerIn)



}

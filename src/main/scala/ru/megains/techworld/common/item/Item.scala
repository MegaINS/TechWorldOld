package ru.megains.techworld.common.item

import ru.megains.techworld.client.render.texture.{TTextureRegister, TextureAtlas}
import ru.megains.techworld.common.EnumActionResult.EnumActionResult
import ru.megains.techworld.common.{ActionResult, EnumActionResult}
import ru.megains.techworld.common.block.{Block, BlockState}
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.item.ItemType.ItemType
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.world.World
import ru.megains.techworld.register.GameRegister


class Item(val name: String) {


    var maxStackSize: Int = 64
    var mass:Int = 1
    val itemType:ItemType = ItemType.base
    var aTexture: TextureAtlas = _

    def registerTexture(textureRegister: TTextureRegister): Unit = {
        aTexture = textureRegister.registerTexture(name)
    }

    def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos : BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

    def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

    def onItemRightClick(itemStackIn: ItemStack, worldIn: World, playerIn: EntityPlayer): ActionResult[ItemStack] = new ActionResult[ItemStack](EnumActionResult.PASS, itemStackIn)
}

object Item {


    def getItemById(id: Int): Item = GameRegister.getItemById(id)

    def getItemFromBlock(block: Block): Item = GameRegister.getItemFromBlock(block)

    def getIdFromItem(item: Item): Int = {
        GameRegister.getIdByItem(item)
    }


    def initItems(): Unit = {
        GameRegister.registerItem(1000, new Item("stick"))
    }

}

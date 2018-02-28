package ru.megains.techworld.common.entity

import ru.megains.techworld.common.item.itemstack.ItemStack

abstract class EntityLivingBase() extends Entity() {


    def setHeldItem(stack: ItemStack): Unit = setItemStackToSlot(stack)
//
//
    def getHeldItem: ItemStack = getItemStackFromSlot
//
//
    def getItemStackFromSlot: ItemStack
//
    def setItemStackToSlot(stack: ItemStack): Unit
}

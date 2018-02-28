package ru.megains.techworld.common.entity.item

import ru.megains.nbt.tag.NBTCompound
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.item.Item
import ru.megains.techworld.common.item.itemstack.ItemStack

class EntityItem() extends Entity() {

    setSize(0.25f, 0.25f, 0.25f)


    var itemStack: ItemStack = _

    def setItem(itemStackIn: ItemStack): Unit ={
        itemStack = itemStackIn
    }
    override def update(): Unit = {
        motionY -= 0.01f

        moveFlying(0, 0, if (onGround) 0.03f else 0.01f)
        move(motionX, motionY, motionZ)
    }

    override def writeEntityToNBT(compound: NBTCompound): Unit = {
        val inventory = compound.createCompound("itemStack")
        if (itemStack != null) {
            inventory.setValue("id", Item.getIdFromItem(itemStack.item))
            inventory.setValue("stackSize", itemStack.stackSize)
        } else {
            inventory.setValue("id", -1)
        }

    }

    override def readEntityFromNBT(compound: NBTCompound): Unit = {
        val inventory = compound.getCompound("itemStack")
        val id: Int = inventory.getInt("id")
        if (id != -1) {
            itemStack = new ItemStack(Item.getItemById(id), inventory.getInt("stackSize"))
        }
    }
}

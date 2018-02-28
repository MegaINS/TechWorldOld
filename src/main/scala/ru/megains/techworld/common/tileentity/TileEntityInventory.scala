package ru.megains.techworld.common.tileentity

import ru.megains.nbt.NBTType.EnumNBTCompound
import ru.megains.nbt.tag.NBTCompound
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.inventory.AInventory
import ru.megains.techworld.common.item.{Item, ItemType}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.world.World

abstract class TileEntityInventory(pos:BlockPos, world: World,slotSize:Int) extends TileEntity(pos, world) with AInventory with ATileEntityInventory{

    var slots:Array[ItemStack] = new Array[ItemStack](slotSize)



    override def getStackInSlot(index: Int): ItemStack = {
        slots(index)
    }


    override def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit = {
        slots(index) = itemStack
    }

    override def decrStackSize(index: Int, size: Int): ItemStack = {

        val stack = slots(index)
        var newStack: ItemStack = null
        if (stack ne null) {
            stack.item.itemType match {
                case ItemType.base | ItemType.single  =>
                    if (stack.stackSize <= size) {
                        newStack = stack
                        slots(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackSize < 1) {
                            slots(index) = null
                        }
                    }
                case ItemType.mass =>
                    if (stack.stackMass <= size) {
                        newStack = stack
                        slots(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackMass < 1) {
                            slots(index) = null
                        }
                    }
            }

        }
        newStack




//        size match {
//            case size:Int  =>
//                val stack = slots(index)
//                var newStack: ItemStack = null
//                if (stack ne null) {
//                    if (stack.stackSize <= size) {
//                        newStack = stack
//                        slots(index) = null
//                    } else {
//                        newStack = stack.splitStack(size)
//                        if (stack.stackSize < 1) {
//                            slots(index) = null
//                        }
//                    }
//                }
//                newStack
//            case size:Float =>
//                val stack = slots(index)
//                var newStack: ItemStack = null
//                if (stack ne null) {
//                    if (stack.stackMass <= size) {
//                        newStack = stack
//                        slots(index) = null
//                    } else {
//                        newStack = stack.splitStack(size)
//                        if (stack.stackMass < 0.01f) {
//                            slots(index) = null
//                        }
//                    }
//                }
//                newStack
//            case _ => null
//        }
    }

    override def writeToNBT(data: NBTCompound): Unit = {
        super.writeToNBT(data)
        val inventory = data.createList("slots", EnumNBTCompound)
        for (i <- slots.indices) {
            val compound = inventory.createCompound()
            val itemStack = slots(i)

            if (itemStack != null) {
                compound.setValue("id", Item.getIdFromItem(itemStack.item))
                compound.setValue("stackSize", itemStack.stackSize)
            } else {
                compound.setValue("id", -1)
            }
        }
    }

    override def readFromNBT(data: NBTCompound): Unit = {
        super.readFromNBT(data)
        val inventory = data.getList("slots")
        for (i <- slots.indices) {
            val compound = inventory.getCompound(i)
            val id: Int = compound.getInt("id")
            if (id != -1) {
                val itemStack = new ItemStack(Item.getItemById(id), compound.getInt("stackSize"))
                slots(i) = itemStack
            }
        }
    }
}

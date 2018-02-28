package ru.megains.techworld.common.tileentity

import ru.megains.nbt.tag.NBTCompound
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.world.World

class TileEntity(val pos:BlockPos, val world: World) {






    def update(world: World): Unit ={

    }

    def writeToNBT(data: NBTCompound): Unit = {

    }

    def readFromNBT(data: NBTCompound): Unit = {

    }
}

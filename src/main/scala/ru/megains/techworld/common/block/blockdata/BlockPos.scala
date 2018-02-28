package ru.megains.techworld.common.block.blockdata

import ru.megains.techworld.common.world.World

class BlockPos(val x:Int,val y:Int,val z:Int) {



    val isBase: Boolean = x%4== 0 && y%4== 0 && z%4== 0

    def sum(direction: BlockDirection) = new BlockPos(x + direction.x, y + direction.y, z + direction.z)

    def sum(inX: Int, inY: Int, inZ: Int) = new BlockPos(inX + x, inY + y, inZ + z)
    def isValid(world: World): Boolean =
        !(z < -world.width || y < -world.height || x < -world.length) &&
        !(z > world.width - 1 || y > world.height - 1 || x > world.length - 1)


    def local(): BlockPos = {
        new BlockPos(x&63,y&63,z&63)
    }
}

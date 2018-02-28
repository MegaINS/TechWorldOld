package ru.megains.techworld.common.block.blockdata

class BlockSidePos(val minX: Int,val  minY: Int,val  minZ: Int,val  maxX: Int,val  maxY: Int,val  maxZ: Int) {





    def sum(direction: BlockDirection) =
        BlockSidePos(
            minX + direction.x,
            minY + direction.y,
            minZ + direction.z,
            maxX + direction.x,
            maxY + direction.y,
            maxZ + direction.z
        )


//    def rotate(side: BlockDirection): BlockSidePos = {
//        var newMinX = minX
//        var newMinZ = minZ
//        var newMaxX = maxX
//        var newMaxZ = maxZ
//
//        side match {
//            case BlockDirection.WEST =>
//                newMaxX = minX + 1
//                newMaxZ = minZ + 1
//                newMinX = newMaxX - (maxX - minX)
//                newMinZ = newMaxZ - (maxZ - minZ)
//
//            case BlockDirection.SOUTH =>
//                newMaxX = minX + 1
//                newMinX = newMaxX - (maxZ - minZ)
//                newMaxZ = minZ + (maxX - minX)
//
//            case BlockDirection.WEST =>
//                newMaxZ = minZ + 1
//                newMinZ = newMaxZ - (maxX - minX)
//                newMaxX = minX + (maxZ - minZ)
//            case _ =>
//        }
//        new BlockSidePos(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ)
//    }

    def sum(x: Int, y: Int, z: Int) = new BlockSidePos(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)
}

object BlockSidePos{
    def apply(minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int): BlockSidePos = new BlockSidePos(minX,minY,minZ,maxX,maxY,maxZ)
}
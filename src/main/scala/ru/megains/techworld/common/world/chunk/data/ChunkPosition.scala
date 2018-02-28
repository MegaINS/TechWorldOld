package ru.megains.techworld.common.world.chunk.data

import ru.megains.techworld.common.physics.AxisAlignedBB
import ru.megains.techworld.common.world.chunk.Chunk

class ChunkPosition(val x:Int,val y:Int,val z:Int) {


    val minX: Int = x * Chunk.CHUNK_SIZE
    val minY: Int = y * Chunk.CHUNK_SIZE
    val minZ: Int = z * Chunk.CHUNK_SIZE
    val maxX: Int = minX + Chunk.CHUNK_SIZE -1
    val maxY: Int = minY + Chunk.CHUNK_SIZE -1
    val maxZ: Int = minZ + Chunk.CHUNK_SIZE -1

    val aabb = new AxisAlignedBB(minX,minY,minZ,maxX+1,maxY+1,maxZ+1)
}

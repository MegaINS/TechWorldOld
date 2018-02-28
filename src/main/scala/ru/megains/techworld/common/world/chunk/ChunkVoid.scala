package ru.megains.techworld.common.world.chunk

import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.world.World
import ru.megains.techworld.common.world.chunk.data.ChunkPosition


class ChunkVoid( position: ChunkPosition,world: World) extends Chunk(position,world) {


    override def isAirBlock(pos: BlockPos): Boolean = true


}

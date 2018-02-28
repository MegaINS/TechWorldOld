package ru.megains.techworld.client.render.api


import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.world.World


abstract class ARenderBlock {

    def render(blockState: BlockState, world: World, posWorld: BlockPos, posRender: BlockPos): Boolean
}

package ru.megains.techworld.client.render.block

import ru.megains.techworld.client.render.api.ARenderBlock
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.world.World

object RenderBlockVoid extends ARenderBlock{


    override def render(blockState: BlockState, world: World, posWorld: BlockPos, posRender: BlockPos): Boolean = {
        false
    }
}

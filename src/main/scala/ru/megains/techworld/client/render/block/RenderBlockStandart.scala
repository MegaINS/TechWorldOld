package ru.megains.techworld.client.render.block

import ru.megains.techworld.client.render.api.ARenderBlock
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.world.World


object RenderBlockStandart extends ARenderBlock {


    override def render(blockState: BlockState, world: World, pos: BlockPos, posRender: BlockPos): Boolean = {

        val block = blockState.block

        val AABB = blockState.getSelectedBoundingBox.sum(posRender.x, posRender.y, posRender.z)
        val minX = AABB.getMinX
        val minY = AABB.getMinY
        val minZ = AABB.getMinZ
        val maxX = AABB.getMaxX
        val maxY = AABB.getMaxY
        val maxZ = AABB.getMaxZ
        var isRender = false
        val blockBody = blockState.getBoundingBox



        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.SOUTH).sum(BlockDirection.SOUTH))) {
            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, block.getATexture(pos,BlockDirection.SOUTH,world))
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.NORTH).sum(BlockDirection.NORTH))) {
            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, block.getATexture(pos,BlockDirection.NORTH,world))
            isRender = true
        }

         if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.DOWN).sum(BlockDirection.DOWN))) {
            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, block.getATexture(pos,BlockDirection.DOWN,world))
            isRender = true
        }


        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.UP).sum(BlockDirection.UP))) {
            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.UP,world))
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.WEST).sum(BlockDirection.WEST))) {
            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.WEST,world))
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.EAST).sum(BlockDirection.EAST))) {
            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.EAST,world))
            isRender = true
        }
        isRender
    }
}

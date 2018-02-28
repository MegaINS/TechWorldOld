package ru.megains.techworld.client.render.block



//object RenderMicroBlock extends ARenderBlock {
//
//    override def render(block: Block, world: World, posWorld: BlockPos, posRender: BlockPos, offset: MultiBlockPos): Boolean = {
//
//
//        val AABB = block.getSelectedBoundingBox(posWorld, offset).sum(posRender.worldX, posRender.worldY, posRender.worldZ)
//        val minX = AABB.getMinX
//        val minY = AABB.getMinY
//        val minZ = AABB.getMinZ
//        val maxX = AABB.getMaxX
//        val maxY = AABB.getMaxY
//        val maxZ = AABB.getMaxZ
//
//        RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, block.getATexture(posWorld,BlockDirection.SOUTH,world))
//        RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, block.getATexture(posWorld,BlockDirection.NORTH,world))
//        RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, block.getATexture(posWorld,BlockDirection.DOWN,world))
//        RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, block.getATexture(posWorld,BlockDirection.UP,world))
//        RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, block.getATexture(posWorld,BlockDirection.WEST,world))
//        RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, block.getATexture(posWorld,BlockDirection.EAST,world))
//
//        true
//    }
//}

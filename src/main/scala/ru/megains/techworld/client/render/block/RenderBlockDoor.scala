package ru.megains.techworld.client.render.block

import ru.megains.techworld.client.render.api.ARenderBlock
import ru.megains.techworld.client.render.block.RenderBlock.mm
import ru.megains.techworld.client.render.texture.TextureAtlas
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.world.World

object RenderBlockDoor  extends ARenderBlock{




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
        val texture:TextureAtlas =  block.getATexture()
        val minU = texture.minU
        val maxU = texture.maxU /16f
        val minV = texture.minV
        val maxV = texture.maxV
        val averageX = (minX + maxX) / 2
        val averageY = (minY + maxY) / 2
        val averageZ = (minZ + maxZ) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2
        var newMaxU = 0f
        var newAverageU = 0f
        val rot = blockState.blockDirection



        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.SOUTH).sum(BlockDirection.SOUTH))) {



            newMaxU = rot match {
                case BlockDirection.EAST |BlockDirection.WEST =>
                    texture.maxU /16f
                case _ => texture.maxU/2
            }
            newAverageU = (minU + newMaxU) / 2

            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, minY, maxZ, newMaxU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, newMaxU, minV)
            mm.addVertexWithUV(averageX, averageY, maxZ, newAverageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(4, 0, 2)
            mm.addIndex(4, 2, 3)
            mm.addIndex(3, 1, 4)
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.NORTH).sum(BlockDirection.NORTH))) {


            newMaxU = rot match {
                case BlockDirection.EAST |BlockDirection.WEST =>
                    texture.maxU /16f
                case _ => texture.maxU/2
            }
            newAverageU = (minU + newMaxU) / 2

            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, minY, minZ, newMaxU, maxV)
            mm.addVertexWithUV(minX, maxY, minZ, newMaxU, minV)
            mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
            mm.addVertexWithUV(averageX, averageY, minZ, newAverageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(0, 4, 2)
            mm.addIndex(2, 4, 3)
            mm.addIndex(1, 3, 4)
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.DOWN).sum(BlockDirection.DOWN))) {
            newMaxU = texture.maxU/16
            newAverageU = (minU + newMaxU) / 2


            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, minY, minZ, newMaxU, maxV)
            mm.addVertexWithUV(maxX, minY, maxZ, newMaxU, minV)
            mm.addVertexWithUV(averageX, minY, averageZ, newAverageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(2, 3, 4)
            mm.addIndex(3, 1, 4)
            mm.addIndex(0, 2, 4)


            isRender = true
        }
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY+7.5f, minZ, minU, maxV)
        mm.addVertexWithUV(minX, minY+7.5f, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY+7.5f, minZ, newMaxU, maxV)
        mm.addVertexWithUV(maxX, minY+7.5f, maxZ, newMaxU, minV)
        mm.addVertexWithUV(averageX, minY+7.5f, averageZ, newAverageU, averageV)
        mm.addIndex(1, 0, 4)
        mm.addIndex(2, 3, 4)
        mm.addIndex(3, 1, 4)
        mm.addIndex(0, 2, 4)

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.UP).sum(BlockDirection.UP))) {

            newMaxU = texture.maxU/16
            newAverageU = (minU + newMaxU) / 2
            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, maxY, minZ, newMaxU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, newMaxU, minV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(averageX, maxY, averageZ, newAverageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(3, 2, 4)
            mm.addIndex(1, 3, 4)
            mm.addIndex(2, 0, 4)
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.WEST).sum(BlockDirection.WEST))) {
            newMaxU = rot match {
                case BlockDirection.NORTH |BlockDirection.NORTH =>
                    texture.maxU /16f
                case _ => texture.maxU/2
            }
            newAverageU = (minU + newMaxU) / 2
            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, minY, maxZ, newMaxU, maxV)
            mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
            mm.addVertexWithUV(minX, maxY, maxZ, newMaxU, minV)
            mm.addVertexWithUV(minX, averageY, averageZ, newAverageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(1, 3, 4)
            mm.addIndex(3, 2, 4)
            mm.addIndex(2, 0, 4)
            isRender = true
        }

        if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.EAST).sum(BlockDirection.EAST))) {
            newMaxU = rot match {
                case BlockDirection.SOUTH |BlockDirection.NORTH =>
                    texture.maxU /16f
                case _ => texture.maxU/2
            }
            newAverageU = (minU + newMaxU) / 2
            mm.setCurrentIndex()
            mm.addVertexWithUV(maxX, minY, minZ, newMaxU, maxV)
            mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, minZ, newMaxU, minV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, averageY, averageZ, newAverageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(3, 1, 4)
            mm.addIndex(2, 3, 4)
            mm.addIndex(0, 2, 4)
            isRender = true
        }
        isRender
    }
}

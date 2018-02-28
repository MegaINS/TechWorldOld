package ru.megains.techworld.client.render.block


import ru.megains.techworld.client.render.mesh.MeshMaker
import ru.megains.techworld.client.render.texture.TextureAtlas


object RenderBlock {

    val mm = MeshMaker.getMeshMaker


    def renderSideUp(minX: Double, maxX: Double, maxY: Double, minZ: Double, maxZ: Double, texture: TextureAtlas): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        val averageX = (minX + maxX) / 2
        val averageZ = (minZ + maxZ) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(averageX, maxY, averageZ, averageU, averageV)
        mm.addIndex(0, 1, 4)
        mm.addIndex(3, 2, 4)
        mm.addIndex(1, 3, 4)
        mm.addIndex(2, 0, 4)
    }

    def renderSideDown(minX: Double, maxX: Double, minY: Double, minZ: Double, maxZ: Double, texture: TextureAtlas): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        val averageX = (minX + maxX) / 2
        val averageZ = (minZ + maxZ) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, minV)
        mm.addVertexWithUV(averageX, minY, averageZ, averageU, averageV)
        mm.addIndex(1, 0, 4)
        mm.addIndex(2, 3, 4)
        mm.addIndex(3, 1, 4)
        mm.addIndex(0, 2, 4)
    }

    def renderSideWest(minX: Double, minY: Double, maxY: Double, minZ: Double, maxZ: Double, texture: TextureAtlas): Unit = {
        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        val averageY = (minY + maxY) / 2
        val averageZ = (minZ + maxZ) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2


        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(minX, averageY, averageZ, averageU, averageV)
        mm.addIndex(0, 1, 4)
        mm.addIndex(1, 3, 4)
        mm.addIndex(3, 2, 4)
        mm.addIndex(2, 0, 4)

    }

    def renderSideEast(maxX: Double, minY: Double, maxY: Double, minZ: Double, maxZ: Double, texture: TextureAtlas): Unit = {
        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        val averageY = (minY + maxY) / 2
        val averageZ = (minZ + maxZ) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2



        mm.setCurrentIndex()
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, averageY, averageZ, averageU, averageV)
        mm.addIndex(1, 0, 4)
        mm.addIndex(3, 1, 4)
        mm.addIndex(2, 3, 4)
        mm.addIndex(0, 2, 4)
    }

    def renderSideSouth(minX:  Double, maxX: Double, minY: Double, maxY: Double, maxZ: Double, texture: TextureAtlas): Unit = {


        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        val averageX = (minX + maxX) / 2
        val averageY = (minY + maxY) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(averageX, averageY, maxZ, averageU, averageV)
        mm.addIndex(1, 0, 4)
        mm.addIndex(4, 0, 2)
        mm.addIndex(4, 2, 3)
        mm.addIndex(3, 1, 4)
    }

    def renderSideNorth(minX: Double, maxX: Double, minY: Double, maxY: Double, minZ: Double, texture: TextureAtlas): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        val averageX = (minX + maxX) / 2
        val averageY = (minY + maxY) / 2
        val averageU = (minU + maxU) / 2
        val averageV = (minV + maxV) / 2

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(averageX, averageY, minZ, averageU, averageV)
        mm.addIndex(0, 1, 4)
        mm.addIndex(0, 4, 2)
        mm.addIndex(2, 4, 3)
        mm.addIndex(1, 3, 4)
    }


}

package ru.megains.techworld.client.render.item

import ru.megains.techworld.client.render.api.ARenderItem
import ru.megains.techworld.client.render.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.render.texture.{TextureAtlas, TextureManager}
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos}
import ru.megains.techworld.common.block.{Block, BlockState}
import ru.megains.techworld.common.item.ItemBlock

class RenderItemBlock(item: ItemBlock) extends ARenderItem {

    override lazy val inventoryMesh: Mesh = createInventoryMesh()
    override lazy val worldMesh: Mesh = createWorldMesh()
    val block: Block = item.block

    override def renderInWorld(textureManager: TextureManager): Unit = worldMesh.render(textureManager)

    override def renderInInventory(textureManager: TextureManager): Unit = inventoryMesh.render(textureManager)

    def createWorldMesh(): Mesh = {

        createInventoryMesh()
    }

    def createInventoryMesh(): Mesh = {


        val aabb = new BlockState(block,new BlockPos(0,0,0),BlockDirection.EAST).getSelectedBoundingBox





        var size:Float =  block.blockSize.length
        if(size<block.blockSize.height){
            size =block.blockSize.height
        }
        if(size<block.blockSize.width){
            size =block.blockSize.width
        }



        val maxX: Double = (aabb.getMaxX / 2f) /size
        val maxY: Double =( aabb.getMaxY / 2f) /size
        val maxZ: Double = (aabb.getMaxZ / 2f) /size
        val minX: Double = -maxX
        val minY: Double = -maxY
        val minZ: Double = -maxZ
        val averageX: Float = 0f
        val averageY: Float = 0f
        val averageZ: Float = 0f


        var minU: Float = 0
        var maxU: Float = 0
        var minV: Float = 0
        var maxV: Float = 0
        var averageU: Float = 0
        var averageV: Float = 0
        var texture: TextureAtlas = null

        val mm = MeshMaker.getMeshMaker



        mm.startMakeTriangles()
        mm.setTexture(TextureManager.locationBlockTexture)
        texture = block.getATexture(blockDirection = BlockDirection.UP)
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV
        averageU = texture.averageU
        averageV = texture.averageV

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



        //V2


        texture = block.getATexture(blockDirection =BlockDirection.DOWN)
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV
        averageU = texture.averageU
        averageV = texture.averageV





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




        texture = block.getATexture(blockDirection =BlockDirection.NORTH)
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV
        averageU = texture.averageU
        averageV = texture.averageV




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



        texture = block.getATexture(blockDirection =BlockDirection.SOUTH)
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV
        averageU = texture.averageU
        averageV = texture.averageV



        mm.setCurrentIndex()
        mm.addColor(0.5f, 0.5f, 0.5f)

        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, averageY, averageZ, averageU, averageV)
        mm.addIndex(1, 0, 4)
        mm.addIndex(3, 1, 4)
        mm.addIndex(2, 3, 4)
        mm.addIndex(0, 2, 4)






        texture = block.getATexture(blockDirection =BlockDirection.WEST)
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV
        averageU = texture.averageU
        averageV = texture.averageV





        mm.setCurrentIndex()
        mm.addColor(0.7f, 0.7f, 0.7f)
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(averageX, averageY, maxZ, averageU, averageV)
        mm.addIndex(1, 0, 4)
        mm.addIndex(4, 0, 2)
        mm.addIndex(4, 2, 3)
        mm.addIndex(3, 1, 4)





        texture = block.getATexture(blockDirection =BlockDirection.EAST)
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV
        averageU = texture.averageU
        averageV = texture.averageV



        mm.setCurrentIndex()
        mm.addColor(1f, 1f, 1f)
        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(averageX, averageY, minZ, averageU, averageV)
        mm.addIndex(0, 1, 4)
        mm.addIndex(0, 4, 2)
        mm.addIndex(2, 4, 3)
        mm.addIndex(1, 3, 4)


        mm.makeMesh()
    }
}

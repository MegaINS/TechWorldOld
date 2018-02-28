package ru.megains.techworld.client.render.world

import java.awt.Color

import org.lwjgl.opengl.GL11._
import ru.megains.techworld.client.render.{Frustum, Renderer, Transformation}
import ru.megains.techworld.client.render.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.render.texture.TextureManager
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.entity.item.EntityItem
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.world.World
import ru.megains.techworld.common.world.chunk.data.ChunkPosition
import ru.megains.techworld.register.GameRegister

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


class WorldRenderer(val world: World, val textureManager: TextureManager) {


    world.worldRenderer = this

    val renderChunks: mutable.HashMap[Long, RenderChunk] = new mutable.HashMap[Long, RenderChunk]
    var blockMouseOver: Mesh = _
    var blockSelect: Mesh = _
    val range = 5
    var lastX = 0
    var lastY = 0
    var lastZ = 0
    val playerRenderChunks:ArrayBuffer[RenderChunk] =  mutable.ArrayBuffer[RenderChunk]()

    var a = false

    def getRenderChunks(entityPlayer: EntityPlayer): ArrayBuffer[RenderChunk] = {
        // TODO:  OPTIMIZE
        val posX: Int = entityPlayer.posX / 64 - (if (entityPlayer.posX < 0) 1 else 0) toInt
        val posY: Int = entityPlayer.posY / 64 - (if (entityPlayer.posY < 0) 1 else 0) toInt
        val posZ: Int = entityPlayer.posZ / 64 - (if (entityPlayer.posZ < 0) 1 else 0) toInt


        if(posX != lastX ||
           posY != lastY ||
           posZ != lastZ ||
           playerRenderChunks.isEmpty){
            lastX = posX
            lastY = posY
            lastZ = posZ
            playerRenderChunks.clear()
            for(x <- posX - range to posX + range;
                y <- posY - range to posY + range;
                z <- posZ - range to posZ + range){
                playerRenderChunks += getRenderChunk(x, y, z)
            }
        }

        playerRenderChunks
    }


    def reRender(pos: BlockPos) {
        val x: Int = pos.x >> 6
        val y: Int = pos.y >> 6
        val z: Int = pos.z >> 6
        getRenderChunk(x, y, z).reRender()
        getRenderChunk(x + 1, y, z).reRender()
        getRenderChunk(x - 1, y, z).reRender()
        getRenderChunk(x, y + 1, z).reRender()
        getRenderChunk(x, y - 1, z).reRender()
        getRenderChunk(x, y, z + 1).reRender()
        getRenderChunk(x, y, z - 1).reRender()
    }

    def reRender(position: ChunkPosition): Unit = {
        val x: Int = position.x
        val y: Int = position.y
        val z: Int = position.z
        getRenderChunk(x, y, z).reRender()
        getRenderChunk(x + 1, y, z).reRender()
        getRenderChunk(x - 1, y, z).reRender()
        getRenderChunk(x, y + 1, z).reRender()
        getRenderChunk(x, y - 1, z).reRender()
        getRenderChunk(x, y, z + 1).reRender()
        getRenderChunk(x, y, z - 1).reRender()
    }

    def reRenderWorld(): Unit = {
        renderChunks.values.foreach(_.reRender())
    }

    def getRenderChunk(x: Int, y: Int, z: Int): RenderChunk = {
        val i: Long = (x & 16777215l) << 40 | (z & 16777215l) << 16 | (y & 65535L)

        if (renderChunks.contains(i)/* && !renderChunks(i).isVoid*/) renderChunks(i)
        else {
            val chunkRen = createChunkRen(x, y, z)
            renderChunks += i -> chunkRen
            chunkRen
        }
    }

    def createChunkRen(x: Int, y: Int, z: Int): RenderChunk = {
        new RenderChunk(world.getChunk(new ChunkPosition(x, y, z)), textureManager)
    }

    def cleanUp(): Unit = renderChunks.values.foreach(_.cleanUp())

    def updateBlockMouseOver(pos: BlockPos, blockState: BlockState): Unit = {
        if (blockMouseOver != null) {
            blockMouseOver.cleanUp()
            blockMouseOver = null
        }

        val mm = MeshMaker.getMeshMaker
        val aabb = blockState.getSelectedBoundingBox

        val minX = aabb.getMinX - 0.01f
        val minY = aabb.getMinY - 0.01f
        val minZ = aabb.getMinZ - 0.01f
        val maxX = aabb.getMaxX + 0.01f
        val maxY = aabb.getMaxY + 0.01f
        val maxZ = aabb.getMaxZ + 0.01f



        mm.startMake(GL_LINES)
        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        blockMouseOver = mm.makeMesh()
    }

    def updateBlockSelect(blockState: BlockState): Unit = {
        if (blockSelect != null) {
            blockSelect.cleanUp()
            blockSelect = null
        }

        val mm = MeshMaker.getMeshMaker
        val aabb = blockState.getSelectedBoundingBox

        val minX = aabb.getMinX
        val minY = aabb.getMinY
        val minZ = aabb.getMinZ
        val maxX = aabb.getMaxX
        val maxY = aabb.getMaxY
        val maxZ = aabb.getMaxZ



        mm.startMake(GL_LINES)
        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        blockSelect = mm.makeMesh()
    }

    def renderBlockMouseOver(): Unit = if (blockMouseOver != null){
        blockMouseOver.render(textureManager)
    }

    def renderBlockSelect(): Unit = if (blockSelect != null){
        blockSelect.render(textureManager)
    }

    def renderEntitiesItem(frustum: Frustum, transformation: Transformation): Unit = {


        world.entities.filter(_.isInstanceOf[EntityItem]).foreach(
            (entity) => {
                if (frustum.cubeInFrustum(entity.body)) {
                    val modelViewMatrix = transformation.buildEntityModelViewMatrix(entity)
                    Renderer.currentShaderProgram.setUniform("modelViewMatrix", modelViewMatrix)
                    GameRegister.getItemRender(entity.asInstanceOf[EntityItem].itemStack.item).renderInWorld(textureManager)
                }
            }
        )
    }
    def renderEntities(frustum: Frustum, transformation: Transformation): Unit = {


        world.entities.filter(!_.isInstanceOf[EntityItem]).foreach(
            (entity) => {
                if (frustum.cubeInFrustum(entity.body)) {
                    val modelViewMatrix = transformation.buildEntityModelViewMatrix(entity)
                    Renderer.currentShaderProgram.setUniform("modelViewMatrix", modelViewMatrix)
                    GameRegister.getItemRender(entity.asInstanceOf[EntityItem].itemStack.item).renderInWorld(textureManager)
                }
            }
        )
    }

}

package ru.megains.techworld.client.render.world

import ru.megains.techworld.client.render.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.render.texture.TextureManager
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.World
import ru.megains.techworld.common.world.chunk.Chunk
import ru.megains.techworld.common.world.chunk.data.ChunkPosition
import ru.megains.techworld.register.GameRegister

class RenderChunk(var chunk: Chunk, textureManager: TextureManager) extends Logger[RenderChunk] {


   // val isVoid: Boolean = chunk.isInstanceOf[ChunkVoid]
    val world: World = chunk.world
    val chunkPosition: ChunkPosition = chunk.position
    var isReRender: Boolean = true
    var blockRender: Int = 0
    val meshes: Array[Mesh] = new Array[Mesh](2)

    def render(layer: Int) {
       // if (!isVoid) {
            if (isReRender) {
                if (RenderChunk.rend < 1) {
                    // if (!chunk.isVoid) {

                    blockRender = 0
                    cleanUp()
                    makeChunk(0)
                    // }
                    isReRender = false
                    RenderChunk.rend += 1
                    RenderChunk.chunkUpdate += 1
                }
            }
            renderChunk(layer)
      //  }

    }

    private def makeChunk(layer: Int) {

        log.debug(s"Start make chunk ${chunkPosition.maxX} ${chunkPosition.maxY} ${chunkPosition.maxZ} ")
        MeshMaker.startMakeTriangles()
        MeshMaker.setTexture(TextureManager.locationBlockTexture)


        val s = chunk.getBlocks
        log.debug(s"Start make chunk ${chunkPosition.maxX} ${chunkPosition.maxY} ${chunkPosition.maxZ} ")
        s.foreach {
            blockState =>
                GameRegister.getBlockRender(blockState.block).render(blockState, world, blockState.pos,  blockState.pos.local())
                blockRender += 1
        }
        log.debug("Chunk completed")

        meshes(layer) = MeshMaker.makeMesh()
        log.debug("Chunk completed")

    }

    def cleanUp() {
        for (mesh <- meshes) {
            if (mesh ne null) mesh.cleanUp()
        }
    }

    private def renderChunk(layer: Int) {
        if (blockRender != 0) if (meshes(layer) ne null) {
            meshes(layer).render(textureManager)
            chunk.chunkTileEntityMap.values.foreach(tileEntity =>
                    GameRegister.getTileEntityRender(tileEntity.getClass).render(tileEntity,world ,textureManager)
            )
            RenderChunk.chunkRender += 1


        }
    }

    def reRender() {
        isReRender = true
    }

}

object RenderChunk {
    var chunkRender: Int = 0
    var chunkUpdate: Int = 0
    var rend: Int = 0

    def clearRend() {
        rend = 0
    }
}


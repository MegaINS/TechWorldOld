package ru.megains.techworld.common.world.storage

import java.io.IOException

import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.World
import ru.megains.techworld.common.world.chunk.data.ChunkPosition
import ru.megains.techworld.common.world.chunk.{Chunk, ChunkVoid}

import scala.collection.mutable
import scala.language.postfixOps

class ChunkProvider(world: World,chunkLoader:ChunkLoader) extends Logger[ChunkProvider]{

    val voidChunk = new ChunkVoid(new ChunkPosition(0,0,0),world)

    val chunkMap = new mutable.HashMap[Long,Chunk]()

    val chunkGenerator: ChunkGenerator = new ChunkGenerator(world)



    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = loadChunk(chunkX, chunkY, chunkZ)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
            try {
                chunk = chunkGenerator.provideChunk(chunkX, chunkY, chunkZ)
            } catch {
                case throwable: Throwable =>
                    throwable.printStackTrace()
            }
            chunkMap += i -> chunk
            //  chunk.onChunkLoad()
            //  chunk.populateChunk(this, chunkGenerator)
        }
        chunk
    }

    def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = getLoadedChunk(chunkX, chunkY, chunkZ)
        if (chunk == null) {
            chunk = chunkLoader.loadChunk(world, chunkX, chunkY, chunkZ)
            if (chunk != null) {
                chunkMap += Chunk.getIndex(chunkX, chunkY, chunkZ) -> chunk
            }
        }
        chunk
    }

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }

    def saveChunkData(chunkIn: Chunk) {
        try
            //  chunkIn.setLastSaveTime(this.worldObj.getTotalWorldTime)
            chunkLoader.saveChunk(chunkIn)

        catch {
            case ioexception: IOException => {
                log.error("Couldn\'t save chunk", ioexception.asInstanceOf[Throwable])
            }
        }
    }
    def saveChunks(p_186027_1: Boolean): Boolean = {

        chunkMap.values.foreach(
            (chunk) => {
               // if (p_186027_1) saveChunkExtraData(chunk)
              //  if (chunk.needsSaving(p_186027_1)) {
                    saveChunkData(chunk)
                    // chunk.setModified(false)

              //  }
            }
        )
        chunkLoader.regionLoader.close()
        true
    }
}

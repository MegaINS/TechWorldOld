package ru.megains.techworld.common.world

import org.joml.Vector3d
import ru.megains.techworld.client.render.world.WorldRenderer
import ru.megains.techworld.common.RayTraceResult
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos, BlockSidePos}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.entity.item.EntityItem
import ru.megains.techworld.common.physics.AxisAlignedBB
import ru.megains.techworld.common.tileentity.TileEntity
import ru.megains.techworld.common.utils.{Logger, MathHelper}
import ru.megains.techworld.common.world.chunk.Chunk
import ru.megains.techworld.common.world.chunk.data.ChunkPosition
import ru.megains.techworld.common.world.storage.{ChunkLoader, ChunkProvider}
import ru.megains.techworld.register.Blocks

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps

class World(saveHandler: AnvilSaveHandler) extends Logger[World]{



    var worldRenderer:WorldRenderer = _
    // The length of the world from -8000000 to 8000000
    val length: Int = 8000000
    // The width of the world from -8000000 to 8000000
    val width: Int = 8000000
    // The height of the world from -40000 to 40000
    val height: Int = 40000
    val chunkLoader: ChunkLoader = saveHandler.getChunkLoader
    val chunkProvider: ChunkProvider = new ChunkProvider(this,chunkLoader)
    val heightMap: WorldHeightMap = new WorldHeightMap(0)
    val entities: ArrayBuffer[Entity] = new ArrayBuffer[Entity]()
    val tickableTileEntities: ArrayBuffer[TileEntity] = new ArrayBuffer[TileEntity]()


    def update() {
        entities.foreach(_.update())
        tickableTileEntities.foreach(_.update(this))
    }

    def spawnEntityInWorld(entity: Entity): Unit = {
        val chunk = getChunk( entity.posX toInt,entity.posY toInt,entity.posZ toInt)
        if (chunk != null){
            chunk.addEntity(entity)

        }
    }

    def addEntity(entityIn: Entity): Unit = {
        entities += entityIn
    }

    def removeEntity(entityIn: Entity): Unit = {
        entities -= entityIn
    }


    def setAirBlock(pos: BlockPos):Boolean ={
        setBlock(new BlockState(Blocks.air,pos))
    }


    def addBlocksInList(aabb: AxisAlignedBB): mutable.HashSet[AxisAlignedBB] = {
        var x0: Int = Math.floor(aabb.getMinX )  toInt
        var y0: Int = Math.floor(aabb.getMinY)  toInt
        var z0: Int = Math.floor(aabb.getMinZ) toInt
        var x1: Int = Math.ceil(aabb.getMaxX)  toInt
        var y1: Int = Math.ceil(aabb.getMaxY)  toInt
        var z1: Int = Math.ceil(aabb.getMaxZ)  toInt


        if (x0 < -length) {
            x0 = -length
        }
        if (y0 < -height) {
            y0 = -height
        }
        if (z0 < -width) {
            z0 = -width
        }
        if (x1 > length) {
            x1 = length
        }
        if (y1 > height) {
            y1 = height
        }
        if (z1 > width) {
            z1 = width
        }
        var blockPos: BlockPos = null
        val aabbs = mutable.HashSet[AxisAlignedBB]()

        for (x <- x0 to x1; y <- y0 to y1; z <- z0 to z1) {

            blockPos = new BlockPos(x, y, z)
            if (!isAirBlock(blockPos)) {
                aabbs += getBlock(blockPos).getBoundingBox
            }
        }
        aabbs
    }

    def getEntityItemsNearEntity(entityIn: Entity, dist: Int): ArrayBuffer[EntityItem] = {
        val cube = entityIn.body.expand(dist)
        entities.filter{
            entity =>entity.isInstanceOf[EntityItem]
        }.filter{
           entity => cube.checkCollision(entity.body)
        }.map(_.asInstanceOf[EntityItem])
    }
    def deleteEntityInWorld(entity: Entity): Unit = {
        val chunk =  chunkProvider.provideChunk( entity.chunkCoordX,entity.chunkCoordY,entity.chunkCoordZ)
        if (chunk != null) chunk.removeEntity(entity)
    }


    def setTileEntity(pos: BlockPos, tileEntityIn: TileEntity): Unit = {
      //  pos = pos.toImmutable // Forge - prevent mutable BlockPos leaks

//        if (!this.isOutsideBuildHeight(pos)) if (tileEntityIn != null && !tileEntityIn.isInvalid) if (this.processingLoadedTiles) {
//
//
//            val iterator = this.addedTileEntityList.iterator
//            while ( {
//                iterator.hasNext
//            }) {
//                val tileentity = iterator.next.asInstanceOf[TileEntity]
//                if (tileentity.getPos == pos) {
//                    tileentity.invalidate()
//                    iterator.remove()
//                }
//            }
//          //  this.addedTileEntityList.add(tileEntityIn)
//        }
//        else {
            val chunk = getChunk(pos)
            if (chunk != null) chunk.addTileEntity(pos, tileEntityIn)
            addTileEntity(tileEntityIn)
      //  }
    }


    def getTileEntity(pos: BlockPos): TileEntity ={
        if (!pos.isValid(this)){
            null
        } else {
           // var tileentity = null
           // if (this.processingLoadedTiles) tileentity = this.getPendingTileEntityAt(pos)
          //  if (tileentity == null)
          var   tileentity = getChunk(pos).getTileEntity(pos)
            //if (tileentity == null) tileentity = this.getPendingTileEntityAt(pos)
            tileentity
        }
    }
    def removeTileEntity(pos: BlockPos): Unit = {


        val chunk = getChunk(pos)
        if (chunk != null) {
            tickableTileEntities -= chunk.getTileEntity(pos)
            chunk.removeTileEntity(pos)

        }

    }




    def addTileEntity(tile: TileEntity): Unit = {


      //  val dest = if (processingLoadedTiles) addedTileEntityList
     //   else loadedTileEntityList
     //   val flag = dest.add(tile)
       // if (flag && tile.isInstanceOf[ITickable])
            tickableTileEntities += tile
//        if (this.isRemote) {
//            val blockpos = tile.pos
//            val iblockstate = getBlock(blockpos)
//            this.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 2)
//        }
//        flag
    }

    def setBlock(block:BlockState):Boolean ={
        if (!block.pos.isValid(this)) {
            return false
        }
        worldRenderer.reRender(block.pos)
        val chunk = getChunk(block.pos)
        chunk.setBlock(block)


        //markAndNotifyBlock(pos, chunk, block, flag)
        true
    }


    def getChunk(x: Int, y: Int, z: Int): Chunk = {

        chunkProvider.provideChunk(x>> 6, y>> 6, z>> 6)


    }
    def getChunk(blockPos: BlockPos): Chunk = getChunk(blockPos.x, blockPos.y, blockPos.z)

    def getChunk(pos: ChunkPosition): Chunk = {
        chunkProvider.provideChunk(pos.x, pos.y,pos.z)
    }

    def getBlock(pos:BlockPos): BlockState ={
        if (!pos.isValid(this)) {
            return  Blocks.air.blockState
        }
        val chunk = getChunk(pos)
        chunk.getBlock(pos)
    }
    def isAirBlock(blockPos: BlockPos): Boolean = if (blockPos.isValid(this)) getChunk(blockPos).isAirBlock(blockPos) else true

    def isAirBlock(blockState: BlockState): Boolean = {
        if (blockState.pos.isValid(this)) getChunk(blockState.pos).isAirBlock(blockState) else true
    }

//    def isAirCube(min: BlockPos,max: BlockPos): Boolean = {
//        if (min.isValid(this)&& max.isValid(this)) getChunk(min).isAirCube(min,max) else true
//    }

    def isOpaqueCube(blockPos: BlockPos): Boolean = getBlock(blockPos).block.isOpaqueCube

//    def isOpaqueCube(min: BlockPos,max: BlockPos): Boolean = getChunk(min).isOpaqueCube(min,max)

    def isOpaqueCube(pos: BlockSidePos): Boolean  = {
        getChunk(pos.minX,pos.minY,pos.minZ).isOpaqueCube(pos)
    }


    def rayTraceBlocks(vec1: Vector3d, vec32: Vector3d, stopOnLiquid: Boolean, ignoreBlockWithoutBoundingBox: Boolean, returnLastUncollidableBlock: Boolean): RayTraceResult = {

        var vec31: Vector3d = vec1
        val i: Int = MathHelper.floor_double(vec32.x)
        val j: Int = MathHelper.floor_double(vec32.y)
        val k: Int = MathHelper.floor_double(vec32.z)
        var l: Int = MathHelper.floor_double(vec31.x)
        var i1: Int = MathHelper.floor_double(vec31.y)
        var j1: Int = MathHelper.floor_double(vec31.z)
        var blockpos: BlockPos = null
        val raytraceresult2: RayTraceResult = null


        var k1: Int = 200
        while (k1 >= 0) {
            k1 -= 1
            if (l == i && i1 == j && j1 == k) {
                return if (returnLastUncollidableBlock) raytraceresult2 else null
            }
            var flag2: Boolean = true
            var flag: Boolean = true
            var flag1: Boolean = true
            var d0: Double = 999.0D
            var d1: Double = 999.0D
            var d2: Double = 999.0D
            if (i > l) {
                d0 = l + 1.0D
            } else if (i < l) {
                d0 = l + 0.0D
            } else {
                flag2 = false
            }
            if (j > i1) {
                d1 = i1 + 1.0D
            } else if (j < i1) {
                d1 = i1 + 0.0D
            } else {
                flag = false
            }
            if (k > j1) {
                d2 = j1 + 1.0D
            } else if (k < j1) {
                d2 = j1 + 0.0D
            } else {
                flag1 = false
            }
            var d3: Double = 999.0D
            var d4: Double = 999.0D
            var d5: Double = 999.0D
            val d6: Double = vec32.x - vec31.x
            val d7: Double = vec32.y - vec31.y
            val d8: Double = vec32.z - vec31.z
            if (flag2) {
                d3 = (d0 - vec31.x) / d6
            }
            if (flag) {
                d4 = (d1 - vec31.y) / d7
            }
            if (flag1) {
                d5 = (d2 - vec31.z) / d8
            }
            if (d3 == -0.0D) {
                d3 = -1.0E-4f
            }
            if (d4 == -0.0D) {
                d4 = -1.0E-4f
            }
            if (d5 == -0.0D) {
                d5 = -1.0E-4f
            }
            var enumfacing: BlockDirection = null
            if (d3 < d4 && d3 < d5) {
                enumfacing = if (i > l) BlockDirection.WEST else BlockDirection.EAST
                vec31 = new Vector3d(d0 toFloat, vec31.y + d7 * d3 toFloat, vec31.z + d8 * d3 toFloat)
            } else if (d4 < d5) {
                enumfacing = if (j > i1) BlockDirection.DOWN else BlockDirection.UP
                vec31 = new Vector3d(vec31.x + d6 * d4 toFloat, d1 toFloat, vec31.z + d8 * d4 toFloat)
            } else {
                enumfacing = if (k > j1) BlockDirection.NORTH else BlockDirection.SOUTH
                vec31 = new Vector3d(vec31.x + d6 * d5 toFloat, vec31.y + d7 * d5 toFloat, d2 toFloat)
            }
            l = MathHelper.floor_double(vec31.x) - (if (enumfacing == BlockDirection.EAST) 1 else 0)
            i1 = MathHelper.floor_double(vec31.y) - (if (enumfacing == BlockDirection.UP) 1 else 0)
            j1 = MathHelper.floor_double(vec31.z) - (if (enumfacing == BlockDirection.SOUTH) 1 else 0)
            blockpos = new BlockPos(l, i1, j1)
            val block1: BlockState = getBlock(blockpos)
            if (block1 != null && block1.block != Blocks.air ) {
                val raytraceresult1: RayTraceResult = block1.block.collisionRayTrace(this, block1, vec31, vec32)
                if (raytraceresult1 != null) {
                    return raytraceresult1
                }
            }

        }
        if (returnLastUncollidableBlock) raytraceresult2 else null

    }

    def save(): Unit = {
        log.info("World saved...")
        log.info("Saving chunks for level \'{}\'/{}")
        saveAllChunks(true)


        log.info("World saved completed")
    }
    def saveAllChunks(p_73044_1: Boolean /*, progressCallback: IProgressUpdate*/) {

      //  if (chunkProvider.canSave) {
            //   if (progressCallback != null) progressCallback.displaySavingString("Saving level")
          //  saveLevel()
            // if (progressCallback != null) progressCallback.displayLoadingString("Saving chunks")
            chunkProvider.saveChunks(p_73044_1)

//            chunkProvider.getLoadedChunks.foreach(
//                (chunk) => {
//                    val pos = chunk.position
//                    if (chunk != null && !playerManager.contains(pos.chunkX, pos.chunkY, pos.chunkZ)) chunkProviderServer.unload(chunk)
//                }
//            )
      //  }
    }
}

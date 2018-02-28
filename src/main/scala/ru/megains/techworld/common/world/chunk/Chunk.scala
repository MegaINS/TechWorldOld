package ru.megains.techworld.common.world.chunk

import ru.megains.techworld.common.block._
import ru.megains.techworld.common.block.blockdata.{BlockPos, BlockSidePos}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.tileentity.{TileEntity, TileEntityContainer}
import ru.megains.techworld.common.world.World
import ru.megains.techworld.common.world.chunk.data.{BlockStorage, ChunkPosition}
import ru.megains.techworld.register.Blocks

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Chunk(val position: ChunkPosition,val world: World){



    var blockStorage = new BlockStorage(position)
    var chunkTileEntityMap = new mutable.HashMap[Long,TileEntity]()
    var chunkEntityMap: ArrayBuffer[Entity] = new ArrayBuffer[Entity]()

    def getTileEntity(pos: BlockPos): TileEntity = {
        var tileentity = chunkTileEntityMap.get( Chunk.getIndex(pos.x,pos.y,pos.z))
//        if (tileentity != null) {
//            chunkTileEntityMap.remove(pos)
//            tileentity = null
//        }
//        if (tileentity == null) if (p_177424_2_ eq Chunk.EnumCreateEntityType.IMMEDIATE) {
//            tileentity = this.createNewTileEntity(pos)
//            this.world.setTileEntity(pos, tileentity)
//        }
       // else if (p_177424_2_ eq Chunk.EnumCreateEntityType.QUEUED) this.tileEntityPosQueue.add(pos.toImmutable)
        tileentity.get
    }

    def addEntity(entityIn: Entity): Unit = {
        chunkEntityMap += entityIn
        entityIn.chunkCoordX = position.x
        entityIn.chunkCoordY = position.y
        entityIn.chunkCoordZ = position.z
        world.addEntity(entityIn)
        entityIn.setWorld(world)
    }

    def removeEntity(entityIn: Entity): Unit = {
        chunkEntityMap -= entityIn
        world.removeEntity(entityIn)
    }

    def setBlock(blockState: BlockState): Boolean = {
        val pos = blockState.pos
        val block = blockState.block
        val blockStatePrevious =  getBlock(pos)

//        val blockX = pos.x & 63
//        val blockY = pos.y & 63
//        val blockZ = pos.z & 63


        //TODO Доделать
//        if((pos.isBase && blockState.block.blockSize == Block.baseBlockSize)||
//                (pos.isBase && blockState.block == Blocks.air && getBlock(blockState.pos).block.blockSize == Block.baseBlockSize)){
//            blockStorage.setDefaultBlock(blockX,blockY,blockZ,blockState)
//        }else{


        val aabb = if(block == Blocks.air){
            blockStatePrevious.getBoundingBox
        }else{
            blockState.getBoundingBox
        }

        val minX = aabb.getMinX toInt
        val minY = aabb.getMinY toInt
        val minZ = aabb.getMinZ toInt
        val maxX = aabb.getMaxX toInt
        val maxY = aabb.getMaxY toInt
        val maxZ = aabb.getMaxZ toInt

        for(x<-minX until maxX;
            y<-minY until maxY;
            z<-minZ until maxZ){

            if(x<=position.maxX && y<=position.maxY &&z<=position.maxZ){
                blockStorage.setBlock(x & 63,y & 63,z & 63,blockState)
            }else{
                world.getChunk(x,y,z).blockStorage.setBlock(x & 63,y & 63,z & 63,blockState)
            }
        }
        //}
        blockStatePrevious.block match {
            case _:BlockContainer =>
                world.removeTileEntity(blockStatePrevious.pos)
            case _ =>
        }




        block match {
           case container:TileEntityContainer =>
               val  tileEntity = container.createNewTileEntity(world,blockState)
               world.setTileEntity(pos, tileEntity)
               // }
              // if (tileEntity != null) tileEntity.updateContainingBlockInfo()
           case _ =>
        }
//
//        if (block.hasTileEntity(state)) {
//            //var tileentity1 = this.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK)
//           // if (tileentity1 == null) {
//           var  tileentity1 = block.createTileEntity(world)
//                world.setTileEntity(pos, tileentity1)
//           // }
//            if (tileentity1 != null) tileentity1.updateContainingBlockInfo()
//        }




        true
    }

    def removeTileEntity(pos: BlockPos): Unit = {
        chunkTileEntityMap -= Chunk.getIndex(pos.x,pos.y,pos.z)
    }


    def addTileEntity(tileEntityIn: TileEntity): Unit = {
        addTileEntity(tileEntityIn.pos,tileEntityIn)
        world.addTileEntity(tileEntityIn)
    }

    def addTileEntity(pos: BlockPos, tileEntityIn: TileEntity): Unit = {
//        if (tileEntityIn.getWorld ne this.world) { //Forge don't call unless it's changed, could screw up bad mods.
//            tileEntityIn.setWorld(this.world)
//        }
      //  tileEntityIn.setPos(pos)
        getBlock(pos).block match {
            case _:BlockContainer =>
                chunkTileEntityMap += Chunk.getIndex(pos.x,pos.y,pos.z) -> tileEntityIn
            case _ =>
        }

//        if (getBlockState(pos).getBlock.hasTileEntity(getBlockState(pos))) {
//          //  if (chunkTileEntityMap.containsKey(pos)) chunkTileEntityMap.get(pos).asInstanceOf[TileEntity].invalidate()
//            tileEntityIn.validate()
//            chunkTileEntityMap.put(pos, tileEntityIn)
//            tileEntityIn.onLoad()
//        }
    }


    def isAirBlock(pos: BlockPos): Boolean = {
        getBlock(pos).block == Blocks.air
    }

    def isAirBlock(blockState: BlockState): Boolean = {

        var empty = true

        val aabb = blockState.getBoundingBox

        val minX = aabb.getMinX toInt
        val minY = aabb.getMinY toInt
        val minZ = aabb.getMinZ toInt
        val maxX = aabb.getMaxX toInt
        val maxY = aabb.getMaxY toInt
        val maxZ = aabb.getMaxZ toInt

        for(x<-minX until maxX;
            y<-minY until maxY;
            z<-minZ until maxZ){

            val blockX = x & 63
            val blockY = y & 63
            val blockZ = z & 63
            if(x <= position.maxX && y<=position.maxY &&z<=position.maxZ){
               if( blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.blockState){
                   empty = false
               }
            }else{
                if(world.getChunk(x,y,z).blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.blockState) empty = false
            }
        }
        empty
    }

//    def isOpaqueCube(one: BlockPos, two: BlockPos): Boolean = {
//
//        var minX,maxX = 0
//        var minY,maxY = 0
//        var minZ,maxZ = 0
//
//        if(one.x<two.x){
//            minX = one.x
//            maxX = two.x
//        }else{
//            minX = two.x
//            maxX = one.x
//        }
//        if(one.y<two.y){
//            minY = one.y
//            maxY = two.y
//        }else{
//            minY = two.y
//            maxY = one.y
//        }
//        if(one.z<two.z){
//            minZ = one.z
//            maxZ = two.z
//        }else{
//            minZ = two.z
//            maxZ = one.z
//        }
//
//
//
//        for(x<-minX to maxX;
//            y<-minY to maxY;
//            z<-minZ to maxZ){
//
//            val blockX = x & 63
//            val blockY = y & 63
//            val blockZ = z & 63
//            if(x <= position.maxX && y<=position.maxY &&z<=position.maxZ){
//                if(!blockStorage.get(blockX,blockY,blockZ).block.isOpaqueCube)
//                    return false
//            }else{
//                if(world.getChunk(x,y,z).blockStorage.get(blockX,blockY,blockZ).block.isOpaqueCube)
//                    return false
//            }
//        }
//        true
//    }

    def isOpaqueCube(pos: BlockSidePos): Boolean = {


        for(x<-pos.minX to pos.maxX;
            y<-pos.minY to pos.maxY;
            z<-pos.minZ to pos.maxZ){

            val blockX = x & 63
            val blockY = y & 63
            val blockZ = z & 63
            if(x <= position.maxX && y<=position.maxY &&z<=position.maxZ){
                if(!blockStorage.get(blockX,blockY,blockZ).block.isOpaqueCube)
                    return false
            }else{
                if(world.getChunk(x,y,z).blockStorage.get(blockX,blockY,blockZ).block.isOpaqueCube)
                    return false
            }
        }
        true
    }


//    def isAirCube(one: BlockPos, two: BlockPos): Boolean = {
//
//
//        var empty = true
//        var minX,maxX = 0
//        var minY,maxY = 0
//        var minZ,maxZ = 0
//
//        if(one.x<two.x){
//            minX = one.x
//            maxX = two.x
//        }else{
//            minX = two.x
//            maxX = one.x
//        }
//        if(one.y<two.y){
//            minY = one.y
//            maxY = two.y
//        }else{
//            minY = two.y
//            maxY = one.y
//        }
//        if(one.z<two.z){
//            minZ = one.z
//            maxZ = two.z
//        }else{
//            minZ = two.z
//            maxZ = one.z
//        }
//
//
//
//        for(x<-minX to maxX;
//            y<-minY to maxY;
//            z<-minZ to maxZ){
//
//            val blockX = x & 63
//            val blockY = y & 63
//            val blockZ = z & 63
//            if(x <= position.maxX && y<=position.maxY &&z<=position.maxZ){
//                if( blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.blockState){
//                    empty = false
//                }
//            }else{
//                if(world.getChunk(x,y,z).blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.blockState) empty = false
//            }
//        }
//        empty
//    }



    def getBlock(pos: BlockPos): BlockState = {
        blockStorage.get(pos.x & 63,pos.y & 63,pos.z & 63)
    }

    def getBlocks:mutable.HashSet[BlockState] ={
        blockStorage.getBlocks
    }

}
object Chunk{

   val CHUNK_SIZE = 64
    def getIndex(x: Long, y: Long, z: Long): Long = (x & 16777215l) << 40 | (z & 16777215l) << 16 | (y & 65535L)
   // def getIndex(chunk: Chunk): Long = getIndex(chunk.position.chunkX, chunk.position.chunkY, chunk.position.chunkZ)

   // def getIndex(position: ChunkPosition): Long = getIndex(position.chunkX, position.chunkY, position.chunkZ)
}


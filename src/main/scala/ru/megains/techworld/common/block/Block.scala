package ru.megains.techworld.common.block

import org.joml.Vector3d
import ru.megains.techworld.client.render.texture.{TTextureRegister, TextureAtlas}
import ru.megains.techworld.common.RayTraceResult
import ru.megains.techworld.common.utils.Vec3f
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos, BlockSidePos, BlockSize}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.physics.AxisAlignedBB
import ru.megains.techworld.common.world.World

class Block(val name: String) {

    var aTexture: TextureAtlas = _
    val blockState = new BlockState(this,null)
    val blockSize: BlockSize = Block.defaultBlockSize
    val blockBody:AxisAlignedBB =  Block.FULL_AABB


    def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, p_onBlockActivated_6:ItemStack, ponBlockActivated7:BlockDirection, p_onBlockActivated_8: Float, p_onBlockActivated_9: Float): Boolean = {
       false
    }

    def isOpaqueCube = true

    def registerTexture(textureRegister: TTextureRegister): Unit = {
        aTexture = textureRegister.registerTexture(name)
    }

    def getATexture(pos: BlockPos = null,blockDirection: BlockDirection = BlockDirection.UP,world: World = null): TextureAtlas = aTexture

    def getSelectedBoundingBox(blockState: BlockState): AxisAlignedBB = blockBody.rotate(blockState.blockDirection)


    def collisionRayTrace(world: World, blockState: BlockState, start: Vector3d, end: Vector3d): RayTraceResult = {
        val pos = blockState.pos
        val vec3d: Vec3f = new Vec3f(start.x toFloat, start.y toFloat, start.z toFloat).sub(pos.x, pos.y, pos.z)
        val vec3d1: Vec3f = new Vec3f(end.x toFloat, end.y toFloat, end.z toFloat).sub(pos.x, pos.y, pos.z)
        val rayTraceResult = getSelectedBoundingBox(blockState).calculateIntercept(vec3d, vec3d1)

        if (rayTraceResult == null) {
            null
        } else {
            new RayTraceResult(rayTraceResult.hitVec.add(pos.x, pos.y, pos.z), rayTraceResult.sideHit, pos, this)
        }
    }
    def getSelectPosition(worldIn: World,entity: Entity, objectMouseOver: RayTraceResult): BlockState = {
        val side = entity.side
        //Todo доделать
        val posTarget: BlockPos = objectMouseOver.blockPos2
        var posSet: BlockPos = objectMouseOver.sideHit match {
            case BlockDirection.UP | BlockDirection.EAST |BlockDirection.SOUTH  => posTarget
            case BlockDirection.DOWN => posTarget.sum(0,-blockSize.height,0)
            case BlockDirection.WEST => posTarget.sum(-blockSize.width,0,0)
            case BlockDirection.NORTH => posTarget.sum(0,0,-blockSize.length)
            case BlockDirection.NONE => posTarget.sum(0,0,0)
        }

        posSet = side match {
            case BlockDirection.EAST  => posTarget.sum(0,0,Math.floor(-blockSize.width/2f)  + 1 toInt)
            case BlockDirection.WEST => posTarget.sum(-blockSize.length+1,0,-blockSize.width/2 )
            case BlockDirection.SOUTH => posTarget.sum(-blockSize.width/2,0,0)
            case BlockDirection.NORTH => posTarget.sum(Math.floor(-blockSize.width/2f)  + 1 toInt,0,-blockSize.length+1)
            case _ => posTarget.sum(0,0,0)
        }




        val blockState = new BlockState(this,posSet,side)
        if (worldIn.isAirBlock(blockState)) blockState
        else null
    }

    def getBoundingBox(blockState: BlockState): AxisAlignedBB = getSelectedBoundingBox(blockState).sum(blockState.pos.x, blockState.pos.y, blockState.pos.z)


    def getSidePos(blockState: BlockState, side: BlockDirection):BlockSidePos ={
        blockBody.rotate(blockState.blockDirection).sum(blockState.pos.x, blockState.pos.y, blockState.pos.z).getSidePos(side)
    }

    def breakBlock(world:World, blockState: BlockState): Unit = {
       // if (hasTileEntity(p_149749_6_) && !this.isInstanceOf[BlockContainer]) world.removeTileEntity(blockState.pos)
    }
}
object Block{

    val FULL_AABB = new AxisAlignedBB(0,0,0,1,1,1)
    val NULL_AABB = new AxisAlignedBB(0,0,0,0,0,0)
    val defaultBlockSize = new BlockSize(1,1,1)
    val baseBlockSize = new BlockSize(4,4,4)
}
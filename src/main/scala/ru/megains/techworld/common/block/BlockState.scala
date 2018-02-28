package ru.megains.techworld.common.block

import org.joml.Vector3d
import ru.megains.techworld.common.RayTraceResult
import ru.megains.techworld.common.block.blockdata.{BlockDirection, BlockPos, BlockSidePos}
import ru.megains.techworld.common.physics.AxisAlignedBB
import ru.megains.techworld.common.world.World

class BlockState(val block: Block,val pos: BlockPos,val blockDirection: BlockDirection = BlockDirection.NONE) {





    def getSelectedBoundingBox: AxisAlignedBB = block.getSelectedBoundingBox(this)


    def collisionRayTrace(world: World, start: Vector3d, end: Vector3d): RayTraceResult = block.collisionRayTrace(world,this, start, end)

    def getBoundingBox: AxisAlignedBB = {
        block.getBoundingBox(this)
    }

    def getSidePos(side: BlockDirection):BlockSidePos ={
        block.getSidePos(this,side)
    }

}

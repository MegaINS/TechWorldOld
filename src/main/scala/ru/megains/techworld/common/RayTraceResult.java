package ru.megains.techworld.common;


import ru.megains.techworld.common.utils.Vec3f;
import ru.megains.techworld.common.block.Block;
import ru.megains.techworld.common.block.blockdata.BlockPos;
import ru.megains.techworld.common.block.blockdata.BlockDirection;

public class RayTraceResult {
    /**
     * Used to determine what sub-segment is hit
     */
    public int subHit = -1;

    /**
     * Used to add extra hit info
     */
    public Object hitInfo = null;
    /**
     * What type of ray trace hit was this? 0 = block, 1 = entity
     */
    public RayTraceResult.Type typeOfHit;
    public BlockDirection sideHit;
    /**
     * The vector position of the hit
     */
    public Vec3f hitVec;
    public Block block;
    public BlockPos blockPos;
    public BlockPos blockPos2;

    /**
     * The hit entity
     */
    // public Entity entityHit;
    public RayTraceResult(Vec3f hitVecIn, BlockDirection sideHitIn, BlockPos blockPosIn, Block block) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn, block);
    }

    public RayTraceResult(Vec3f hitVecIn, BlockDirection sideHitIn) {
        this.sideHit = sideHitIn;
        this.hitVec = hitVecIn;
        //   this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, null,null);
    }

//    public RayTraceResult(Entity entityIn)
//    {
//        this(entityIn, new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ));
//    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vec3f hitVecIn, BlockDirection sideHitIn, BlockPos blockPosIn, Block block) {
        typeOfHit = typeIn;
        blockPos = blockPosIn;
        sideHit = sideHitIn;


        blockPos2 = new BlockPos((int) Math.floor(hitVecIn.x),(int)Math.floor(hitVecIn.y),(int)Math.floor(hitVecIn.z));



        this.hitVec = new Vec3f(Math.abs((hitVecIn.x % 1 + 1f) % 1), Math.abs((hitVecIn.y % 1 + 1f) % 1), Math.abs((hitVecIn.z % 1 + 1f) % 1));

        if (sideHit == BlockDirection.UP$.MODULE$ && hitVec.y == 0.0) {
            hitVec.add(0, 1, 0);
        }
        if (sideHit == BlockDirection.EAST$.MODULE$ && hitVec.x == 0.0) {
            hitVec.add(1, 0, 0);
        }
        if (sideHit == BlockDirection.SOUTH$.MODULE$ && hitVec.z == 0.0) {
            hitVec.add(0, 0, 1);
        }



        this.block = block;
    }

//    public RayTraceResult(Entity entityHitIn, Vec3d hitVecIn)
//    {
//        this.typeOfHit = RayTraceResult.Type.ENTITY;
//        this.entityHit = entityHitIn;
//        this.hitVec = hitVecIn;
//    }

    public BlockPos getBlockWorldPos() {
        return this.blockPos;
    }


    public static enum Type {
        MISS,
        BLOCK,
        ENTITY;
    }
}
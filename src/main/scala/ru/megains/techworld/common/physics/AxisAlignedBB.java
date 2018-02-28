package ru.megains.techworld.common.physics;


import ru.megains.techworld.common.RayTraceResult;
import ru.megains.techworld.common.block.blockdata.BlockSidePos;
import ru.megains.techworld.common.utils.Vec3f;
import ru.megains.techworld.common.block.blockdata.BlockDirection;

public class AxisAlignedBB {
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public AxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public AxisAlignedBB() {
        this(0, 0, 0, 0, 0, 0);
    }


    public void set(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void set(AxisAlignedBB aabb) {
        this.minX = aabb.minX;
        this.minY = aabb.minY;
        this.minZ = aabb.minZ;
        this.maxX = aabb.maxX;
        this.maxY = aabb.maxY;
        this.maxZ = aabb.maxZ;
    }

    public AxisAlignedBB getCopy() {
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

    }


    public AxisAlignedBB move(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

//
//    public AxisAlignedBB rotate(BlockDirection side) {
//
//        float newMinX = minX ;
//        float newMinZ = minZ ;
//        float newMaxX = maxX ;
//        float newMaxZ = maxZ ;
//
//
//
//        if(BlockDirection.WEST$.MODULE$ == side){
//
//            newMaxX = minX + 1;
//            newMaxZ = minZ + 1;
//            newMinX = newMaxX - (maxX - minX);
//            newMinZ = newMaxZ - (maxZ - minZ);
//        }
//
//        if(BlockDirection.SOUTH$.MODULE$ == side){
//            newMaxX = minX + 1;
//            newMinX = newMaxX - (maxZ - minZ);
//            newMaxZ = minZ + (maxX - minX);
//
//        }
//
//        if(BlockDirection.NORTH$.MODULE$ == side){
//            newMaxZ = minZ +1 ;
//            newMinZ = newMaxZ - (maxX - minX);
//            newMaxX = minX + (maxZ - minZ);
//        }
//
//        return new AxisAlignedBB(newMinX  , minY ,newMinZ ,newMaxX, maxY , newMaxZ );
//    }

    public AxisAlignedBB rotate(BlockDirection side) {

       // float newMinX = minX ;
      //  float newMinZ = minZ ;
        double newMaxX = maxX ;
        double newMaxZ = maxZ ;



//        if(BlockDirection.WEST$.MODULE$ == side ){
//
//            newMaxX = minX + 1;
//            newMaxZ = minZ + 1;
//            newMinX = newMaxX - (maxX - minX);
//            newMinZ = newMaxZ - (maxZ - minZ);
//        }

        if(BlockDirection.SOUTH$.MODULE$ == side || BlockDirection.NORTH$.MODULE$ == side){
            newMaxX = minX + maxZ - minZ;
           // newMinX = newMaxX - (maxZ - minZ);
            newMaxZ = minZ + (maxX - minX);

        }
//
//        if(BlockDirection.NORTH$.MODULE$ == side){
//            newMaxZ = minZ +1 ;
//            newMinZ = newMaxZ - (maxX - minX);
//            newMaxX = minX + (maxZ - minZ);
//        }

        return new AxisAlignedBB(minX  , minY ,minZ ,newMaxX, maxY , newMaxZ );
    }





    public BlockSidePos getSidePos(BlockDirection side) {


        if(BlockDirection.EAST$.MODULE$ == side){
            return new BlockSidePos((int) maxX,(int) minY,(int) minZ,(int) maxX,(int) maxY-1,(int) maxZ-1);
        }

        if(BlockDirection.WEST$.MODULE$ == side){
            return new BlockSidePos((int) minX,(int) minY,(int) minZ,(int) minX,(int) maxY-1,(int) maxZ-1);
        }

        if(BlockDirection.SOUTH$.MODULE$ == side){
            return new BlockSidePos((int) minX,(int) minY,(int) maxZ,(int) maxX-1,(int) maxY-1,(int) maxZ);
        }

        if(BlockDirection.NORTH$.MODULE$ == side){
            return new BlockSidePos((int) minX,(int) minY,(int) minZ,(int) maxX-1,(int) maxY-1,(int) minZ);
        }

        if(BlockDirection.DOWN$.MODULE$ == side){
            return new BlockSidePos((int) minX,(int) minY,(int) minZ,(int) maxX-1,(int) minY,(int) maxZ-1);
        }

        if(BlockDirection.UP$.MODULE$ == side){
            return new BlockSidePos((int) minX,(int) maxY,(int) minZ,(int) maxX-1,(int) maxY,(int) maxZ-1);
        }

        return null;
    }




    public AxisAlignedBB sum(float x, float y, float z) {
        return new AxisAlignedBB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);

    }




    public AxisAlignedBB sum(float x) {
        return new AxisAlignedBB(minX * x, minY * x, minZ * x, maxX * x, maxY * x, maxZ * x);

    }
    public AxisAlignedBB suma(float x) {
        return new AxisAlignedBB(minX / x, minY / x, minZ / x, maxX / x, maxY / x, maxZ / x);

    }
    public AxisAlignedBB addCoord(double x, double y, double z) {
        double x0 = minX;
        double y0 = minY;
        double z0 = minZ;
        double x1 = maxX;
        double y1 = maxY;
        double z1 = maxZ;
        if (x > 0.0) {
            x1 += x;
        } else {
            x0 += x;
        }
        if (y > 0.0) {
            y1 += y;
        } else {
            y0 += y;
        }
        if (z > 0.0) {
            z1 += z;
        } else {
            z0 += z;
        }
        return new AxisAlignedBB(x0, y0, z0, x1, y1, z1);
    }
    public AxisAlignedBB expand(float x, float y, float z)
    {
        double d0 = this.minX - x;
        double d1 = this.minY - y;
        double d2 = this.minZ - z;
        double d3 = this.maxX + x;
        double d4 = this.maxY + y;
        double d5 = this.maxZ + z;
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }
    public AxisAlignedBB expand(float x) {
       return expand(x, x, x);
    }
//    public boolean checkCollision(AxisAlignedBB aabb) {
//
//
//        if (minY <= aabb.getMaxY() && maxY >= aabb.getMinY()) {
//            if (minX <= aabb.getMaxX() && maxX >= aabb.getMinX()) {
//                if (minZ <= aabb.getMaxZ() && maxZ >= aabb.getMinZ()) {
//                    return true;
//                }
//
//            }
//        }
//        return false;
//    }
    public boolean checkCollision(AxisAlignedBB aabb) {


        if (maxY >= aabb.getMaxY() && minY <= aabb.getMinY()) {
            if (maxX>= aabb.getMaxX() && minX  <= aabb.getMinX()) {
                if (maxZ>= aabb.getMaxZ() && minZ  <= aabb.getMinZ()) {
                    return true;
                }

            }
        }
        return false;
    }

    public double checkXcollision(AxisAlignedBB aabb, double x) {

        if (minY < aabb.getMaxY() && maxY > aabb.getMinY()) {
            if (minZ < aabb.getMaxZ() && maxZ > aabb.getMinZ()) {
                double max;
                if (x > 0.0 && minX >= aabb.getMaxX() - x) {
                    max = minX - aabb.getMaxX();
                    if (max < x) {
                        x = max;
                    }
                }
                if (x < 0.0 && maxX <= aabb.getMinX() - x) {
                    max = maxX - aabb.getMinX();
                    if (max > x) {
                        x = max;
                    }
                }
            }
        }
        return x;
    }

    public double checkYcollision(AxisAlignedBB aabb, double y) {
        if (minX < aabb.getMaxX() && maxX > aabb.getMinX()) {
            if (minZ < aabb.getMaxZ() && maxZ > aabb.getMinZ()) {
                Double max;
                if (y > 0.0 && minY >= aabb.getMaxY()) {
                    max = minY - aabb.getMaxY();
                    if (max < y) {
                        y = max;
                    }
                }
                if (y < 0.0 && maxY <= aabb.getMinY()) {
                    max = maxY - aabb.getMinY();
                    if (max > y) {
                        y = max;
                    }
                }
            }
        }


        return y;
    }

    public double checkZcollision(AxisAlignedBB aabb, double z) {

        if (minY < aabb.getMaxY() && maxY > aabb.getMinY()) {
            if (minX < aabb.getMaxX() && maxX > aabb.getMinX()) {
                Double max;
                if (z > 0.0 && minZ >= aabb.getMaxZ() - z) {
                    max = minZ - aabb.getMaxZ();
                    if (max < z) {
                        z = max;
                    }
                }
                if (z < 0.0 && maxZ <= aabb.getMinZ() - z) {
                    max = maxZ - aabb.getMinZ();
                    if (max > z) {
                        z = max;
                    }
                }


            }
        }
        return z;
    }

    public RayTraceResult calculateIntercept(Vec3f vecA, Vec3f vecB) {
        Vec3f vec3d = this.func_186671_a(this.minX, vecA, vecB);
        BlockDirection enumfacing = BlockDirection.WEST$.MODULE$;
        Vec3f vec3d1 = this.func_186671_a(this.maxX, vecA, vecB);

        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1;
            enumfacing = BlockDirection.EAST$.MODULE$;
        }

        vec3d1 = this.func_186663_b(this.minY, vecA, vecB);

        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1;
            enumfacing = BlockDirection.DOWN$.MODULE$;
        }

        vec3d1 = this.func_186663_b(this.maxY, vecA, vecB);

        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1;
            enumfacing = BlockDirection.UP$.MODULE$;
        }


        vec3d1 = this.func_186665_c(this.minZ, vecA, vecB);

        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1;
            enumfacing = BlockDirection.NORTH$.MODULE$;
        }

        vec3d1 = this.func_186665_c(this.maxZ, vecA, vecB);

        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1;
            enumfacing = BlockDirection.SOUTH$.MODULE$;
        }


        return vec3d == null ? null : new RayTraceResult(vec3d, enumfacing);
    }

    boolean func_186661_a(Vec3f p_186661_1_, Vec3f p_186661_2_, Vec3f p_186661_3_) {
        return p_186661_2_ == null || p_186661_1_.distanceSquared(p_186661_3_) < p_186661_1_.distanceSquared(p_186661_2_);
    }

    Vec3f func_186671_a(double p_186671_1_, Vec3f p_186671_3_, Vec3f p_186671_4_) {
        Vec3f vec3d = p_186671_3_.getIntermediateWithXValue(p_186671_4_, p_186671_1_);
        return vec3d != null && this.intersectsWithYZ(vec3d) ? vec3d : null;
    }

    Vec3f func_186663_b(double p_186663_1_, Vec3f p_186663_3_, Vec3f p_186663_4_) {
        Vec3f vec3d = p_186663_3_.getIntermediateWithYValue(p_186663_4_, p_186663_1_);
        return vec3d != null && this.intersectsWithXZ(vec3d) ? vec3d : null;
    }

    Vec3f func_186665_c(double p_186665_1_, Vec3f p_186665_3_, Vec3f p_186665_4_) {
        Vec3f vec3d = p_186665_3_.getIntermediateWithZValue(p_186665_4_, p_186665_1_);
        return vec3d != null && this.intersectsWithXY(vec3d) ? vec3d : null;
    }

    public boolean intersectsWithYZ(Vec3f vec) {
        return vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ;
    }

    public boolean intersectsWithXZ(Vec3f vec) {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ;
    }

    public boolean intersectsWithXY(Vec3f vec) {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public double getCenterX() {
        return (maxX + minX) / 2;
    }

    public double getCenterZ() {
        return (maxZ + minZ) / 2;
    }
}

package ru.megains.techworld.client.render;

import org.joml.Matrix4f;
import ru.megains.techworld.common.block.blockdata.BlockPos;
import ru.megains.techworld.common.entity.Entity;
import ru.megains.techworld.common.physics.AxisAlignedBB;
import ru.megains.techworld.common.utils.Vec3f;
import ru.megains.techworld.common.world.chunk.data.ChunkPosition;




public class Transformation {

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelMatrix;

    private final Matrix4f modelViewMatrix;

    private final Matrix4f modelLightMatrix;

    private final Matrix4f modelLightViewMatrix;

    private final Matrix4f viewMatrix;

    private final Matrix4f lightViewMatrix;

    private final Matrix4f orthoProjMatrix;

    private final Matrix4f ortho2DMatrix;

    private final Matrix4f orthoModelMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        modelLightMatrix = new Matrix4f();
        modelLightViewMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        orthoProjMatrix = new Matrix4f();
        ortho2DMatrix = new Matrix4f();
        orthoModelMatrix = new Matrix4f();
        lightViewMatrix = new Matrix4f();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix(float fov, float width, float height, float zNear, float zFar, Camera camera) {
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);


        return projectionMatrix;
    }

    public final Matrix4f getOrthoProjectionMatrix() {
        return orthoProjMatrix;
    }

//    public Matrix4f updateOrthoProjectionMatrix(float left, float right, float bottom, float top, float zNear, float zFar) {
//
//
//        float tx = - (right + left) / (right - left),
//                ty = - (top + bottom) / (top - bottom),
//                tz = - (zFar + zNear) / (zFar - zNear);
//        return   new Matrix4f(2 / (right - left), 0, 0, tx,
//                    0, 2 / (top - bottom), 0, ty,
//                    0, 0, -2 / (zFar - zNear), tz,
//                    0, 0, 0, 1);
//    }

    public Matrix4f updateOrthoProjectionMatrix(float left, float right, float bottom, float top, float zNear, float zFar) {
        orthoProjMatrix.identity();
        orthoProjMatrix.setOrtho(left, right, bottom, top, zNear, zFar);

        return orthoProjMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f updateViewMatrix(Camera camera) {
        return updateGenericViewMatrix(camera.getPosition(), camera.getRotation(), viewMatrix);
    }


    public Matrix4f getLightViewMatrix() {
        return lightViewMatrix;
    }

    public void setLightViewMatrix(Matrix4f lightViewMatrix) {
        this.lightViewMatrix.set(lightViewMatrix);
    }

    public Matrix4f updateLightViewMatrix(Vec3f position, Vec3f rotation) {
        return updateGenericViewMatrix(position, rotation, lightViewMatrix);
    }

    private Matrix4f updateGenericViewMatrix(Vec3f position, Vec3f rotation, Matrix4f matrix) {
        matrix.identity();
        // First do the rotation so camera rotates over its position
        matrix.rotate((float) Math.toRadians(rotation.x), new Vec3f(1, 0, 0))
                .rotate((float) Math.toRadians(rotation.y), new Vec3f(0, 1, 0));
        // Then do the translation
        matrix.translate(-position.x/4, -position.y/4, -position.z/4);
        return matrix;
    }

    public final Matrix4f getOrtho2DProjectionMatrix(float left, float right, float bottom, float top) {
        ortho2DMatrix.identity();
        // ortho2DMatrix.setOrtho2D(left, right, bottom, top);
        ortho2DMatrix.setOrtho(left, right, bottom, top, -100, 2000);
        return ortho2DMatrix;
    }

    Matrix4f buildTextModelViewMatrix(BlockPos blockPos) {
        //    Vector3f rotation = gameItem.getRotation();
        modelMatrix.identity().translate(blockPos.x(), blockPos.y(), blockPos.z())
//                rotateX((float)Math.toRadians(-rotation.x)).
//                rotateY((float)Math.toRadians(-rotation.y)).
//                rotateZ((float)Math.toRadians(-rotation.z)).
                .scale(1f / 16f);
        //  modelViewMatrix.set(matrix);
        return modelViewMatrix.set(modelMatrix);//.mul(modelMatrix);
    }

    public Matrix4f buildBlockModelViewMatrix(BlockPos blockPos) {
        //    Vector3f rotation = gameItem.getRotation();
        modelMatrix.identity().translate(blockPos.x()/4f,blockPos.y()/4f,blockPos.z()/4f).scale(0.25f);
//                rotateX((float)Math.toRadians(-rotation.x)).
//                rotateY((float)Math.toRadians(-rotation.y)).
//                rotateZ((float)Math.toRadians(-rotation.z)).
        //  . scale(1f / 16f);
        //  modelViewMatrix.set(matrix);
        return modelViewMatrix.set(modelMatrix);//.mul(modelMatrix);
    }

//    public Matrix4f buildTileEntityViewMatrix(BlockPos blockPos) {
//        //    Vector3f rotation = gameItem.getRotation();
//        modelMatrix.identity().translate(blockPos.x()/4f,blockPos.y()/4f,blockPos.z()/4f).scale(0.25f);
////                rotateX((float)Math.toRadians(-rotation.x)).
////                rotateY((float)Math.toRadians(-rotation.y)).
////                rotateZ((float)Math.toRadians(-rotation.z)).
//        //  . scale(1f / 16f);
//        //  modelViewMatrix.set(matrix);
//        return modelViewMatrix.set(modelMatrix);//.mul(modelMatrix);
//    }

    public static Matrix4f buildTileEntityViewMatrix(BlockPos blockPos,int rotationX,int rotationY,int rotationZ) {
        //    Vector3f rotation = gameItem.getRotation();
        Matrix4f modelMatrix = new Matrix4f().identity().translate(blockPos.x()/4f,blockPos.y()/4f,blockPos.z()/4f).
                rotateX((float)Math.toRadians(-rotationX)).
                rotateY((float)Math.toRadians(-rotationY)).
                rotateZ((float)Math.toRadians(-rotationZ)).
                scale(0.25f);
        //  . scale(1f / 16f);
        //  modelViewMatrix.set(matrix);
        return modelMatrix;//.mul(modelMatrix);
    }

    public Matrix4f buildChunkModelViewMatrix(ChunkPosition position) {
        modelMatrix.identity().translate(position.minX()/4f, position.minY()/4f, position.minZ()/4f).scale(0.25f);
        return modelViewMatrix.set(modelMatrix);
    }

//    public Matrix4f buildModelLightViewMatrix(GameItem gameItem, Matrix4f matrix) {
//        Vector3f rotation = gameItem.getRotation();
//        modelLightMatrix.identity().translate(gameItem.getPosition()).
//                rotateX((float)Math.toRadians(-rotation.x)).
//                rotateY((float)Math.toRadians(-rotation.y)).
//                rotateZ((float)Math.toRadians(-rotation.z)).
//                scale(gameItem.getScale());
//        modelLightViewMatrix.set(matrix);
//        return modelLightViewMatrix.mul(modelLightMatrix);
//    }


    public Matrix4f buildOrtoProjModelMatrix(int x, int y) {

        return modelMatrix.identity().translate(x, y, 0);

    }

    public Matrix4f buildOrtoProjModelMatrix(int x, int y, float scale) {

        return modelMatrix.identity().translate(x, y, 0).scale(scale);

    }

    public Matrix4f buildOrtoProjModelMatrix(int xPos, int yPos, int zPos, int xPot, int yPot, int zPot, float scale) {

        return modelMatrix.identity()
                .translate(xPos, yPos, zPos)
                .rotateXYZ((float) Math.toRadians(-xPot),
                        (float) Math.toRadians(-yPot),
                        (float) Math.toRadians(-zPot))
                .scale(scale);

    }

//    public Matrix4f buildOrtoProjModelMatrix(GuiRenderInfo item) {
//        Vec3f rotation = item.rotation();
//        modelMatrix.identity().translate(item.position()).
//                rotateX((float) Math.toRadians(-rotation.x())).
//                rotateY((float) Math.toRadians(-rotation.y())).
//                rotateZ((float) Math.toRadians(-rotation.z())).
//                scale(item.scale());
//        return modelMatrix;
//    }

    public Matrix4f buildChunkModelViewMatrix(ChunkPosition position, Matrix4f lightViewMatrix) {
        modelMatrix.identity().translate(position.minX(), position.minY(), position.minZ());
        modelViewMatrix.set(lightViewMatrix);
        return modelViewMatrix.mul(modelMatrix);
    }

    public Matrix4f buildModelLightViewMatrix(ChunkPosition position, Matrix4f matrix) {

        modelLightMatrix.identity().translate(position.minX(), position.minY(), position.minZ());
        modelLightViewMatrix.set(matrix);
        return modelLightViewMatrix.mul(modelLightMatrix);
    }


    public Matrix4f buildEntityModelViewMatrix(Entity entity) {
        AxisAlignedBB position = entity.body();
        // Vector3f position = entity.getPosition();
        float anim2 = (float) (Math.sin(System.currentTimeMillis() % 2000.0 / 2000 * 2 * Math.PI) * 0.1f) + 0.4f;
        modelMatrix.identity().translate((float) (position.minX + 0.125f )/4 ,(float)( position.minY)/4 + anim2, (float)(position.minZ + 0.125f)/4).

                // rotateX((float)Math.toRadians(-rotation.x)).
                        rotateY((float) Math.toRadians((float) ((System.currentTimeMillis() % 10801.0 / 30)))).
                // rotateZ((float)Math.toRadians(-rotation.z)).
                        scale(0.25f);
        // modelViewMatrix.set(matrix);
        return modelViewMatrix.set(modelMatrix);
    }


}

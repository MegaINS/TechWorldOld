package ru.megains.techworld.client.render.gui


import ru.megains.techworld.common.utils.Vec3f


class GuiRenderInfo(val position: Vec3f, val rotation: Vec3f, val scale: Float) {

    def this(xPos: Int, yPos: Int, xRot: Int, yRot: Int, zRot: Int, scale: Float) = {
        this(new Vec3f(xPos, yPos, 0), new Vec3f(xRot, yRot, zRot), scale)
    }

    def this(xPos: Int, yPos: Int) = {
        this(xPos, yPos, -25, 45, 0, 25)
    }


}

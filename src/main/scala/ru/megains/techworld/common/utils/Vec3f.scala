package ru.megains.techworld.common.utils

import org.joml.Vector3f


class Vec3f(xIn: Double, yIn: Double, zIn: Double) extends Vector3f(xIn toFloat, yIn toFloat, zIn toFloat) {

    def this() {
        this(0, 0, 0)
    }


    def getIntermediateWithXValue(vec: Vector3f, x: Double): Vec3f = {
        val d0: Float = vec.x - this.x
        val d1: Float = vec.y - this.y
        val d2: Float = vec.z - this.z

        if (d0 * d0 < 1.0000000116860974E-7D) {
            null
        } else {
            val d3: Double = (x - this.x) / d0
            if (d3 >= 0.0D && d3 <= 1.0D) new Vec3f(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3)
            else null

        }
    }


    def getIntermediateWithYValue(vec: Vector3f, y: Double): Vec3f = {
        val d0: Float = vec.x - this.x
        val d1: Float = vec.y - this.y
        val d2: Float = vec.z - this.z

        if (d1 * d1 < 1.0000000116860974E-7D) {
            null
        } else {

            val d3: Double = (y - this.y) / d1
            if (d3 >= 0.0D && d3 <= 1.0D) new Vec3f(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3)
            else null
        }
    }


    def getIntermediateWithZValue(vec: Vector3f, z: Double): Vec3f = {
        val d0: Float = vec.x - this.x
        val d1: Float = vec.y - this.y
        val d2: Float = vec.z - this.z

        if (d2 * d2 < 1.0000000116860974E-7D) {
            null
        } else {
            val d3: Double = (z - this.z) / d2
            if (d3 >= 0.0D && d3 <= 1.0D) new Vec3f(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3)
            else null
        }
    }

    override def add(x: Float, y: Float, z: Float): Vec3f = {
        this.x += x
        this.y += y
        this.z += z
        this
    }

    override def sub(x: Float, y: Float, z: Float): Vec3f = {
        this.x -= x
        this.y -= y
        this.z -= z
        this
    }
}

package ru.megains.techworld.common.world

import scala.collection.mutable

class WorldHeightMap(val seed: Int) {

    val heightMap: mutable.HashMap[Long, Int] = new mutable.HashMap[Long, Int]

    def getHeight(x: Int, z: Int): Int = {
        heightMap.getOrElseUpdate( getIndex(x, z), generateHeight(x, z))
    }

    def generateHeightMap(x: Int, z: Int): Array[Array[Int]] = {
        val array: Array[Array[Int]] = new Array[Array[Int]](16)
        for (i <- 0 to 15) {
            array(i) = new Array[Int](16)
        }

        val heightMinXMinZ: Float = getHeight(x, z)
        val heightMaxXMinZ: Float = getHeight(x + 1, z)
        val heightMinXMaxZ: Float = getHeight(x, z + 1)
        val heightMaxXMaxZ: Float = getHeight(x + 1, z + 1)


        val size: Float = 16
        val ad: Float = heightMinXMaxZ - heightMinXMinZ
        val bc: Float = heightMaxXMaxZ - heightMaxXMinZ

        for (i <- 1 to 16) {

            array(0)(i - 1) = heightMinXMinZ + (ad / (size / i)) toInt
        }
        for (i <- 1 to 16) {
            array(15)(i - 1) = heightMaxXMinZ + (bc / (size / i)) toInt
        }
        for (i <- 1 to 14; j <- 1 to 16) {

            val off = array(15)(j - 1) - array(0)(j - 1)
            array(i)(j - 1) = array(0)(j - 1) + (off / (size / i)) toInt
        }
        array
    }


    def generateHeight(x: Int, z: Int): Int = {
        val a = seed >> 1 & seed
        val b = x >> 2
        val c = z >> 1
        val d = x << 2 | z >> 2
        (seed & x | ((z | a) ^ b | c ^ d) ^ ((b | a) | d)) & 31
    }

    def getIndex(x: Long, z: Long): Long = (x & 16777215l) << 24 | (z & 16777215l)
}

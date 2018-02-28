package ru.megains.techworld.common.world.region

import java.io.{DataInputStream, DataOutputStream}

import scala.collection.mutable
import scala.reflect.io.{Directory, File, Path}

class RegionLoader(worldDirectory: Directory) {

    val regionByIndex = new mutable.HashMap[Long, Region]()
    val FILE_EXTENSION: String = ".tw"
    val regionDirectory: Directory = worldDirectory / Path("region").toDirectory
    if (!regionDirectory.exists) regionDirectory.createDirectory()

    def getRegion(chunkX: Int, chunkY: Int, chunkZ: Int): Region = {
        val index = getIndex(chunkX >> 4, chunkY >> 4, chunkZ >> 4)
        if (regionByIndex.contains(index)) {
            regionByIndex(index)
        } else {

            val regionFile: File = regionDirectory / Path(s"reg.${chunkX >> 4}.${chunkY >> 4}.${chunkZ >> 4}" + FILE_EXTENSION) toFile

            if (!regionFile.exists) {
                regionFile.createFile()
            }
            val region = new Region(regionFile)
            regionByIndex += index -> region
            region
        }
    }

    def close(): Unit = {
        regionByIndex.values.foreach(_.close())
    }

    def getIndex(chunkX: Long, chunkY: Long, chunkZ: Long): Long = (chunkX & 16777215l) << 40 | (chunkZ & 16777215l) << 16 | (chunkY & 65535L)


    def getChunkInputStream(chunkX: Int, chunkY: Int, chunkZ: Int): DataInputStream = {
        getRegion(chunkX, chunkY, chunkZ).getChunkDataInputStream(chunkX & 15, chunkY & 15, chunkZ & 15)
    }

    def getChunkOutputStream(chunkX: Int, chunkY: Int, chunkZ: Int): DataOutputStream = {
        getRegion(chunkX, chunkY, chunkZ).getChunkDataOutputStream(chunkX & 15, chunkY & 15, chunkZ & 15)
    }
}

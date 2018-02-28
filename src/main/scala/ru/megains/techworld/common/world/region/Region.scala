package ru.megains.techworld.common.world.region

import java.io._

import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.region.Region.ChunkOutputStream

import scala.collection.mutable.ArrayBuffer
import scala.reflect.io.File

class Region(regionFile: File) extends Logger[Region] {


    val file = new RandomAccessFile(regionFile.jfile, "rw")
    val offsets = new Array[Int](4096)
    val emptySegments = new ArrayBuffer[Boolean]()
    initRegion()


    def initRegion(): Unit = {
        val offsetLength = Region.OFFSET_LENGTH / Region.SEGMENT_LENGTH
        for (_ <- 0 until offsetLength) {
            emptySegments += false
        }
        if (file.length() < Region.OFFSET_LENGTH) {
            for (_ <- 0 until offsetLength) {
                file.write(Region.VOID_SEGMENT)
            }
        } else {
            for (i <- offsets.indices) {

                offsets(i) = file.readInt()
            }
            val segments = file.length() / Region.SEGMENT_LENGTH - offsetLength toInt
            var notEmptySegment = 0



            for (i <- offsetLength until segments + offsetLength) {
                file.seek(i * Region.SEGMENT_LENGTH)
                if (notEmptySegment == 0) {
                    val length = file.readByte()
                    if (length == 0) {
                        emptySegments += true
                    } else {
                        notEmptySegment = length
                        emptySegments += false
                    }
                } else {
                    notEmptySegment -= 1
                    emptySegments += false
                }
            }


        }
    }

    def writeChunk(index: Int, buf: Array[Byte], count: Int): Unit = {
        var offset = offsets(index)
        val segmentNeed = (count + 1) / Region.SEGMENT_LENGTH + 1
        var updateOffset = true

        if (offset != 0) {
            file.seek(offset)
            val segmentBusy = file.readByte()
            val firstSegment = offset / Region.SEGMENT_LENGTH

            for (i <- firstSegment until firstSegment + segmentBusy) {
                emptySegments(i) = true
            }
            if (segmentBusy >= segmentNeed) {
                updateOffset = false
            }
        }

        if (updateOffset) {
            offset = getEmptySegmentsForLength(segmentNeed) * Region.SEGMENT_LENGTH
        }


        file.seek(offset)
        file.writeByte(segmentNeed)
        file.write(buf, 0, Region.SEGMENT_LENGTH * segmentNeed - 1)
        val firstSegment = offset / Region.SEGMENT_LENGTH
        val lastSegment = firstSegment + segmentNeed



        for (i <- firstSegment until lastSegment) {
            if (i < emptySegments.length) {
                emptySegments(i) = false
            } else {
                emptySegments += false
            }

        }

        setOffset(index, offset)
    }

    def setOffset(index: Int, offset: Int) {
        offsets(index) = offset
        file.seek(index * 4)
        file.writeInt(offset)
    }

    def getEmptySegmentsForLength(segmentNeed: Int): Int = {
        for (i <- 0 to emptySegments.length - segmentNeed) {
            var empty = true
            for (j <- 0 until segmentNeed) {
                if (!emptySegments(j + i)) empty = false
            }
            if (empty) return i
        }
        emptySegments.length
    }


    def getChunkDataInputStream(x: Int, y: Int, z: Int): DataInputStream = {

        if (outOfBounds(x, y, z)) return null
        val index = getIndex(x, y, z)
        val offset = offsets(index)
        if (offset == 0) return null
        file.seek(offset)
        val sizeSegments = file.readByte()
        if (sizeSegments == 0) return null
        val array = new Array[Byte](sizeSegments * Region.SEGMENT_LENGTH - 1)

        file.read(array)
        new DataInputStream(new ByteArrayInputStream(array))
    }

    def getIndex(x: Int, y: Int, z: Int): Int = x << 8 | y << 4 | z

    def outOfBounds(x: Int, y: Int, z: Int): Boolean = {
        x < 0 || x > 15 || y < 0 || y > 15 || z < 0 || z > 15
    }

    def getChunkDataOutputStream(x: Int, y: Int, z: Int): DataOutputStream = {
        if (outOfBounds(x, y, z)) null
        else new DataOutputStream(new ChunkOutputStream(getIndex(x, y, z), this))
    }


    def close() {
        if (file != null) file.close()
    }
}

object Region {


    val OFFSET_LENGTH = 16384
    val SEGMENT_LENGTH = 2048
    val VOID_SEGMENT = new Array[Byte](SEGMENT_LENGTH)

    private class ChunkOutputStream(index: Int, region: Region) extends ByteArrayOutputStream(16384) {

        override def close(): Unit = {
            region.writeChunk(index, buf, count)
        }
    }

}
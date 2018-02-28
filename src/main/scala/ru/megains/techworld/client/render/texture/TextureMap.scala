package ru.megains.techworld.client.render.texture

import java.nio.ByteBuffer

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30._
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.register.GameRegister

import scala.collection.mutable


class TextureMap() extends ATexture with TTextureRegister with Logger[TextureMap] {

    val missingTexture = new TextureAtlas("blocks/missing")
    var list: List[TextureAtlas] = _
    var map: Array[Array[Boolean]] = _
    var width: Int = 1
    var height: Int = 1
    val textureBlockMap: mutable.HashMap[String, TextureAtlas] = new mutable.HashMap[String, TextureAtlas]

    override def loadTexture(): Boolean = {
        registerAllTexture()
        loadTextureAtlas()
        true
    }

    def registerAllTexture(): Unit = {
        textureBlockMap += "missing" -> missingTexture
        GameRegister.getBlocks.foreach(_.registerTexture(this))
        GameRegister.getItems.foreach(_.registerTexture(this))
    }

    def loadTextureAtlas(): Unit = {

        textureBlockMap.values.foreach(_.loadTexture())
        list = textureBlockMap.values.toList.sortBy(_.width).reverse
        createTexture()
        updateTexture()
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)
        glGenerateMipmap(GL_TEXTURE_2D)
    }

    override def registerTexture(textureName: String): TextureAtlas = {

        if (textureBlockMap.contains(textureName)) return textureBlockMap(textureName)
        val tTexture = new TextureAtlas("blocks/" + textureName)
        if (!tTexture.isMissingTexture) {
            textureBlockMap += textureName -> tTexture
            tTexture
        } else {
            log.error("Missing texture = " + textureName)
            missingTexture
        }
    }

    def updateTexture(): Unit = {
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)
        list.foreach(_.updateTexture(width, height))
    }

    def createTexture(): Unit = {

        map = Array.ofDim[Boolean](width, height)
        list.foreach((tex: TextureAtlas) => {
            searchBox(tex.height, tex)
        })
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)

        val byteBuffer: ByteBuffer = null
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
        //   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 4)
        //  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_LOD, 0)
        //  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LOD, 4)
        //   glTexParameteri(GL_TEXTURE_2D, 34049, 0)
        //  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR)
        //  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer)
        log.info("Create texture block map " + width + "-" + height + " pixels")
    }

    def searchBox(size: Int, tex: TextureAtlas): Boolean = {

        for (x <- 0 to (map.length - size); y <- 0 to (map(x).length - size)) {
            var boxEmpty: Boolean = true

            for (i <- x until x + size; j <- y until y + size) {
                if (map(i)(j)) boxEmpty = false
            }

            if (boxEmpty) {
                for (i <- x until x + size; j <- y until y + size) {
                    map(i)(j) = true
                }
                tex.startX = x
                tex.startY = y
                return true
            }
        }
        resizeMap()
        searchBox(size, tex)
    }

    def resizeMap(): Unit = {
        width *= 2
        height *= 2
        val temp = Array.ofDim[Boolean](width, height)
        for (x <- map.indices; y <- map(x).indices) {
            temp(x)(y) = map(x)(y)
        }
        map = temp
    }

}

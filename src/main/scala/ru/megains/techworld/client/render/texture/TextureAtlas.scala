package ru.megains.techworld.client.render.texture

import java.nio.ByteBuffer

import de.matthiasmann.twl.utils.PNGDecoder
import de.matthiasmann.twl.utils.PNGDecoder.Format
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL12._


class TextureAtlas(val name: String) {

    // io.Source.fromFile("/textures/"+ name+".png")
    val is = getClass.getResourceAsStream("/textures/" + name + ".png")
    var averageU: Float = _
    var minV: Float = _
    var minU: Float = _
    var maxU: Float = _
    var averageV: Float = _
    var width: Int = _
    var height: Int = _
    var maxV: Float = _
    var byteByf: ByteBuffer = _
    var startX: Int = 0
    var startY: Int = 0

    def isMissingTexture = is == null


    def loadTexture(): Unit = {

        val png = new PNGDecoder(is)
        width = png.getWidth
        height = png.getHeight
        byteByf = ByteBuffer.allocateDirect(width * height * 4)
        png.decode(byteByf, width * 4, Format.RGBA)
        byteByf.flip()
    }

    def updateTexture(widthAll: Float, heightAll: Float): Unit = {
        minU = startX / widthAll
        maxU = (startX + width) / widthAll
        minV = startY / heightAll
        maxV = (startY + height) / heightAll
        averageU = (minU + maxU) / 2
        averageV = (minV + maxV) / 2

        glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, startX, startY, width, height, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8_REV, byteByf)
    }


}

package ru.megains.techworld.client.render.font

import java.io.IOException

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11._
import org.lwjgl.stb.{STBTTBakedChar, STBTruetype}
import ru.megains.techworld.client.render.texture.ATexture
import ru.megains.techworld.common.utils.IOUtil


class Font(val name: String) extends ATexture {


    val height = 24
    val BITMAP_W = 1024
    val BITMAP_H = 1024
    val cdata: STBTTBakedChar.Buffer = STBTTBakedChar.malloc(2000)

    override def loadTexture(): Boolean = {
        val texID = getGlTextureId
        try {
            val ttf = IOUtil.ioResourceToByteBuffer(s"fonts/$name.ttf", 160 * 1024)
            val bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H)
            STBTruetype.stbtt_BakeFontBitmap(ttf, 24, bitmap, BITMAP_W, BITMAP_H, 0, cdata)
            glBindTexture(GL_TEXTURE_2D, texID)
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap)
        } catch {
            case e: IOException =>
                throw new RuntimeException(e)
        }
        true
    }
}

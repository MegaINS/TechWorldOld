package ru.megains.techworld.client.render.texture

import org.lwjgl.opengl.GL11


abstract class ATexture() {

    var glTextureId: Int = -1

    def loadTexture(): Boolean

    def getGlTextureId: Int = {
        if (glTextureId == -1) {
            glTextureId = GL11.glGenTextures
        }
        glTextureId
    }

    def deleteGlTexture() {
        if (glTextureId != -1) {
            GL11.glDeleteTextures(glTextureId)
            glTextureId = -1
        }
    }


}

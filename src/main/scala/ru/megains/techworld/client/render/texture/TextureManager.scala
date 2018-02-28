package ru.megains.techworld.client.render.texture

import org.lwjgl.opengl.GL11

import scala.collection.mutable

class TextureManager {

    val textureMapBlock = new TextureMap()
    val mapATexture: mutable.HashMap[String, ATexture] = new mutable.HashMap[String, ATexture]
    var currentTexture: String = _


    def bindTexture(name: String) {

        if (!name.equals(currentTexture)) {
            currentTexture = name
            val aTexture: ATexture = getTexture(currentTexture)
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, aTexture.getGlTextureId)
        }
    }

    def getTexture(name: String): ATexture = {
        mapATexture.getOrElse(name, default = {
            val aTexture = new SimpleTexture(name)
            loadTexture(name, aTexture)
            aTexture
        })
    }

    def loadTexture(name: String, aTexture: ATexture): Boolean = {

        if (aTexture.loadTexture()) {
            mapATexture += name -> aTexture
            true
        } else {
            println("Error load texture " + name)
            mapATexture += name -> TextureManager.missingTexture
            false
        }

    }


}

object TextureManager {
    val missingTexture = new SimpleTexture("missing")
    missingTexture.loadTexture()
    val locationBlockTexture: String = "texture/blocks.png"
}

package ru.megains.techworld.client.render.mesh

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import ru.megains.techworld.client.render.Renderer
import ru.megains.techworld.client.render.texture.TextureManager

import scala.collection.mutable.ArrayBuffer

class Mesh private[mesh](val makeMode: Int, val vertexCount: Int) {

    val vaoId: Int = glGenVertexArrays
    val vboIdList: ArrayBuffer[Int] = ArrayBuffer[Int]()
    var clear: Boolean = false

    def this(makeMode: Int, indices: Array[Int], positions: Array[Float], colours: Array[Float]) {
        this(makeMode, indices.length)
        glBindVertexArray(vaoId)
        bindArray(0, 3, positions)
        bindArray(1, 4, colours)
        bindArrayIndices(indices)
        glBindVertexArray(0)
    }

    def bindArray(index: Int, size: Int, array: Array[Float]): Unit = {
        val vboId: Int = glGenBuffers
        vboIdList += vboId

        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        val buffer = BufferUtils.createFloatBuffer(array.length).put(array)
        buffer.flip()
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    def bindArrayIndices(array: Array[Int]): Unit = {
        val vboId: Int = glGenBuffers
        vboIdList += vboId
        val buffer = BufferUtils.createIntBuffer(array.length).put(array)
        buffer.flip()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    def render(textureManager: TextureManager) {
        initRender(textureManager)
        glDrawElements(makeMode, vertexCount, GL_UNSIGNED_INT, 0)
        endRender()
    }

    private[mesh] def initRender(textureManager: TextureManager) {
        Renderer.currentShaderProgram.setUniform("useTexture", true)
        glBindVertexArray(vaoId)
        for (id <- 0 to vboIdList.size) {
            glEnableVertexAttribArray(id)
        }
    }

    private[mesh] def endRender() {
        for (id <- 0 to vboIdList.size) {
            glDisableVertexAttribArray(id)
        }
        glBindVertexArray(0)
    }

    def cleanUp() {
        if (!clear) {
            clear = true
            for (id <- 0 to vboIdList.size) {
                glDisableVertexAttribArray(id)
            }
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            for (id <- vboIdList) {
                glDeleteBuffers(id)
            }
            glBindVertexArray(0)
            glDeleteVertexArrays(vaoId)

        }
    }

    override def finalize(): Unit = {
        cleanUp()
        super.finalize()
    }
}


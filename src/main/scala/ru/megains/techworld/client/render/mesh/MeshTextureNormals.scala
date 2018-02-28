package ru.megains.techworld.client.render.mesh

import org.lwjgl.opengl.GL30._

class  MeshTextureNormals(makeMode: Int, textureName: String, indices: Array[Int], positions: Array[Float], colours: Array[Float], textCoords: Array[Float], normals: Array[Float])
        extends MeshTexture(makeMode, textureName, indices, positions, colours, textCoords,true) {

    glBindVertexArray(vaoId)
    bindArray(3, 3, normals)
    glBindVertexArray(0)

}

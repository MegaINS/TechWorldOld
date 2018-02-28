package ru.megains.techworld.client.render.api

import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.client.render.texture.TextureManager

abstract class ARenderItem {

    val inventoryMesh: Mesh

    val worldMesh: Mesh

    def renderInInventory(textureManager: TextureManager): Unit

    def renderInWorld(textureManager: TextureManager): Unit

}

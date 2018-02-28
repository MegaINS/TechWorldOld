package ru.megains.techworld.client.render.tileentity

import ru.megains.techworld.client.render.texture.TextureManager
import ru.megains.techworld.common.tileentity.TileEntity
import ru.megains.techworld.common.world.World

trait TRenderTileEntity {


    def init():Unit


    def render(tileEntity: TileEntity, world: World,textureManager: TextureManager): Boolean
}

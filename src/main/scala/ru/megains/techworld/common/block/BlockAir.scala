package ru.megains.techworld.common.block

class BlockAir extends Block("air"){

    override def isOpaqueCube: Boolean = false

   // override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    override def getSelectedBoundingBox(blockState: BlockState) = Block.NULL_AABB
}

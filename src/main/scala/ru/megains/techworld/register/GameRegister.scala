package ru.megains.techworld.register

import ru.megains.techworld.client.render.api.{ARenderBlock, ARenderItem}
import ru.megains.techworld.client.render.block.RenderBlockStandart
import ru.megains.techworld.client.render.item.{RenderItemBlock, RenderItemStandart}
import ru.megains.techworld.client.render.tileentity.TRenderTileEntity
import ru.megains.techworld.common.block.Block
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.item.{Item, ItemBlock}
import ru.megains.techworld.common.tileentity.TileEntity

object GameRegister {



    private val blockData = new RegisterNamespace[Block] with RegisterRender[ARenderBlock] {
       override val default: ARenderBlock = RenderBlockStandart
   }
    private val itemData = new RegisterNamespace[Item] with RegisterRender[ARenderItem] {
        override val default = null
    }
    val tileEntityData = new RegisterNamespace[Class[_<:TileEntity]] with RegisterRender[TRenderTileEntity] {
        override val default = null
    }

    val entityData = new RegisterNamespace[Class[_<:Entity]]/* with RegisterRender[TRenderTileEntity]{
        override val default = null
    }*/

    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Unit = {
        privateRegisterTileEntity(id, tileEntity)
    }

    def getTileEntityById(id: Int):Class[_<:TileEntity] = {
        tileEntityData.getObject(id)
    }

    def getIdByTileEntity(tileEntity: Class[_ <: TileEntity]): Int = {
        tileEntityData.getIdByObject(tileEntity)
    }
    private def privateRegisterTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean = {
        if (tileEntityData.contains(tileEntity)) {
            println("TileEntity \"" + tileEntity.toString + "\" already register")
        } else {
            if (tileEntityData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (tileEntityData.contains(tileEntity.toString)) {
                    println("Name \"" + tileEntity.toString + "\" not single")
                } else {
                    tileEntityData.registerObject(id, tileEntity.toString, tileEntity)
                    return true
                }
            }
        }
        false
    }

    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Unit = {
        privateRegisterEntity(id, tileEntity)
    }

    def getEntityById(id: Int):Class[_<:Entity] = {
        entityData.getObject(id)
    }

    def getIdByEntity(tileEntity: Class[_ <: Entity]): Int = {
        entityData.getIdByObject(tileEntity)
    }
    private def privateRegisterEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean = {
        if (entityData.contains(tileEntity)) {
            println("Entity \"" + tileEntity.toString + "\" already register")
        } else {
            if (entityData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (entityData.contains(tileEntity.toString)) {
                    println("Name \"" + tileEntity.toString + "\" not single")
                } else {
                    entityData.registerObject(id, tileEntity.toString, tileEntity)
                    return true
                }
            }
        }
        false
    }

    def registerBlock(id: Int, block: Block): Unit = {
        if (privateRegisterBlock(id, block)) {

            val item = new ItemBlock(block)

            if (privateRegisterItem(id, item)) {
                itemData.registerRender(id, new RenderItemBlock(item))
            }
        }
    }

    private def privateRegisterBlock(id: Int, block: Block): Boolean = {
        if (blockData.contains(block)) {
            println("Block \"" + block.name + "\" already register")
        } else {
            if (blockData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (blockData.contains(block.name)) {
                    println("Name \"" + block.name + "\" not single")
                } else {
                    blockData.registerObject(id, block.name, block)
                    return true
                }
            }
        }
        false
    }

    def registerItem(id: Int, item: Item): Unit = {
        if (privateRegisterItem(id, item)) {
            itemData.registerRender(id, new RenderItemStandart(item))
        }
    }

    private def privateRegisterItem(id: Int, item: Item): Boolean = {
        if (itemData.contains(item)) {
            println("Item \"" + item.name + "\" already register")
        } else {
            if (itemData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (itemData.contains(item.name)) {
                    println("Name \"" + item.name + "\" not single")
                } else {
                    itemData.registerObject(id, item.name, item)

                    return true
                }
            }
        }
        false
    }


    def getBlockFromItem(item: Item): Block = blockData.getObject(itemData.getIdByObject(item))


    def getItemFromBlock(block: Block): Item = itemData.getObject(blockData.getIdByObject(block))

    def getBlocks: Iterable[Block] = blockData.getObjects

    def getItems: Iterable[Item] = itemData.getObjects

    def getBlockById(id: Int): Block = blockData.getObject(id)

    def getItemById(id: Int): Item = itemData.getObject(id)

    def getBlockByName(name: String): Block = blockData.getObject(name)

    def registerBlockRender(block: Block, aRenderBlock: ARenderBlock): Unit = {
        val id: Int = getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, aRenderBlock)
        } else {
            println("Block +\"" + block.name + "\" not register")
        }
    }

    def registerTileEntityRender(tileEntity: Class[_<:TileEntity], aRenderBlock: TRenderTileEntity): Unit = {
        val id: Int = getIdByTileEntity(tileEntity)
        if (id != -1) {
            tileEntityData.registerRender(id, aRenderBlock)
        } else {
            println("Block +\"" + tileEntity.toString + "\" not register")
        }
    }

    def getTileEntityRender(tileEntity: Class[_<:TileEntity]): TRenderTileEntity = tileEntityData.getRender(getIdByTileEntity(tileEntity))

    def getBlockRender(block: Block): ARenderBlock = blockData.getRender(getIdByBlock(block))

    def getIdByBlock(block: Block): Int = blockData.getIdByObject(block)

    def getItemRender(item: Item): ARenderItem = itemData.getRender(getIdByItem(item))

    def getIdByItem(item: Item): Int = itemData.getIdByObject(item)

    def getItemByName(name: String): Item = itemData.getObject(name)



}

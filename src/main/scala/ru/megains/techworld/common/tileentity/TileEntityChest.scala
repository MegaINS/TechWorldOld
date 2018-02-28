package ru.megains.techworld.common.tileentity

import ru.megains.techworld.client.render.gui.{GuiChest, GuiContainer}
import ru.megains.techworld.common.block.blockdata.BlockPos
import ru.megains.techworld.common.container.{Container, ContainerTest}
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.World



class TileEntityChest(pos:BlockPos, world: World) extends TileEntityInventory(pos, world,40) with Logger[TileEntityChest]{

    log.info("Set tile entity")


    override def update(world: World): Unit = {

    }



    override def getGui(player: EntityPlayer): GuiContainer = new GuiChest(player.inventory,this)

    override def getContainer(player: EntityPlayer): Container = new ContainerTest(player.inventory,this)
}

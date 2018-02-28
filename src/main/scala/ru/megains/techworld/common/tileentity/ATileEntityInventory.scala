package ru.megains.techworld.common.tileentity

import ru.megains.techworld.client.render.gui.GuiContainer
import ru.megains.techworld.common.container.Container
import ru.megains.techworld.common.entity.player.EntityPlayer

trait ATileEntityInventory {

    def getContainer(player: EntityPlayer): Container

    def getGui(player: EntityPlayer): GuiContainer

}


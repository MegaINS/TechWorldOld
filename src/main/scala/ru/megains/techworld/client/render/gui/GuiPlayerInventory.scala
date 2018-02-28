package ru.megains.techworld.client.render.gui

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.common.entity.player.EntityPlayer

class GuiPlayerInventory(entityPlayer: EntityPlayer) extends GuiContainer(entityPlayer.inventoryContainer) {


    var playerInventory: Mesh = _

    override def initGui(orangeCraft: TechWorldClient): Unit = {
        playerInventory = createTextureRect(500, 240, "gui/playerInventory")
    }



    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawObject(playerInventory, 150, 0)
        super.drawScreen(mouseX, mouseY)
    }

    override def cleanup(): Unit = {
        playerInventory.cleanUp()
    }
}

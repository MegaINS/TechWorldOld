package ru.megains.techworld.client.render.gui

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.common.container.ContainerTest
import ru.megains.techworld.common.inventory.InventoryPlayer
import ru.megains.techworld.common.tileentity.TileEntityChest

class GuiChest(inventoryPlayer: InventoryPlayer,inventoryTest: TileEntityChest) extends GuiContainer(new ContainerTest(inventoryPlayer,inventoryTest)){

    var chestInventory: Mesh = _


    override def initGui(orangeCraft: TechWorldClient): Unit = {
        chestInventory = createTextureRect(500, 440, "gui/chestInventory")
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawObject(chestInventory, 150, 0)
        super.drawScreen(mouseX, mouseY)
    }

    override def cleanup(): Unit = {
        chestInventory.cleanUp()
    }
}

package ru.megains.techworld.client.render.gui


import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.mesh.Mesh

class GuiHotBar extends GuiInGame {

    var hotBar: Mesh = _
    var stackSelect: Mesh = _

    override def initGui(orangeCraft: TechWorldClient): Unit = {
        stackSelect = createTextureRect(56, 54, "gui/stackSelect")
        hotBar = createTextureRect(484, 52, "gui/hotBar")
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        val inventory = oc.player.inventory

        drawObject(hotBar, 158, 0)
        drawObject(stackSelect, 156 + inventory.stackSelect * 48, 0)
        for (i <- 0 to 9) {
            drawItemStack(inventory.getStackInSlot(i), 164 + i * 48, 6)
        }


    }

    override def cleanup(): Unit = {
        hotBar.cleanUp()
        stackSelect.cleanUp()
    }
}

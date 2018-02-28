package ru.megains.techworld.client.render.gui

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.mesh.Mesh

class GuiUI extends GuiInGame{

    var target: Mesh = _

    override def initGui(orangeCraft: TechWorldClient): Unit = {
        target = createTextureRect(40, 40, "gui/target")

    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        drawObject(target, 380, 280)

    }

    override def cleanup(): Unit = {
        target.cleanUp()

    }
}

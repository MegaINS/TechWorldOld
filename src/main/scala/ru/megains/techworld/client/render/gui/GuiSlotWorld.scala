package ru.megains.techworld.client.render.gui

import java.awt.Color

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.gui.Element.GuiElement
import ru.megains.techworld.client.render.mesh.Mesh



class GuiSlotWorld(id: Int, val worldName: String, orangeCraft: TechWorldClient) extends GuiElement(orangeCraft) {

    val weight: Int = 400
    val height: Int = 80
    val positionX: Int = 100
    val positionY: Int = 500 - 110 * id

    val textMesh: Mesh = createString(worldName, Color.WHITE)
    val slotSelect = createRect(weight, height, Color.darkGray)
    val slot = createRect(weight, height, Color.BLACK)
    var select: Boolean = false


    def draw(mouseX: Int, mouseY: Int): Unit = {

        val background: Mesh = if (select) slotSelect else slot

        drawObject(positionX, positionY, 1, background)

        drawObject(positionX + 10, positionY + 30, 2, textMesh)

    }

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= positionX && mouseX <= positionX + weight && mouseY >= positionY && mouseY <= positionY + height
    }
}

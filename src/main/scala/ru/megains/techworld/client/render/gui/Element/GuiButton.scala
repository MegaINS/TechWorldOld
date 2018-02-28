package ru.megains.techworld.client.render.gui.Element

import java.awt.Color

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.mesh.Mesh



class GuiButton(val id: Int, oc: TechWorldClient, val buttonText: String, positionX: Int, positionY: Int, weight: Int, height: Int) extends GuiElement(oc) {


    val textMesh: Mesh = createString(buttonText, Color.WHITE)
    val buttonUp: Mesh = createRect(weight, height, Color.WHITE)
    val buttonDown: Mesh = createRect(weight, height, Color.darkGray)
    val buttonDisable: Mesh = createRect(weight, height, Color.BLACK)
    var enable = true


    def draw(mouseX: Int, mouseY: Int): Unit = {

        val background: Mesh = if (!enable) buttonDisable else if (isMouseOver(mouseX, mouseY)) buttonDown else buttonUp

        drawObject(positionX, positionY, 1, background)

        drawObject(positionX + weight / 2, positionY + height / 2, 1, textMesh)

    }

    def clear(): Unit = {
        textMesh.cleanUp()
        buttonDown.cleanUp()
        buttonUp.cleanUp()
    }

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        enable && mouseX >= positionX && mouseX <= positionX + weight && mouseY >= positionY && mouseY <= positionY + height
    }


}

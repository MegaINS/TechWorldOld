package ru.megains.techworld.client.render.gui

import java.awt.Color

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.common.container.Container
import ru.megains.techworld.common.entity.player.EntityPlayer
import ru.megains.techworld.common.inventory.Slot


abstract class GuiContainer(val inventorySlots: Container) extends GuiScreen {

    val rect: Mesh = createRect(40, 40, new Color(200, 255, 100, 100))

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        inventorySlots.inventorySlots.foreach(
            (slot) => {
                drawSlot(slot)
                if (isMouseOverSlot(slot, mouseX, mouseY)) {
                    drawObject(rect, slot.xPos, slot.yPos)
                }
            }
        )

        drawItemStack(oc.player.inventory.itemStack, mouseX - 20, mouseY - 15)


    }

    override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_E | GLFW_KEY_ESCAPE => oc.guiManager.setGuiScreen(null)
            case _ =>
        }

    }

    def isMouseOverSlot(slot: Slot, mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= slot.xPos && mouseX <= slot.xPos + 40 && mouseY >= slot.yPos && mouseY <= slot.yPos + 40
    }

    def drawSlot(slot: Slot): Unit = {
        drawItemStack(slot.getStack, slot.xPos, slot.yPos)
    }

    override def cleanup(): Unit = {}

    override def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {

        player.openContainer.mouseClicked(x, y, button, player)
      //  oc.playerController.windowClick(x, y, button, player: EntityPlayer)
    }

    def getSlotAtPosition(x: Int, y: Int): Slot = inventorySlots.inventorySlots.find(isMouseOverSlot(_, x, y)).orNull
}

package ru.megains.techworld.client.render.gui

import java.awt.Color

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.gui.Element.{GuiButton, GuiElement}
import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.common.entity.player.EntityPlayer

import scala.collection.mutable.ArrayBuffer

abstract class GuiScreen extends GuiElement {




    val buttonList: ArrayBuffer[GuiButton] = new ArrayBuffer[GuiButton]()


    override def setData(orangeCraft: TechWorldClient): Unit = {
        buttonList.foreach(_.clear())
        buttonList.clear()
        super.setData(orangeCraft)
    }


    def mouseReleased(x: Int, y: Int, button: Int): Unit = {}

    def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        if (button == 0) {
            buttonList.foreach(
                guiButton => {
                    if (guiButton.isMouseOver(x, y)) {
                        actionPerformed(guiButton)
                        return
                    }
                }
            )
        }

    }

    def mouseClickMove(x: Int, y: Int): Unit = {}

    def actionPerformed(button: GuiButton) {}

    def keyTyped(typedChar: Char, keyCode: Int) {
        keyCode match {
            case GLFW_KEY_ESCAPE => oc.guiManager.setGuiScreen(null)
            case _ =>
        }
    }


    def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        buttonList.foreach(_.draw(mouseX, mouseY))
    }

    def drawDefaultBackground(): Unit = {
        drawObject(GuiScreen.background, 0, 0)
    }

    def cleanup(): Unit = {
        buttonList.foreach(_.clear())
        buttonList.clear()
    }

    def tick(): Unit = {}
}

object GuiScreen extends Gui {

    val background: Mesh = createGradientRect(800, 600, new Color(128, 128, 128, 128), new Color(0, 0, 0, 128))
}

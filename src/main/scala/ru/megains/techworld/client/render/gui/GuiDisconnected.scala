package ru.megains.techworld.client.render.gui

import java.awt.Color

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.gui.Element.GuiButton



class GuiDisconnected(screen: GuiScreen, reasonLocalizationKey: String, chatComp: String) extends GuiScreen with GuiText {


    override def initGui(orangeCraft: TechWorldClient): Unit = {
        addText("text", createString(chatComp, Color.BLACK))
        buttonList += new GuiButton(0, oc, "Cancel", 300, 200, 200, 50)
    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 => oc.guiManager.setGuiScreen(screen)
            case _ =>
        }
    }


    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        super.drawScreen(mouseX, mouseY)
        drawObject(300, 300, 2, text("text"))
    }
}

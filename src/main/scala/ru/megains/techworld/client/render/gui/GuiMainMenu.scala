package ru.megains.techworld.client.render.gui

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.gui.Element.GuiButton


class GuiMainMenu extends GuiScreen {



    override def initGui(orangeCraft: TechWorldClient): Unit = {
        buttonList += new GuiButton(0, orangeCraft, "SingleGame", 250, 450, 300, 50)
        buttonList += new GuiButton(1, orangeCraft, "MultiplayerGame", 250, 380, 300, 50)
        buttonList += new GuiButton(2, orangeCraft, "Option", 250, 310, 300, 50)
        buttonList += new GuiButton(3, orangeCraft, "Exit game", 250, 240, 300, 50)

    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 => oc.guiManager.setGuiScreen(new GuiSelectWorld(this))
            case 1 => oc.guiManager.setGuiScreen(new GuiMultiplayer(this))
            case 3 => oc.running = false
            case _ =>
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY)
    }

}

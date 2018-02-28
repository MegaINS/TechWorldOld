package ru.megains.techworld.client.render.gui

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.periphery.Mouse

import scala.collection.mutable


class GuiManager(val techWorld: TechWorldClient) {



    private val guiInGame: mutable.HashMap[String, GuiInGame] = mutable.HashMap[String, GuiInGame]()
    private var guiScreen: GuiScreen = _

    def init(): Unit = {
        addGuiInGame("hotBar", new GuiHotBar())
        addGuiInGame("debugInfo", new GuiDebugInfo())
        addGuiInGame("blockSelect", new GuiBlockSelect())
        addGuiInGame("ui", new GuiUI())
    }

    def tick(): Unit = {
        if (guiScreen ne null) guiScreen.tick()
        if (techWorld.world ne null) guiInGame.values.filter(_ ne null).foreach(_.tick())


    }

    def addGuiInGame(name: String, gui: GuiInGame): Unit = {
        if (gui ne null) {
            gui.setData(techWorld)
        }
        guiInGame += name -> gui
    }

    def isGuiScreen: Boolean = guiScreen ne null

    def setGuiScreen(screen: GuiScreen) {
        if (guiScreen ne null) guiScreen.cleanup()

        if (screen ne null) {
            screen.setData(techWorld)
            if (guiScreen eq null) techWorld.ungrabMouseCursor()
        } else techWorld.grabMouseCursor()

        guiScreen = screen
    }

    def draw(mouseX: Int, mouseY: Int): Unit = {
        if (guiScreen ne null) {
            guiScreen.drawScreen(mouseX, mouseY)
        } else {
            guiInGame.values.filter(_ ne null).foreach(_.drawScreen(mouseX, mouseY))
        }
    }

    def removeGuiInGame(name: String): Unit = {
        guiInGame.remove(name)
    }

    def getGuiInGame(name: String): GuiInGame = {
        guiInGame.getOrElse(name, null)
    }

    def handleInput(): Unit = {
        //        while (Mouse.next()) {
        //
        //            val x = Mouse.getX
        //            val y = Mouse.getY
        //            val button = Mouse.getEventButton
        //            val buttonState = Mouse.getEventButtonState
        //            if (button == -1) {
        //                guiScreen.mouseClickMove(x, y)
        //            } else if (buttonState) {
        //                guiScreen.mouseClicked(x, y, button, orangeCraft.player)
        //            } else {
        //                guiScreen.mouseReleased(x, y, button)
        //            }
        //        }

        //
        //
        //        while (Keyboard.next()) {
        //            if (Keyboard.getEventKeyState) {
        //                guiScreen.keyTyped(Keyboard.getEventCharacter, Keyboard.getEventKey)
        //            }
        //
        //
        //        }
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            guiScreen.keyTyped(key.toChar, key)
        }
    }

    def runTickMouse(button: Int, buttonState: Boolean): Unit = {
            val x = Mouse.getX
            val y = Mouse.getY
            if (button == -1) {
                guiScreen.mouseClickMove(x, y)
            } else if (buttonState) {
                guiScreen.mouseClicked(x, y, button, techWorld.player)
            } else {
                guiScreen.mouseReleased(x, y, button)
            }

    }

    def cleanup(): Unit = {
        if (guiScreen ne null) guiScreen.cleanup()
        guiInGame.values.filter(_ ne null).foreach(_.cleanup())
    }
}

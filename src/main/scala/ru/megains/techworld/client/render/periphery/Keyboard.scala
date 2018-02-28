package ru.megains.techworld.client.render.periphery

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.TechWorldClient

object Keyboard {
    var windowId: Long = 0

    def init(window: Window, orangeCraft: TechWorldClient): Unit = {
        windowId = window.id
        glfwSetKeyCallback(windowId, (windowHnd: Long, key: Int, scancode: Int, action: Int, mods: Int) => {
            if (orangeCraft.guiManager.isGuiScreen) orangeCraft.guiManager.runTickKeyboard(key, action, mods)
            else orangeCraft.runTickKeyboard(key, action, mods)

        })


    }
}

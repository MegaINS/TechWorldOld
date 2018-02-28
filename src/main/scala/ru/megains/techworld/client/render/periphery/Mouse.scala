package ru.megains.techworld.client.render.periphery

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.TechWorldClient

object Mouse {

    var x: Double = 0
    var y: Double = 0
    var preX: Double = 0
    var preY: Double = 0

    def getY: Int = 600 - y toInt

    def getX: Int = x toInt

    def getDX: Double = x - preX

    def getDY: Double = (y - preY) * -1

    var windowId: Long = 0

    def getDWheel: Int = DWheel.toInt

    var DWheel: Double = 0

    def update(window: Window): Unit = {

        preX = x
        preY = y
        DWheel = 0
        glfwPollEvents()
    }

    def init(window: Window, orangeCraft: TechWorldClient): Unit = {
        windowId = window.id
        glfwSetCursorPosCallback(window.id, (windowHnd, xpos, ypos) => {
            x = xpos
            y = ypos
        })
        glfwSetMouseButtonCallback(window.id, (windowHnd, button, action, mods) => {
            if (orangeCraft.guiManager.isGuiScreen) orangeCraft.guiManager.runTickMouse(button, action == GLFW_PRESS)
            else orangeCraft.runTickMouse(button, action == GLFW_PRESS)


        })
        glfwSetScrollCallback(windowId, (window: Long, xoffset: Double, yoffset: Double) => {
            DWheel = yoffset
        })
    }

    def setGrabbed(mode: Boolean) = {
        if (mode) {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        } else {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL)
        }
    }
}

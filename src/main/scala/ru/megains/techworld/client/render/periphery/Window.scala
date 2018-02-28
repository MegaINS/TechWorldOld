package ru.megains.techworld.client.render.periphery

import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil.NULL

class Window {
    def getHeight(): Int = height

    def getWidth(): Int = width


    var width: Int = 800
    var height: Int = 600
    var id: Long = NULL

    def create(): Unit = {

        if (!glfwInit) {
            throw new RuntimeException("Failed to init GLFW.")
        }
        glfwWindowHint(GLFW_DEPTH_BITS, 24)
        id = glfwCreateWindow(width, height, "OrangeCraft", NULL, NULL)
        if (id == NULL) {
            glfwTerminate()
            throw new RuntimeException
        }

        glfwMakeContextCurrent(id)
        GL.createCapabilities
        glfwSwapInterval(0)
    }

    def isClose: Boolean = glfwWindowShouldClose(id)

    def update(): Unit = {

        glfwSwapBuffers(id)
    }

    def destroy(): Unit = {
        if (id != NULL) {
            glfwDestroyWindow(id)
        }
    }

}

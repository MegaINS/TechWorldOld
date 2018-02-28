package ru.megains.techworld.client.render.font

import java.awt.Color

import org.lwjgl.stb.{STBTTAlignedQuad, STBTruetype}
import org.lwjgl.system.MemoryStack
import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.mesh.{Mesh, MeshMaker}

import scala.collection.mutable


class FontRender(oc: TechWorldClient) {

    val ZPOS: Float = 0.0f
    val fontsMap: mutable.HashMap[String, Font] = new mutable.HashMap[String, Font]


    def createStringGui(text: String, color: Color, font: Font): Mesh = {
        val stack: MemoryStack = MemoryStack.stackPush
        val x = stack.floats(0.0f)
        val y = stack.floats(0.0f)
        val q = STBTTAlignedQuad.mallocStack(stack)
        val mm = MeshMaker
        mm.startMakeTriangles()
        mm.setTexture(font.name)
        mm.addColor(color)

        for (i <- text.indices) {
            val c = text.charAt(i)
            if (c == '\n') {
                y.put(0, y.get(0) + font.height)
                x.put(0, 0.0f)
            } else {
                STBTruetype.stbtt_GetBakedQuad(font.cdata, font.BITMAP_W, font.BITMAP_H, c, x, y, q, true)
                mm.setCurrentIndex()
                mm.addVertexWithUV(q.x0, q.y1 * -1, ZPOS, q.s0, q.t1)
                mm.addVertexWithUV(q.x0, q.y0 * -1, ZPOS, q.s0, q.t0)
                mm.addVertexWithUV(q.x1, q.y0 * -1, ZPOS, q.s1, q.t0)
                mm.addVertexWithUV(q.x1, q.y1 * -1, ZPOS, q.s1, q.t1)
                mm.addIndex(1, 0, 3)
                mm.addIndex(2, 1, 3)
            }
        }
        if (stack != null) stack.close()
        mm.makeMesh()
    }

    def loadFont(name: String): Font = {
        if (fontsMap.contains(name)) fontsMap(name)
        else {
            val font = new Font(name)
            oc.textureManager.loadTexture(name, font)
            fontsMap += name -> font
            font
        }
    }











}

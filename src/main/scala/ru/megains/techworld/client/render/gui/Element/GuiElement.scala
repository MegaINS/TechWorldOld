package ru.megains.techworld.client.render.gui.Element

import java.awt.Color

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.font.{FontRender, Fonts}
import ru.megains.techworld.client.render.Renderer
import ru.megains.techworld.client.render.gui.Gui
import ru.megains.techworld.client.render.item.RenderItem
import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.common.item.itemstack.ItemStack

abstract class GuiElement extends Gui {

    def this(orangeCraft: TechWorldClient) {
        this()
        setData(orangeCraft)
    }

    var itemRender: RenderItem = _
    var renderer: Renderer = _
    var fontRender: FontRender = _
    var oc: TechWorldClient = _


    def setData(orangeCraft: TechWorldClient): Unit = {
        itemRender = orangeCraft.itemRender
        renderer = orangeCraft.renderer
        fontRender = orangeCraft.fontRender
        oc = orangeCraft

        initGui(orangeCraft)
    }

    def initGui(orangeCraft: TechWorldClient): Unit = {}

    def drawObject(mesh: Mesh, xPos: Int, yPos: Int): Unit = super.drawObject(xPos, yPos, 1, mesh, renderer)

    def drawObject(xPos: Int, yPos: Int, scale: Float, mesh: Mesh): Unit = super.drawObject(xPos, yPos, scale, mesh, renderer)

    def createString(text: String, color: Color): Mesh = fontRender.createStringGui(text, color, Fonts.timesNewRomanR)

    def drawItemStack(itemStack: ItemStack, xPos: Int, yPos: Int): Unit = itemRender.renderItemStackToGui(xPos, yPos, itemStack)
}

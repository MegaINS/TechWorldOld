package ru.megains.techworld.client.render.item

import java.awt.Color

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.Renderer
import ru.megains.techworld.client.render.font.{FontRender, Fonts}
import ru.megains.techworld.client.render.gui.Gui
import ru.megains.techworld.client.render.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.item.{Item, ItemBlock}
import ru.megains.techworld.common.utils.Vec3f
import ru.megains.techworld.register.GameRegister

import scala.collection.mutable

class RenderItem(orangeCraft: TechWorldClient) extends Gui {
    val renderer: Renderer = orangeCraft.renderer
    val fontRender: FontRender = orangeCraft.fontRender
    val cub: Mesh ={
        val mm = MeshMaker.getMeshMaker
        mm.startMakeTriangles()
        mm.addColor(1, 1,1, 1)
        mm.addVertex(xZero, zZero, zZero)
        mm.addVertex(xZero, 12, zZero)
        mm.addVertex(15, 12, zZero)
        mm.addVertex(15, zZero, zZero)
        mm.addIndex(0, 2, 1)
        mm.addIndex(0, 3, 2)
        mm.makeMesh()
    } //createRect(15, 12, Color.white)
    val numberMeshMap = new mutable.HashMap[Int, Mesh]()
    val floatMeshMap = new mutable.HashMap[Float, Mesh]()


    def renderItemStackToGui(xPos: Int, yPos: Int, itemStack: ItemStack): Unit = {
        if (itemStack != null) {
            itemStack.item match {
                case block: ItemBlock =>
                    renderItemBlockToGui(xPos, yPos, block)
                case item: Item =>
                    renderItemToGui(xPos, yPos, item)

            }
            renderItemStackOverlay(xPos, yPos, itemStack)
        }
    }

    def renderItemStackOverlay(xPos: Int, yPos: Int, itemStack: ItemStack): Unit = {
        if (itemStack.stackSize > 1) {
            drawObject(xPos + 2, yPos + 2, cub, renderer)
            drawObject(xPos + 3, yPos + 3,0.5f, getNumberMesh(itemStack.stackSize), renderer)
        }
        drawObject(xPos + 13, yPos + 25, cub, renderer)
        drawObject(xPos  + 14, yPos + 26,0.5f,getNumberMesh(itemStack.stackMass) , renderer)
    }

    def renderItemBlockToGui(xPos: Int, yPos: Int, itemBlock: ItemBlock): Unit = {
        val shaderProgram = renderer.hudShaderProgram
        shaderProgram.setUniform("modelMatrix", renderer.transformation.buildOrtoProjModelMatrix(xPos + 20, yPos + 20, 0, -25, 45, 0, 25))
        shaderProgram.setUniform("colour", new Vec3f(1f, 1f, 1f))
        GameRegister.getItemRender(itemBlock).renderInInventory(renderer.textureManager)
    }

    def renderItemToGui(xPos: Int, yPos: Int, item: Item): Unit = {
        val shaderProgram = renderer.hudShaderProgram
        shaderProgram.setUniform("modelMatrix", renderer.transformation.buildOrtoProjModelMatrix(xPos + 4, yPos + 4, 1))
        shaderProgram.setUniform("colour", new Vec3f(1f, 1f, 1f))
        GameRegister.getItemRender(item).renderInInventory(renderer.textureManager)
    }

    def getNumberMesh(number: Int): Mesh = {
        numberMeshMap.getOrElse(number, default = {
            val mesh = fontRender.createStringGui(number.toString, Color.black, Fonts.timesNewRomanR)
            numberMeshMap += number -> mesh
            mesh
        })
    }
    def getNumberMesh(number: Float): Mesh = {
        floatMeshMap.getOrElse(number, default = {
            val mesh = fontRender.createStringGui(number.toString, Color.black, Fonts.timesNewRomanR)
            floatMeshMap += number -> mesh
            mesh
        })
    }
    def drawFloat(x:Int,y:Int,number: Float): Unit = {
        //Доделать
        val base:Int = number toInt
        val remain:Int = ((number - base) * 1000) toInt

       val baseMesh = numberMeshMap.getOrElse(base, default = {
            val mesh = fontRender.createStringGui(base.toString, Color.black, Fonts.timesNewRomanR)
            numberMeshMap += base -> mesh
            mesh
        })
        val remainMesh = numberMeshMap.getOrElse(remain, default = {
            val mesh = fontRender.createStringGui(remain.toString, Color.black, Fonts.timesNewRomanR)
            numberMeshMap += remain -> mesh
            mesh
        })
    }

}

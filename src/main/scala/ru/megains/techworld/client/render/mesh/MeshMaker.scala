package ru.megains.techworld.client.render.mesh

import java.awt.Color

import org.lwjgl.opengl.GL11._

import scala.collection.mutable.ArrayBuffer


object MeshMaker {


    private var posArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var colourArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var textCordsArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var normalsArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var indicesArray: ArrayBuffer[Int] = ArrayBuffer[Int]()

    private var colorR: Float = _
    private var colorG: Float = _
    private var colorB: Float = _
    private var colorA: Float = _
    private var textureU: Double = _
    private var textureV: Double = _
    private var normalX: Float = _
    private var normalY: Float = _
    private var normalZ: Float = _
    private var currentIndex: Int = _
    private var vertexCount: Int = _
    private var makeMode: Int = _
    private var isStartMake: Boolean = false
    private var isNormals: Boolean = false
    private var textureName: String = ""

    def getMeshMaker: MeshMaker.type = this

    def startMakeTriangles(): Unit = {
        startMake(GL_TRIANGLES)
    }

    def startMake(mode: Int): Unit = {
        if (isStartMake) {
            sys.error("MeshMaker has already started")
        } else {
            isStartMake = true
            reset()
            makeMode = mode
        }
    }

    def reset(): Unit = {

        posArray.clear()
        colourArray.clear()
        textCordsArray.clear()
        normalsArray.clear()
        indicesArray.clear()
        colorR = 1
        colorG = 1
        colorB = 1
        colorA = 1
        textureV = 0
        textureU = 0
        normalX = 0
        normalY = 0
        normalZ = 0
        makeMode = 0
        currentIndex = 0
        vertexCount = 0
        isNormals = false
        textureName = ""

    }

    def setTexture(textureName: String): Unit = {
        this.textureName = textureName
    }

    def setCurrentIndex(): Unit = {
        currentIndex = vertexCount
    }

    def addIndex(index: Int*) {
        index.foreach(indicesArray += _ + currentIndex)
    }

    def addIndex(index: Int) {
        indicesArray :+= index + currentIndex
    }

    def addNormals(xn: Float, yn: Float, zn: Float) {
        normalX = xn
        normalY = yn
        normalZ = zn
        isNormals = true
    }

    def addVertexWithUV(x: Double, y: Double, z: Double, u: Double, v: Double) {
        addTextureUV(u, v)
        addVertex(x, y, z)
    }

    def addVertex(x: Double, y: Double, z: Double) {
        textCordsArray += (textureU toFloat, textureV toFloat)
        colourArray += (colorR, colorG, colorB, colorA)
        normalsArray += (normalX, normalY, normalZ)
        posArray += (x toFloat, y toFloat, z toFloat)
        vertexCount += 1
    }

    def addTextureUV(u: Double, v: Double) {
        textureU = u
        textureV = v
    }

    def addColor(r: Float, g: Float, b: Float): Unit = addColor(r, g, b, 1)

    def addColor(r: Float, g: Float, b: Float, a: Float) {
        colorR = r
        colorG = g
        colorB = b
        colorA = a
    }

    def addColorRGBA(r: Int, g: Int, b: Int, a: Int): Unit = {
        addColor(r / 255f, g / 255f, b / 255f, a / 255f)
    }

    def addColor(color: Color): Unit = {
        addColorRGBA(color.getRed, color.getGreen, color.getBlue, color.getAlpha)
    }

    def makeMesh(): Mesh = {
        if (!isStartMake) {
            sys.error("MeshMaker not started")
        } else {
            var mesh: Mesh = null
            if (isNormals) {
                mesh = new MeshTextureNormals(makeMode, textureName, indicesArray.toArray, posArray.toArray, colourArray.toArray, textCordsArray.toArray, normalsArray.toArray)
            } else/* if (textureName != "")*/ {
                mesh = new MeshTexture(makeMode, textureName, indicesArray.toArray, posArray.toArray, colourArray.toArray, textCordsArray.toArray,textureName != "")
           } //else {
//                mesh = new Mesh(makeMode, indicesArray.toArray, posArray.toArray, colourArray.toArray)
//            }
            isStartMake = false
            reset()
            mesh
        }
    }


}

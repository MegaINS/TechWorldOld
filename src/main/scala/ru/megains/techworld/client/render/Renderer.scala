package ru.megains.techworld.client.render

import java.nio.FloatBuffer

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11._
import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.periphery.Mouse
import ru.megains.techworld.client.render.shader.ShaderProgram
import ru.megains.techworld.client.render.texture.TextureManager
import ru.megains.techworld.client.render.world.{RenderChunk, WorldRenderer}
import ru.megains.techworld.common.utils.{Logger, Utils}

class Renderer(tw: TechWorldClient) extends Logger[Renderer]{


    val transformation = new Transformation
    var textureManager: TextureManager = _
    var hudShaderProgram: ShaderProgram = _
    var sceneShaderProgram: ShaderProgram = _
    var frustum: Frustum = _
    var worldRenderer: WorldRenderer = _
    val FOV: Float = Math.toRadians(60.0f).toFloat
    val Z_NEAR: Float = 0.01f
    val Z_FAR: Float = 1000f
    val _proj: FloatBuffer = BufferUtils.createFloatBuffer(16)

    val _modl: FloatBuffer = BufferUtils.createFloatBuffer(16)

    def init(textureManager: TextureManager) {
        this.textureManager = textureManager
        setupSceneShader()
        setupHudShader()
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)





    }

    private def setupSceneShader() {
        sceneShaderProgram = new ShaderProgram
        sceneShaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"))
        sceneShaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"))
        sceneShaderProgram.link()

        sceneShaderProgram.createUniform("projectionMatrix")
        sceneShaderProgram.createUniform("modelViewMatrix")
        sceneShaderProgram.createUniform("useTexture")
    }

    @throws[Exception]
    private def setupHudShader() {
        hudShaderProgram = new ShaderProgram
        hudShaderProgram.createVertexShader(Utils.loadResource("/shaders/hud_vertex.vs"))
        hudShaderProgram.createFragmentShader(Utils.loadResource("/shaders/hud_fragment.fs"))
        hudShaderProgram.link()

        hudShaderProgram.createUniform("projectionMatrix")
        hudShaderProgram.createUniform("modelMatrix")
        hudShaderProgram.createUniform("colour")
        hudShaderProgram.createUniform("useTexture")
    }

    def render(camera: Camera) {
        transformation.updateProjectionMatrix(FOV, 800, 600, Z_NEAR, Z_FAR, camera)
        transformation.updateViewMatrix(camera)
        val projectionMatrix: Matrix4f = transformation.getProjectionMatrix
        val viewMatrix: Matrix4f = transformation.getViewMatrix
        _proj.clear()
        _modl.clear()
        projectionMatrix.get(_proj)
        projectionMatrix.mul(viewMatrix)
        viewMatrix.get(_modl)
        frustum = Frustum.getFrustum(_proj, _modl)


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT)
        if (tw.world != null) renderScene()
        renderGui()


    }

    def bindShaderProgram(shaderProgram: ShaderProgram): Unit = {
        Renderer.bindShaderProgram(shaderProgram)
    }

    def unbindShaderProgram(): Unit = {
        Renderer.unbindShaderProgram()
    }

    private def renderScene() {


        bindShaderProgram(sceneShaderProgram)

        val projectionMatrix: Matrix4f = transformation.getProjectionMatrix
        sceneShaderProgram.setUniform("projectionMatrix", projectionMatrix)

        glEnable(GL_CULL_FACE)

         var modelViewMatrix: Matrix4f = null
        RenderChunk.clearRend()


        worldRenderer.getRenderChunks(tw.player).foreach((renderChunk: RenderChunk) => {
              if(frustum.chunkInFrustum(renderChunk.chunkPosition)){
            sceneShaderProgram.setUniform("modelViewMatrix", transformation.buildChunkModelViewMatrix(renderChunk.chunk.position))
            renderChunk.render(0)
             }
        })

          worldRenderer.renderEntitiesItem(frustum, transformation)
        glDisable(GL_CULL_FACE)
      //  glDisable(GL_DEPTH_TEST)

                val objectMouseOver = tw.objectMouseOver
                if (objectMouseOver != null) {
                    modelViewMatrix = transformation.buildBlockModelViewMatrix(objectMouseOver.blockPos)
                    sceneShaderProgram.setUniform("modelViewMatrix", modelViewMatrix)
                    worldRenderer.renderBlockMouseOver()
                }
      //  glEnable(GL_DEPTH_TEST)
                val blockSelectPosition = tw.blockSelectPosition
                if (blockSelectPosition != null) {
                    modelViewMatrix = transformation.buildBlockModelViewMatrix(blockSelectPosition.pos)
                    sceneShaderProgram.setUniform("modelViewMatrix", modelViewMatrix)
                    worldRenderer.renderBlockSelect()
                }




        unbindShaderProgram()
    }
    private def renderGui() {
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)

        bindShaderProgram(hudShaderProgram)


        val ortho: Matrix4f = transformation.getOrtho2DProjectionMatrix(0, 800, 0, 600)
        hudShaderProgram.setUniform("projectionMatrix", ortho)
        tw.guiManager.draw(Mouse.getX, Mouse.getY)


        unbindShaderProgram()


        glEnable(GL_DEPTH_TEST)
        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
    }

    def cleanup() {
        if (sceneShaderProgram != null) sceneShaderProgram.cleanup()
        if (hudShaderProgram != null) hudShaderProgram.cleanup()

    }

}

object Renderer {
    var currentShaderProgram: ShaderProgram = _

    def bindShaderProgram(shaderProgram: ShaderProgram): Unit = {
        if (currentShaderProgram ne null) currentShaderProgram.unbind()
        currentShaderProgram = shaderProgram
        currentShaderProgram.bind()
    }

    def unbindShaderProgram(): Unit = {
        if (currentShaderProgram ne null) currentShaderProgram.unbind()
        currentShaderProgram = null
    }
}

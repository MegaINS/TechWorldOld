package ru.megains.techworld.client.render.shader

import org.joml.{Matrix3f, Matrix4f, Vector4f}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20._
import ru.megains.techworld.common.utils.Vec3f

import scala.collection.mutable

class ShaderProgram {
    val programId: Int = glCreateProgram
    val uniforms = new mutable.HashMap[String, UniformData]
    var vertexShaderId = 0
    var fragmentShaderId = 0

    if (programId == 0) throw new Exception("Could not create Shader")


    def createVertexShader(shaderCode: String): Unit = {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    def createFragmentShader(shaderCode: String): Unit = {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    protected def createShader(shaderCode: String, shaderType: Int): Int = {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) throw new Exception("Error creating shader. Code: " + shaderId)
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024))
        glAttachShader(programId, shaderId)
        shaderId
    }


    def link(): Unit = {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) throw new Exception("Error linking Shader code: " + glGetShaderInfoLog(programId, 1024))
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) System.err.println("Warning validating Shader code: " + glGetShaderInfoLog(programId, 1024))
    }

    def bind(): Unit = glUseProgram(programId)

    def unbind(): Unit = glUseProgram(0)

    def createUniform(uniformName: String): Unit = {
        val uniformLocation = glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) throw new Exception("Could not find uniform:" + uniformName)
        uniforms += uniformName -> new UniformData(uniformLocation)
    }

    def setUniform(uniformName: String, value: Matrix4f): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        // Check if float buffer has been created
        var fb = uniformData.floatBuffer
        if (fb == null) {
            fb = BufferUtils.createFloatBuffer(16)
            uniformData.floatBuffer = fb
        }
        // Dump the matrix into a float buffer
        value.get(fb)
        glUniformMatrix4fv(uniformData.uniformLocation, false, fb)
    }

    def setUniform(uniformName: String, value: Matrix3f): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        var fb = uniformData.floatBuffer
        if (fb == null) {
            fb = BufferUtils.createFloatBuffer(9)
            uniformData.floatBuffer = fb
        }
        value.get(fb)
        glUniformMatrix3fv(uniformData.uniformLocation, false, fb)
    }


    def setUniform(uniformName: String, value: Int): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) { // System.out.println("Uniform [" + uniformName + "] has nor been created");
            return
            //  throw new RuntimeException("Uniform [" + uniformName + "] has nor been created");
        }
        glUniform1i(uniformData.uniformLocation, value)
    }

    def setUniform(uniformName: String, value: Boolean): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) return
        glUniform1i(uniformData.uniformLocation, if (value) 1 else 0)
    }

    def setUniform(uniformName: String, value: Float): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        glUniform1f(uniformData.uniformLocation, value)
    }

    def setUniform(uniformName: String, value: Vec3f): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        glUniform3f(uniformData.uniformLocation, value.x, value.y, value.z)
    }

    def setUniform(uniformName: String, value: Vector4f): Unit = {
        val uniformData = uniforms.getOrElse(uniformName,default = null)
        if (uniformData == null) throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        glUniform4f(uniformData.uniformLocation, value.x, value.y, value.z, value.w)
    }




    def cleanup(): Unit = {
        unbind()
        if (programId != 0) {
            if (vertexShaderId != 0) glDetachShader(programId, vertexShaderId)
            if (fragmentShaderId != 0) glDetachShader(programId, fragmentShaderId)
            glDeleteProgram(programId)
        }
    }


}

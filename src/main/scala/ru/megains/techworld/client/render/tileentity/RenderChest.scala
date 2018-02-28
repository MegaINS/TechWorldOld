package ru.megains.techworld.client.render.tileentity
import org.joml.Matrix4f
import ru.megains.techworld.client.render.mesh.Mesh
import ru.megains.techworld.client.render.texture.TextureManager
import ru.megains.techworld.client.render.{OBJLoader, Renderer, Transformation}
import ru.megains.techworld.common.block.blockdata.BlockDirection
import ru.megains.techworld.common.tileentity.TileEntity
import ru.megains.techworld.common.world.World

object RenderChest extends TRenderTileEntity{


    var i:Long = 0
    var delZ = 0
    var up = true
    var chest1:Mesh = _
    var chest2:Mesh = _
    override def init(): Unit = {
        chest1 = OBJLoader.loadMesh("/obj/chest1.obj")
        chest2 = OBJLoader.loadMesh("/obj/chest2.obj")
    }

    override def render(tileEntity: TileEntity, world: World,textureManager: TextureManager): Boolean = {

       val blockState = world.getBlock(tileEntity.pos)

        if(i<System.currentTimeMillis()-50){
            i = System.currentTimeMillis()
            if(up){
                delZ -=1
            }else{
                delZ +=1
            }
            delZ match {
                case -90 => up = false
                case 0 =>  up = true
                case _=>
            }

        }


        val blockPos = blockState.blockDirection match {
            case BlockDirection.WEST | BlockDirection.EAST =>
                tileEntity.pos.sum(2,2,3)
            case _ =>
                tileEntity.pos.sum(3,2,2)
        }



        var modelViewMatrix: Matrix4f = null
        modelViewMatrix = Transformation.buildTileEntityViewMatrix(blockPos,0,  blockState.blockDirection.getAngel,0)
        Renderer.currentShaderProgram.setUniform("modelViewMatrix", modelViewMatrix)
        chest2.render(textureManager)

        val rx = -2f
        val ry = 0.6f
        val rz = 0.0f

        modelViewMatrix = Transformation.buildTileEntityViewMatrix(blockPos,0, blockState.blockDirection.getAngel ,0)

        modelViewMatrix.translate(rx, ry,rz)
                .rotateZ(Math.toRadians(-delZ).toFloat)
                .translate(-rx,- ry,-rz)

        Renderer.currentShaderProgram.setUniform("modelViewMatrix", modelViewMatrix)
        chest1.render(textureManager)
        true
    }
}

package ru.megains.techworld.client.render.gui

import java.awt.Color

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.common.block.blockdata.BlockDirection

class GuiDebugInfo extends GuiInGame with GuiText {

   // var lastGameType: GameType = NOT_SET
    var lastSide:BlockDirection = BlockDirection.UP

    override def initGui(orangeCraft: TechWorldClient): Unit = {


        addText("position", createString("Player position:", Color.WHITE))
        addText("position.x", createString("x:", Color.WHITE))
        addText("position.y", createString("y:", Color.WHITE))
        addText("position.z", createString("z:", Color.WHITE))
        addText("position.side", createString("side:", Color.WHITE))
        addText("fps", createString("FPS:", Color.WHITE))
        addText("memory", createString("Memory use:", Color.WHITE))
      //  addText("gameType", createString("Game type: " + lastGameType.name, Color.WHITE))
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        val height = 600
        val weight = 800
        drawObject(0, height - 20, 1, text("position"))
        drawObject(0, height - 40, 1, text("position.x"))
        drawObject(0, height - 60, 1, text("position.y"))
        drawObject(0, height - 80, 1, text("position.z"))
        drawObject(0, height - 100, 1, text("position.side"))
       // drawObject(0, height - 120, 1, text("gameType"))
        drawObject(weight - 250, height - 40, 1, text("fps"))
        drawObject(weight - 250, height - 20, 1, text("memory"))

    }

    override def tick(): Unit = {
        val player = oc.player
        addText("position.x", createString("x: " + player.posX, Color.WHITE))
        addText("position.y", createString("y: " + player.posY, Color.WHITE))
        addText("position.z", createString("z: " + player.posZ, Color.WHITE))
     //   val gameType = oc.playerController.currentGameType
//        if (lastGameType ne gameType) {
//            lastGameType = gameType
//            addText("gameType", createString("Game type: " + gameType.name, Color.WHITE))
//        }
        val side = oc.player.side
        if(side!= lastSide){
            lastSide = side
            addText("position.side", createString("side: "+side.name, Color.WHITE))
        }
        tickI += 1
        if (tickI > 19) {
            tickI = 0
            val usedBytes: Long = (Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory) / 1048576
            addText("memory", createString("Memory use: " + usedBytes + "/" + Runtime.getRuntime.totalMemory / 1048576 + "MB", Color.WHITE))
            addText("fps", createString("FPS: " + oc.lastFrames, Color.WHITE))
        }


    }

    var tickI = 0

}

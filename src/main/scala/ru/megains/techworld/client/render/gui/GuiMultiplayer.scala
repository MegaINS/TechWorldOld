package ru.megains.techworld.client.render.gui

import ru.megains.techworld.client.TechWorldClient
import ru.megains.techworld.client.render.gui.Element.GuiButton
import ru.megains.techworld.client.render.mesh.Mesh



class GuiMultiplayer(guiMainMenu: GuiScreen) extends GuiScreen {

    var worldsSlot: GuiSlotWorld = _

    //val server: ServerData = new ServerData("localhost", "localhost", true)
   /// val pinger = new ServerPinger
    var pin: Long = -1
    var pingMesh: Mesh = _

    override def initGui(orangeCraft: TechWorldClient): Unit = {


        worldsSlot = new GuiSlotWorld(0, "World", oc)


        buttonList += new GuiButton(0, oc, "Ping", 500, 100, 200, 50)
        buttonList += new GuiButton(1, oc, "Cancel", 500, 20, 200, 50)
        buttonList += new GuiButton(2, oc, "Connect", 100, 20, 200, 50)


    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
          //  case 0 => ping()
            case 1 => oc.guiManager.setGuiScreen(guiMainMenu)
          //  case 2 => connectToServer(server)
            case _ =>
        }
    }

    override def tick(): Unit = {
//        if (pin != server.pingToServer) {
//            pin = server.pingToServer
//            if (pingMesh ne null) pingMesh.cleanUp()
//            pingMesh = createString(pin toString, Color.BLACK)
//        }
    }

//    def connectToServer(server: ServerData) {
//        oc.guiManager.setGuiScreen(new GuiConnecting(this, oc, server))
//    }

//    def ping() = {
//        try {
//            pinger.ping(server)
//        } catch {
//            case e: Throwable =>
//                e.printStackTrace()
//
//        }
//    }


    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        super.drawScreen(mouseX, mouseY)
        worldsSlot.draw(mouseX, mouseY)
        drawObject(pingMesh, 400, 400)
    }
}

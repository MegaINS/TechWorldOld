package ru.megains.techworld

import ru.megains.techworld.server.{ServerCommand, TechWorldServer}

import scala.reflect.io.Path

object StartServer extends App {

    Thread.currentThread.setName("Server thread")

    val server = new TechWorldServer(Path("Z:/TechWorld/server").toDirectory)
    val serverCommand = new ServerCommand(server)
    serverCommand.setName("serverControl")
    serverCommand.setDaemon(true)
    serverCommand.start()
    server.start()



}

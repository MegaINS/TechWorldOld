package ru.megains.techworld

import ru.megains.techworld.client.TechWorldClient

import scala.reflect.io.Path



object StartClient extends App {
    try {


    Thread.currentThread.setName("Client")
    TechWorldClient.techWorld = new TechWorldClient(Path("Z:/TechWorld/").toDirectory)
    TechWorldClient.techWorld.run()
    } catch {
        case e:Exception => println(e)
    }
}

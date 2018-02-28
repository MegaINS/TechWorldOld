package ru.megains.techworld.common.world

import scala.collection.mutable.ArrayBuffer
import scala.reflect.io.{Directory, Path}


class AnvilSaveFormat(savesDirectory: Directory) {


    def getSavesArray: Array[String] = {
        if (savesDirectory.exists) {
            val array: ArrayBuffer[String] = new ArrayBuffer[String]()
            savesDirectory.list.foreach(
                (path) => {
                    if (path.isDirectory) {
                        array += path.name
                    }
                }
            )
            array.toArray
        } else {
            null
        }

    }

    def getSaveLoader(worldName: String): AnvilSaveHandler = {
        new AnvilSaveHandler(savesDirectory, worldName)
    }


    def deleteWorldDirectory(worldName: String): Unit = {
        val world: Directory = savesDirectory / Path(worldName) toDirectory
        val a = world.deleteRecursively()

    }


}

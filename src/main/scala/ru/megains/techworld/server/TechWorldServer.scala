package ru.megains.techworld.server

import ru.megains.techworld.common.utils.Logger

import scala.reflect.io.Directory


class TechWorldServer(serverDir: Directory) extends Logger[TechWorldServer]{

    var serverRunning = true
    var timeOfLastWarning = 0l

    def start():Unit = {

           run()
    }




    def run() = {

        var var1: Long = getCurrentTimeMillis
        var var50: Long = 0L

        while (serverRunning) {

            val var5: Long = getCurrentTimeMillis
            var var7: Long = var5 - var1

            if (var7 > 2000L && var1 - timeOfLastWarning >= 15000L) {
                log.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", var7, var7 / 50L)
                var7 = 2000L
                timeOfLastWarning = var1
            }

            if (var7 < 0L) {
                log.warn("Time ran backwards! Did the system time change?")
                   var7 = 0L
            }

            var50 += var7
            var1 = var5
            while (var50 > 50L) {
                var50 -= 50L
                update()

            }
            Thread.sleep(Math.max(1L, 50L - var50))

        }

        stop()
    }


    def stop(): Unit = {

    }


    def update(): Unit = {

    }



    def getCurrentTimeMillis: Long = System.currentTimeMillis
}

name := "TechWorld"

version := "1.0"

scalaVersion := "2.12.2"

val lwjglVersion = "3.1.5"

libraryDependencies ++= Seq(
    "org.joml" % "joml" % "1.9.0",
    "org.lwjgl" % "lwjgl" % lwjglVersion,
    "org.lwjgl" % "lwjgl-glfw" % lwjglVersion,
    "org.lwjgl" % "lwjgl-opengl" % lwjglVersion,
    "org.lwjgl" % "lwjgl-stb" % lwjglVersion,

    "org.lwjgl" % "lwjgl"  % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
    "org.lwjgl" % "lwjgl-glfw"% lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
    "org.lwjgl" % "lwjgl-opengl" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
    "org.lwjgl" % "lwjgl-stb"% lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",


    "io.netty" % "netty-all" % "5.0.0.Alpha2",
    "org.apache.logging.log4j" % "log4j-core" % "2.8.2",
    "org.apache.logging.log4j" % "log4j-api" % "2.8.2",


    "org.javassist" % "javassist" % "3.18.1-GA",



    "org.l33tlabs.twl" % "pngdecoder" % "1.0"

)

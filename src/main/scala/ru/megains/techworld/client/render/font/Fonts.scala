package ru.megains.techworld.client.render.font

import ru.megains.techworld.client.TechWorldClient

object Fonts {
    val fontRender: FontRender = TechWorldClient.techWorld.fontRender
    val timesNewRomanR: Font = fontRender.loadFont("TimesNewRomanR")
}

package ru.megains.techworld

object Test extends App{

    val let = 15
    val sum0 = 5000
    val pro:Float = 8


    val mes = 12*let
    val proI:Float = pro/12f/100f
    var sum:Float = 0
    var sumSPro:Float = 0
    var mesPro:Float  = 0
    var sumPro:Float  = 0
    for(i<- 1 to mes ){



        sum += sum0
        sumSPro += sum0
        mesPro =  sumSPro * proI
        sumPro += mesPro
        sumSPro += mesPro

        println(s"Месяц $i Сумма положенная = $sum Процент за месяц =  $mesPro Проценты за период = $sumPro Сумма с процентами = $sumSPro  ")
    }
}

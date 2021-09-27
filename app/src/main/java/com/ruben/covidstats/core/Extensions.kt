package com.ruben.covidstats.core

//Extension for linebreak the integer
fun Int.lineBreak(text: String):String{
    return "$this\n$text"
}
package com.boazj.gradle.log.utils

import com.boazj.gradle.utils.Color

class ColorCycler {
    def int index = 0
    def List colors = Color.values()

    def Color getNextColor(){
        Color c = colors[index % colors.size()]
        index++
        return c
    }
}

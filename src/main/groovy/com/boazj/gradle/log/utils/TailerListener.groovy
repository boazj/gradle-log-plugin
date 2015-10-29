package com.boazj.gradle.log.utils

import com.boazj.gradle.utils.Color
import com.boazj.gradle.utils.Output
import com.boazj.gradle.utils.OutputFactory
import com.boazj.gradle.utils.OutputListener
import org.apache.commons.io.input.TailerListenerAdapter
import org.gradle.api.Project

class TailerListener extends TailerListenerAdapter {
    private String header = ''
    private Output output
    private Project project
    private Color color

    TailerListener(Output output, String header, boolean showHeader = false, Color color = Color.White) {
        this.project = project
        this.color = color
        this.output = output
        if (showHeader) {
            this.header = "[${header}] "
        }
    }

    @Override
    void handle(String line) {
        synchronized (output) {
            output.say(header, color)
            output.sayln(line)
        }
    }
}
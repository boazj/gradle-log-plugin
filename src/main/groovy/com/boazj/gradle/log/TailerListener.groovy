package com.boazj.gradle.log

import com.boazj.gradle.utils.Color
import com.boazj.gradle.utils.Output
import com.boazj.gradle.utils.OutputFactory
import com.boazj.gradle.utils.OutputListener
import org.apache.commons.io.input.TailerListenerAdapter
import org.gradle.api.Project

class TailerListener extends TailerListenerAdapter {
    private String header = '';
    private Output output;
    private Project project;
    private Color color;

    TailerListener(Project project, File f, boolean showHeader = false, Color color = Color.White) {
        this(null, project, f, showHeader, color);
    }

    TailerListener(OutputListener listener, Project project, File f, boolean showHeader = false, Color color = Color.White) {
        this.project = project;
        this.color = color;
        output = OutputFactory.create(listener, project, "Tailer-${f.getName()}")
        if (showHeader) {
            header = "[${f.getName()}] ";
        }
    }

    @Override
    void handle(String line) {
        synchronized (project) {
            output.say(header, color)
            output.sayln(line)
        }
    }
}
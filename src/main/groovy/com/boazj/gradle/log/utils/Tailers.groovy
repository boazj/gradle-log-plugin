package com.boazj.gradle.log.utils

import com.boazj.gradle.utils.Output
import com.boazj.gradle.utils.OutputFactory
import com.boazj.gradle.utils.OutputListener
import org.apache.commons.io.input.Tailer
import org.gradle.api.Project
import org.gradle.api.file.FileCollection

class Tailers {

    private FileCollection logs;
    private boolean showColors;
    private boolean showOnlyNewLines;
    private Project project;

    private int delay = Tailer.DEFAULT_DELAY_MILLIS;
    private OutputListener listener;

    private List<Tailer> tailers;
    private List<Thread> threads;

    def Tailers(FileCollection logs, boolean showOnlyNewLines, boolean showColors, Project project) {
        this.project = project;
        this.showOnlyNewLines = showOnlyNewLines;
        this.showColors = showColors;
        this.logs = logs;
    }

    def void start() {
        ColorCycler colors = new ColorCycler();
        boolean showHeader = logs.size() > 1;

        Output output = OutputFactory.create(listener, project, "tailers")
        tailers = logs.collect { File f ->
            new Tailer(f, new TailerListener(output, f.getName(), showHeader, colors.getNextColor()), delay, showOnlyNewLines);
        };

        threads = tailers.collect { Tailer t -> new Thread(t); }
        threads.each { it.start() }
    }

    def void stop() {
        tailers.each { it.stop(); }
        threads.each { it.interrupt(); }
    }

    int getDelay() {
        return delay
    }

    void setDelay(int delay) {
        this.delay = delay
    }

    OutputListener getListener() {
        return listener
    }

    void setListener(OutputListener listener) {
        this.listener = listener
    }
}

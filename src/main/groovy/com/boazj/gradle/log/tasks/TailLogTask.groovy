package com.boazj.gradle.log.tasks

import com.boazj.gradle.log.utils.Tailers
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.StopActionException
import org.gradle.api.tasks.TaskAction

class TailLogTask extends DefaultTask {

    private boolean showOnlyNewLines = true;

    private boolean showColors = true;

    private FileCollection log = null;

    @TaskAction
    void tail() {
        if (log == null || log.isEmpty()) {
            throw new StopActionException('No logs to tail')
        }

        Tailers tailers = new Tailers(log, showOnlyNewLines, showColors, getProject());
        tailers.start();

        Reader r = System.console().reader();
        while (!['exit', 'q', 'quit'].contains(r.readLine())) {
        }

        tailers.stop();
    }

    FileCollection getLog() { return log; }

    void setLog(FileCollection log) { this.log = log; }

    boolean getShowOnlyNewLines() { return showOnlyNewLines; }

    void setShowOnlyNewLines(boolean showOnlyNewLines) { this.showOnlyNewLines = showOnlyNewLines; }

    boolean getShowColors() { return showColors; }

    void setShowColors(boolean showColors) { this.showColors = showColors; }
}

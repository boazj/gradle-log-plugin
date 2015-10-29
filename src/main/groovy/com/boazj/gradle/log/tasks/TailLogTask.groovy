package com.boazj.gradle.log.tasks

import com.boazj.gradle.log.utils.Tailers
import com.boazj.gradle.utils.OutputListener
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException

class TailLogTask extends DefaultTask {

    private boolean showOnlyNewLines = true

    private boolean showColors = true

    private FileCollection log = null

    private OutputListener listener

    @TaskAction
    void tail() {
        if (log == null || log.isEmpty()) {
            throw new TaskExecutionException('No logs to tail')
        }

        Tailers tailers = new Tailers(log, showOnlyNewLines, showColors, getProject())
        if (listener != null) {
            tailers.setListener(listener)
        }
        tailers.start()

        Scanner s = new Scanner(System.in)
        while (!['exit', 'q', 'quit'].contains(s.nextLine())) {
        }

        tailers.stop()
    }

    FileCollection getLog() { return log }

    void setLog(FileCollection log) { this.log = log }

    boolean getShowOnlyNewLines() { return showOnlyNewLines }

    void setShowOnlyNewLines(boolean showOnlyNewLines) { this.showOnlyNewLines = showOnlyNewLines }

    boolean getShowColors() { return showColors }

    void setShowColors(boolean showColors) { this.showColors = showColors }

    void setListener(OutputListener listener) { this.listener = listener }
}

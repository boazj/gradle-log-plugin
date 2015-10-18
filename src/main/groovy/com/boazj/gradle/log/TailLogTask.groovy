package com.boazj.gradle.log

import com.boazj.gradle.utils.Color
import com.boazj.gradle.utils.OutputListener
import org.apache.commons.io.input.Tailer
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.StopActionException
import org.gradle.api.tasks.TaskAction

class TailLogTask extends DefaultTask {

    private File log = null;

    private Boolean showHeaders = null;

    private boolean showOnlyNewLines = true;

    private boolean showColors = true;

    private FileCollection logs = null;

    private OutputListener listener = null;

    @TaskAction
    void tail() {
        FileCollection logs = getAllLogs();
        if (logs.isEmpty()) {
            throw new StopActionException('No logs to tail')
        }
        setDefaultShowHeadersValue(logs);
        List<Tailer> tailers = [];
        logs.eachWithIndex { File f, int i ->
            tailers.add(
                    Tailer.create(
                            f,
                            new TailerListener(listener, getProject(), f, showHeaders, getColor(i)),
                            Tailer.DEFAULT_DELAY_MILLIS,
                            showOnlyNewLines
                    )
            );
        }

        Reader r = System.console().reader()
        while (['exit', 'q', 'quit'].contains(r.readLine())) {
        }
        println('')
        tailers.each { Tailer t -> t.stop() };
    }

    private Color getColor(int index){
        if (!showColors) {
            return Color.White
        }
        return Color.values()[index % Color.values().size()]
    }

    private FileCollection getAllLogs(){
        FileCollection files = getProject().files();
        if (logs != null) {
            files += logs;
        }
        if (log != null) {
            files += getProject().files(log);
        }
        return files;
    }

    private void setDefaultShowHeadersValue(FileCollection logs) {
        if (logs.size() > 1 && showHeaders == null) {
            showHeaders = true;
        } else if (logs.size() == 1 && showHeaders == null) {
            showHeaders = false;
        }
    }

    File getLog() { return log; }

    void setLog(File log) { this.log = log; }

    Boolean getShowHeaders() { return showHeaders; }

    void setShowHeaders(Boolean showHeaders) { this.showHeaders = showHeaders; }

    FileCollection getLogs() { return logs; }

    void setLogs(FileCollection logs) { this.logs = logs; }

    boolean getShowOnlyNewLines() { return showOnlyNewLines; }

    void setShowOnlyNewLines(boolean showOnlyNewLines) { this.showOnlyNewLines = showOnlyNewLines; }

    boolean getShowColors() { return showColors; }

    void setShowColors(boolean showColors) { this.showColors = showColors; }

    OutputListener getListener() { return listener; }

    void setListener(OutputListener listener) { this.listener = listener; }
}

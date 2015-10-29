package com.boazj.gradle.log.utils

import com.boazj.gradle.utils.AccumulatingOutputListener
import com.boazj.gradle.utils.OutputListener
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertTrue

class TailersTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()

    private static final int DELAY = 50

    @Test
    public void testTailersSingleLogNewLinesOnly() {
        def Project project = ProjectBuilder.builder().build()
        def OutputListener outputListener = new AccumulatingOutputListener()
        File log = testProjectDir.newFile('log.log')
        FileCollection logs = project.files(log)
        Tailers tailers = new Tailers(logs, true, true, project)
        tailers.listener = outputListener
        tailers.delay = DELAY;
        tailers.start()
        write(log, 'test string')
        Thread.sleep(DELAY * 10)
        tailers.stop()
        assertTrue(outputListener.contains('test string'))
    }

    private void write(File file, String... lines) throws Exception {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

}


package com.boazj.gradle.log.tasks

import com.boazj.gradle.utils.AccumulatingOutputListener
import com.boazj.gradle.utils.OutputListener
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskExecutionException
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class TailLogTaskTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()

    @Test(expected = TaskExecutionException.class)
    public void testNullLogs(){
        Project project = ProjectBuilder.builder().build()
        TailLogTask task = project.task('tail', type: TailLogTask)
        task.execute()
    }

    @Test(expected = TaskExecutionException.class)
    public void testEmptyLogs(){
        Project project = ProjectBuilder.builder().build()
        TailLogTask task = project.task('tail', type: TailLogTask)
        task.log = project.files()
        task.execute()
    }

    @Test
    public void testSingleLogFileTailLogTask(){
        Project project = ProjectBuilder.builder().build()
        TailLogTask task = project.task('tail', type: TailLogTask)
        OutputListener listener = new AccumulatingOutputListener()
        File logFile = testProjectDir.newFile('logFile.logFile')
        FileCollection log = project.files(logFile);

        task.listener = listener
        task.log = log
        task.showOnlyNewLines = true
        task.showColors = false

        InputStream systemIn = System.in
        ByteArrayInputStream input = new ByteArrayInputStream('exit'.getBytes())
        System.setIn(input)

        task.execute()

        System.setIn(systemIn)

        //If we haven't reached here then we're stuck!
        //We don't really care about the tailed output as it's tested in a different test
        Assert.assertTrue(true)
    }
}

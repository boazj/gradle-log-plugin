package com.boazj.gradle.log.tasks

import com.boazj.gradle.log.tasks.TailLogTask
import com.boazj.gradle.utils.AccumulatingOutputListener
import com.boazj.gradle.utils.OutputListener
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class TailLogTaskTest extends Specification {


//    def 'test single log file'() {
//        given:
//            Project project = ProjectBuilder.builder().build()
//            def TailLogTask task = project.task('tail', type: TailLogTask);
//            def OutputListener listener = new AccumulatingOutputListener();
//            def File log = file('tmp.test.log');
//            task.listener = listener;
//            task.standardInput = new
//            Thread t = Thread.start {
//                task.execute();
//            }
//        when:
//
//        then:
//            listener.contains('Checking Gradle version ... ')
//        then:
//            noExceptionThrown()
//    }

}

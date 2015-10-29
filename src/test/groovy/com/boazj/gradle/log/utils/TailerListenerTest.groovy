package com.boazj.gradle.log.utils

import com.boazj.gradle.utils.AccumulatingOutputListener
import com.boazj.gradle.utils.Output
import com.boazj.gradle.utils.OutputFactory
import com.boazj.gradle.utils.OutputListener
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class TailerListenerTest extends Specification {
    def "handle header-less log"() {
        given:
            def Project project = ProjectBuilder.builder().build()
            def OutputListener outputListener = new AccumulatingOutputListener()
            Output output = OutputFactory.create(outputListener, project, "tailers")
            def TailerListener listener = new TailerListener(output, 'test.log', false)
        when:
            listener.handle('test string')
        then:
            outputListener.contains('test string')
        then:
            noExceptionThrown()
    }

    def "handle header log"() {
        given:
            def Project project = ProjectBuilder.builder().build()
            def OutputListener outputListener = new AccumulatingOutputListener()
            Output output = OutputFactory.create(outputListener, project, "tailers")
            def TailerListener listener = new TailerListener(output, 'test.log', true)
        when:
            listener.handle('test string')
        then:
            outputListener.contains("[test.log] test string")
        then:
            noExceptionThrown()
    }
}

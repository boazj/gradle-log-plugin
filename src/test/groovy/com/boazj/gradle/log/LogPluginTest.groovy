package com.boazj.gradle.log

import nebula.test.PluginProjectSpec
import org.gradle.api.GradleException
import org.gradle.api.tasks.wrapper.Wrapper

class LogPluginTest extends PluginProjectSpec {

    @Override
    String getPluginName() { return 'com.boazj.log' }

    def 'test log task when applying plugin'() {
        when:
            this.project.apply plugin: 'com.boazj.log'

        then:
            this.project.tasks.getByName(LogPlugin.TAIL_TASK_NAME)
    }

    def 'test hijacked task'() {
        given:
            this.project.tasks.create(LogPlugin.TAIL_TASK_NAME, Wrapper.class)

        when:
            this.project.apply plugin: 'com.boazj.log'

        then:
            def e = thrown(GradleException.class)
            e.cause.message == "Cannot instantiate plugin Log due to hijacked task name (${LogPlugin.TAIL_TASK_NAME})"
    }

    def 'test apply plugin on a project with that plugin already applied'() {
        given:
            this.project.apply plugin: 'com.boazj.log'
        when:
            this.project.apply plugin: 'com.boazj.log'

        then:
            noExceptionThrown()
    }
}
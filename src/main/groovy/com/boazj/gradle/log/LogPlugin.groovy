package com.boazj.gradle.log

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginInstantiationException

class LogPlugin implements Plugin<Project> {
    public static final String LOG_EXTENSION_NAME = 'log'
    public static final String TAIL_TASK_NAME = 'tail'

    @Override
    void apply(Project project) {
        def potentialHijackedTask = project.tasks.findByName(TAIL_TASK_NAME)

        if (project.extensions.findByName(LOG_EXTENSION_NAME) != null) {
            throw new PluginInstantiationException("Cannot instantiate plugin Log due to hijacked extension name (${LOG_EXTENSION_NAME})")
        }

        if (potentialHijackedTask != null) {
            throw new PluginInstantiationException("Cannot instantiate plugin Log due to hijacked task name (${TAIL_TASK_NAME})")
        }

        project.tasks.create(TAIL_TASK_NAME, TailLogTask.class)
    }

}



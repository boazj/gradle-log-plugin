package com.boazj.gradle.log

import com.boazj.gradle.log.tasks.TailLogTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginInstantiationException

class LogPlugin implements Plugin<Project> {
    public static final String TAIL_TASK_NAME = 'tail'

    @Override
    void apply(Project project) {
        def potentialHijackedTask = project.tasks.findByName(TAIL_TASK_NAME)

        if (potentialHijackedTask != null) {
            throw new PluginInstantiationException("Cannot instantiate plugin Log due to hijacked task name (${TAIL_TASK_NAME})")
        }

        project.tasks.create(TAIL_TASK_NAME, TailLogTask.class)
    }

}
package com.longway.plugin;

import org.gradle.BuildListener;
import org.gradle.BuildResult;
import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionListener;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.TaskState;
import org.gradle.util.Clock;


/**
 * Created by longway on 16/10/22. Email:longway1991117@sina.com
 */

class TimeListener implements TaskExecutionListener, BuildListener {
    private Clock clock
    private times = []
    private total = [:] // 计算module的执行时间

    @Override
    void beforeExecute(Task task) {
        clock = new org.gradle.util.Clock()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        def ms = clock.timeInMs
        def name = task.name;
        def path = task.path;
        def moduleName = task.project.name;
        if (total.containsKey(moduleName)) {
            total.put(moduleName, total.get(moduleName) + ms)
        } else {
            total.put(moduleName, ms)
        }
        times.add([ms, name])
        task.project.logger.error "${path} spend ${ms}ms"
    }

    @Override
    void buildFinished(BuildResult result) {
        println "module spend time:"
        Set<String> keys = total.keySet();
        for (String key : keys) {
            def moduleTime = total.get(key);
            if (moduleTime > 1000) {
                printf("\t%sms  %s (%s)\n", moduleTime, key, "module consume time large than 1s suggest optimize")
            } else {
                printf("\t%sms  %s (%s)\n", moduleTime, key, "good")
            }
        }
        println "Task spend time:"
        boolean noIssue = true;
        for (time in times) {
            if (time[0] >= 50) {
                noIssue = false;
                printf "\t%sms  %s\n", time
            }
        }
        if (noIssue) {
            println("\tnot found issue,task consume time large than 50ms will show")
        }
    }

    @Override
    void buildStarted(Gradle gradle) {}

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}
}

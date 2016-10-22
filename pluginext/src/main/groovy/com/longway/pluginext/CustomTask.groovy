package com.longway.pluginext;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * Created by longway on 16/10/22. Email:longway1991117@sina.com
 */

public class CustomTask extends DefaultTask {
    @TaskAction
    public void out() {
        println("params1 is ${project.pluginExt.params1}")
        println("params2 is ${project.pluginExt.params2}")
    }
}

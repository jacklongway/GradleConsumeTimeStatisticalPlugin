package com.longway.pluginext;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by longway on 16/10/22. Email:longway1991117@sina.com
 */

public class PluginImpl implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.extensions.create("pluginExt",PluginExtensions)
        project.task("TestTask",type:CustomTask)
    }
}

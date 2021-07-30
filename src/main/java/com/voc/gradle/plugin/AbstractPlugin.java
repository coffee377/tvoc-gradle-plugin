package com.voc.gradle.plugin;

import com.voc.gradle.plugin.api.IPlugin;
import org.gradle.api.Project;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/08/13 16:37
 */
public abstract class AbstractPlugin implements IPlugin {

    private Project project;

    @Override
    public Project getProject() {
        return this.project;
    }

    @Override
    public void setProject(Project project) {
        this.project = project;
    }

}

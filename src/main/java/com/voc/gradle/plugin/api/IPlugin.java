package com.voc.gradle.plugin.api;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/08/13 16:19
 */
public interface IPlugin extends Plugin<Project>, IProject {

    /**
     * 官方插件入口
     *
     * @param project Project
     */
    @Override
    default void apply(@NotNull Project project) {
        this.setProject(project);
        this.onApply(project);
    }

    /**
     * 应用插件
     *
     * @param project Project
     */
    void onApply(Project project);

}

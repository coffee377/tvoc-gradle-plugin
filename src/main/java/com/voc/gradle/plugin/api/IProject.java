package com.voc.gradle.plugin.api;

import org.gradle.api.Project;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/15 10:15
 */
public interface IProject {

    /**
     * 获取当前项目
     *
     * @return Project
     */
    Project getProject();

    /**
     * 设置当前项目
     *
     * @param project Project
     */
    void setProject(Project project);

}

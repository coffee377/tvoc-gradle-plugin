package com.voc.gradle.plugin.tasks;

import org.gradle.api.DefaultTask;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/07/19 17:12
 */
public class BaseTask extends DefaultTask {
    public static final String DEVTOOLS_GROUP_NAME = "devtools";
    public final static String BUILD_HELP_GROUP_NAME = "build help";

    public BaseTask() {
        this.setGroup("GROUP_DEVTOOLS");
    }

}

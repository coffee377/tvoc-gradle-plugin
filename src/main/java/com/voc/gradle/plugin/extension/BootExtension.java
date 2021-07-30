package com.voc.gradle.plugin.extension;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/17 09:17
 */
public class BootExtension {

    private final Project project;

    private final Property<Boolean> library;

    public BootExtension(Project project) {
        this.project = project;
        this.library = this.project.getObjects().property(Boolean.class);
    }

    public Property<Boolean> getLibrary() {
        return library;
    }


}

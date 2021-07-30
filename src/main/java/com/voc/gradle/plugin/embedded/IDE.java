package com.voc.gradle.plugin.embedded;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/15 10:55
 */
@Getter
public enum IDE {
    /**
     * 开发工具
     */
    IDEA("idea"),
    ECLIPSE("eclipse"),
    VISUAL_STUDIO("visual_studio"),
    XCODE("xcode");

    private final String name;

    public static IDE of(String ideName) {
        return Arrays.stream(IDE.values()).filter(ide -> ide.getName().equalsIgnoreCase(ideName)).findFirst().orElse(IDE.IDEA);
    }

    IDE(String name) {
        this.name = name;
    }
}

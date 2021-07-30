package com.voc.gradle.plugin.util;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/07/26 09:23
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

}

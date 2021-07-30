package com.voc.gradle.plugin.util;

import com.voc.gradle.plugin.embedded.ExtraProps;
import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/07/25 09:51
 */
@Slf4j
public class ExtraPropsUtils {

    /**
     * 获取属性值
     *
     * @param extension    扩展属性
     * @param keyName      属性名称
     * @param defaultValue 属性值
     * @return Object
     */
    public static Object getValue(ExtraPropertiesExtension extension, String keyName, Object defaultValue) {
        if (!extension.has(keyName)) {
            extension.set(keyName, defaultValue);
            return defaultValue;
        }
        return extension.get(keyName);
    }

    /**
     * 获取属性值
     *
     * @param project      当前项目
     * @param keyName      属性名称
     * @param defaultValue 属性值
     * @return T
     */
    public static Object getValue(Project project, String keyName, Object defaultValue) {
        ExtraPropertiesExtension extraProperties = project.getExtensions().getExtraProperties();
        return getValue(extraProperties, keyName, defaultValue);
    }

    public static String getStringValue(Project project, String keyName, String defaultValue) {
        return (String) getValue(project, keyName, defaultValue);
    }

    public static boolean getBooleanValue(Project project, String keyName, boolean defaultValue) {
        return (boolean) getValue(project, keyName, defaultValue);
    }

    /**
     * 获取属性值
     *
     * @param extension 扩展属性
     * @param props     属性枚举
     * @return Object
     */
    public static Object getValue(ExtraPropertiesExtension extension, ExtraProps props) {
        return getValue(extension, props.getKey(), props.getValue());
    }

    public static String getStringValue(Project project, ExtraProps props) {
        return getStringValue(project, props.getKey(), props.getValue().toString());
    }

    public static boolean getBooleanValue(Project project, ExtraProps props) {
        return getBooleanValue(project, props.getKey(), Boolean.parseBoolean(props.getValue().toString()));
    }

}

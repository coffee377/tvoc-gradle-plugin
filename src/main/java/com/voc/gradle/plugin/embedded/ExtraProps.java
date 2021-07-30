package com.voc.gradle.plugin.embedded;

import lombok.Getter;
import org.gradle.api.JavaVersion;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/08 16:06
 */
@Getter
public enum ExtraProps {
    /**
     * 扩展配置属性
     */
    JUNIT_VERSION("junit.version", "5.5.2"),
    SLF4J_VERSION("org.slf4j.version", "1.7.25"),
    JAVA_CRACK_VERSION("com.voc.crack.version", "2.0.2-RELEASE"),
    LOMBOK_VERSION("lombok.version", "1.18.4"),
    LOGBACK_VERSION("logback.version", "1.2.3"),
    FAST_JSON_VERSION("fastjson.version", "1.2.58"),
    GOOGLE_AUTO_SERVICE_VERSION("com.google.auto.service.version", "1.0-rc4"),
    KOTLIN_VERSION("kotlin.version", "1.3.40"),
    JAVAX_SERVLET_VERSION("javax.servlet.version", "3.1.0"),

//    ENABLE_KOTLIN("kotlin.enable", false),
    KOTLIN_INCREMENTAL("kotlin.incremental", true),
    KOTLIN_JVM_VERSION("kotlin.jvm.version", JavaVersion.current().toString()),


    ;

    private final String key;

    private final Object value;

    private DepEnum dependency;

    ExtraProps(String key, Object value) {
        this.key = key;
        this.value = value;
    }

}

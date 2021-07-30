package com.voc.gradle.plugin.api;

import com.voc.gradle.plugin.embedded.DepEnum;
import com.voc.gradle.plugin.util.DepUtils;
import com.voc.gradle.plugin.util.ExtraPropsUtils;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.JavaPlugin;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/15 10:21
 */
public interface IDependency extends IProject {

    /**
     * 添加依赖
     *
     * @param configurationName  配置面名称
     * @param dependencyNotation 依赖
     */
    default void addDependency(String configurationName, Object dependencyNotation) {
        DependencyHandler dependencies = getProject().getDependencies();
        Dependency dependency = dependencies.create(dependencyNotation);
        dependencies.add(configurationName, dependency);
    }

    /**
     * 添加依赖
     *
     * @param configurationName 配置面名称
     * @param dependency        依赖
     */
    default void addDependency(String configurationName, DepEnum dependency) {
        String version = ExtraPropsUtils.getStringValue(getProject(), dependency.getExtraProps());
        this.addDependency(configurationName, DepUtils.of(dependency, version));
    }

    /**
     * 添加 annotationProcessor 依赖
     *
     * @param dependency 依赖
     * @see JavaPlugin#ANNOTATION_PROCESSOR_CONFIGURATION_NAME
     */
    default void addAnnotationProcessor(DepEnum dependency) {
        this.addDependency(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, dependency);
    }


    /**
     * 添加 implementation 依赖
     *
     * @param dependency Dependency
     * @see JavaPlugin#IMPLEMENTATION_CONFIGURATION_NAME
     */
    default void addImplementation(DepEnum dependency) {
        this.addDependency(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, dependency);
    }

    /**
     * 添加 testImplementation 依赖
     *
     * @param dependency Dependency
     * @see JavaPlugin#TEST_IMPLEMENTATION_CONFIGURATION_NAME
     */
    default void addTestImplementation(DepEnum dependency) {
        this.addDependency(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME, dependency);
    }

    /**
     * 添加 compileOnly 依赖
     *
     * @param dependency Dependency
     * @see JavaPlugin#COMPILE_ONLY_CONFIGURATION_NAME
     */
    default void addCompileOnly(DepEnum dependency) {
        this.addDependency(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME, dependency);
    }

    /**
     * 添加 api 依赖
     *
     * @param dependency Dependency
     * @see JavaPlugin#API_CONFIGURATION_NAME
     */
    default void addApi(DepEnum dependency) {
        this.addDependency(JavaPlugin.API_CONFIGURATION_NAME, dependency);
    }

}

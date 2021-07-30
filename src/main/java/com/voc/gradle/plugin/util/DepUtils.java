package com.voc.gradle.plugin.util;

import com.voc.gradle.plugin.embedded.DepEnum;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency;
import org.gradle.api.internal.artifacts.dsl.dependencies.ModuleFactoryHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/07/24 18:56
 */
public class DepUtils {

    /**
     * 创建依赖
     *
     * @param configuration 配置名称
     * @param group         组织
     * @param name          名称
     * @param version       版本
     * @param transitive    是否传递依赖
     * @param classifier    分类
     * @param excludeRule   排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(@Nullable String configuration, String group, String name, String version
            , boolean transitive, String classifier, ExcludeRule... excludeRule) {
        DefaultExternalModuleDependency moduleDependency = new DefaultExternalModuleDependency(group, name, version, configuration);
        /* 是否传递依赖 */
        moduleDependency.setTransitive(transitive);
        /* 分类 */
        ModuleFactoryHelper.addExplicitArtifactsIfDefined(moduleDependency, null, classifier);
        /* 排除项 */
        exclude(moduleDependency, transitive, excludeRule);
        return moduleDependency;
    }

    /**
     * 创建依赖
     *
     * @param group       组织
     * @param name        名称
     * @param version     版本
     * @param transitive  是否传递依赖
     * @param classifier  分类
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(String group, String name, String version, boolean transitive, String classifier, ExcludeRule... excludeRule) {
        return of(null, group, name, version, transitive, classifier, excludeRule);
    }

    /**
     * 创建依赖
     *
     * @param group       组织
     * @param name        名称
     * @param version     版本
     * @param transitive  是否传递依赖
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(String group, String name, String version, boolean transitive, ExcludeRule... excludeRule) {
        return of(group, name, version, transitive, null, excludeRule);
    }

    /**
     * 创建依赖
     *
     * @param group       组织
     * @param name        名称
     * @param version     版本
     * @param classifier  分类
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(String group, String name, String version, String classifier, ExcludeRule... excludeRule) {
        return of(group, name, version, true, classifier, excludeRule);
    }

    /**
     * 创建依赖
     *
     * @param group       组织
     * @param name        名称
     * @param version     版本
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(String group, String name, String version, ExcludeRule... excludeRule) {
        return of(group, name, version, true, null, excludeRule);
    }

    /**
     * 创建依赖
     *
     * @param configuration 配置名称
     * @param dependency    内部依赖枚举
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(String configuration, DepEnum dependency) {
        return of(configuration, dependency.getGroup(), dependency.getName(), dependency.getVersion(), dependency.isTransitive(), dependency.getClassifier(), dependency.getExcludeRule());
    }

    /**
     * 创建依赖
     *
     * @param dependency 内部依赖枚举
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(DepEnum dependency) {
        return of(null, dependency);
    }

    /**
     * 创建依赖
     *
     * @param dependency  内部依赖枚举
     * @param version     版本
     * @param transitive  是否传递依赖
     * @param classifier  分类
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(DepEnum dependency, String version, boolean transitive, String classifier, ExcludeRule... excludeRule) {
        dependency
                .setVersion(version)
                .setTransitive(transitive)
                .setClassifier(classifier)
                .setExcludeRule(excludeRule);
        return of(dependency);
    }


    /**
     * 创建依赖
     *
     * @param dependency 内部依赖枚举
     * @param version    版本
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(DepEnum dependency, String version) {
        return of(dependency, version, true, null);
    }


    /**
     * 创建依赖
     *
     * @param dependency  内部依赖枚举
     * @param transitive  是否传递依赖
     * @param classifier  分类
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(DepEnum dependency, boolean transitive, String classifier, ExcludeRule... excludeRule) {
        return of(dependency, null, transitive, classifier, excludeRule);
    }


    /**
     * 创建依赖
     *
     * @param dependency  内部依赖枚举
     * @param transitive  是否传递依赖
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(DepEnum dependency, boolean transitive, ExcludeRule... excludeRule) {
        return of(dependency, null, transitive, null, excludeRule);
    }


    /**
     * 创建依赖
     *
     * @param dependency  内部依赖枚举
     * @param classifier  分类
     * @param excludeRule 排除规则
     * @return DefaultExternalModuleDependency
     */
    public static DefaultExternalModuleDependency of(DepEnum dependency, String classifier, ExcludeRule... excludeRule) {
        return of(dependency, null, true, classifier, excludeRule);
    }

    /**
     * 排除依赖性
     *
     * @param dependency  依赖
     * @param transitive  传递依赖
     * @param excludeRule 排除规则
     */
    private static void exclude(DefaultExternalModuleDependency dependency, boolean transitive, ExcludeRule... excludeRule) {
        if (transitive && excludeRule != null) {
            Map<String, String> exclude = new HashMap<>(2);
            for (ExcludeRule rule : excludeRule) {
                if (StringUtils.isNotEmpty(rule.getGroup())) {
                    exclude.put("group", rule.getGroup());
                }
                if (StringUtils.isNotEmpty(rule.getModule())) {
                    exclude.put("module", rule.getModule());
                }
                dependency.exclude(exclude);
            }
        }
    }

}

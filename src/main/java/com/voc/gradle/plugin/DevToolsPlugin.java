package com.voc.gradle.plugin;

import com.voc.gradle.plugin.api.IDependency;
import com.voc.gradle.plugin.api.IRepository;
import com.voc.gradle.plugin.embedded.DepEnum;
import com.voc.gradle.plugin.embedded.ExtraProps;
import com.voc.gradle.plugin.embedded.IDE;
import com.voc.gradle.plugin.extension.DevToolsExtension;
import com.voc.gradle.plugin.util.ExtraPropsUtils;
import lombok.Getter;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.tasks.Delete;
import org.gradle.ide.visualstudio.plugins.VisualStudioPlugin;
import org.gradle.ide.xcode.plugins.XcodePlugin;
import org.gradle.jvm.tasks.Jar;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/08/23 14:27
 */
@Getter
public class DevToolsPlugin extends AbstractPlugin implements IRepository, IDependency {

    public static final String DEV_TOOL_EXTENSION_NAME = "devtools";
    private static final Pattern JUNIT_4_PATTERN = Pattern.compile("^4.*");
    private static final Pattern JUNIT_5_PATTERN = Pattern.compile("^5.*");

    public final Map<String, String> INNER_ALI_MAVEN = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put("Public", "https://maven.aliyun.com/repository/public/");
                put("Google", "https://maven.aliyun.com/repository/google");
                put("GradlePlugin", "https://maven.aliyun.com/repository/gradle-plugin");
                put("Spring", "https://maven.aliyun.com/repository/spring");
                put("SpringPlugin", "https://maven.aliyun.com/repository/spring-plugin");
            }}
    );

    @Override
    public void onApply(Project project) {
        this.configureExtension(project);
        this.configureConfigurations(project);
        this.applyPlugins(project);
        this.configureJar(project);
        this.configureConfigurations(project);
        this.configureClean(project);
        this.configureRepositories(project);
        this.configureDependencies(project);
    }

    /**
     * 配置扩展选项
     *
     * @param project Project
     */
    private void configureExtension(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        extensions.create(DEV_TOOL_EXTENSION_NAME, DevToolsExtension.class, project);
        project.afterEvaluate(p ->
                extensions.configure(DevToolsExtension.class, devToolExtension -> {
                    if (devToolExtension.isAliMaven()) {
                        INNER_ALI_MAVEN.forEach(
                                (name, url) -> devToolExtension.getMaven().create(name, mavenRepositories ->
                                        mavenRepositories.setUrl(url)
                                )
                        );
                    }
                })
        );

    }

    /**
     * 配置使用插件
     *
     * @param project Project
     */
    private void applyPlugins(Project project) {
        PluginContainer plugins = project.getPlugins();

        /* 1. 应用开发工具插件，默认使用 idea */
        String ide = ExtraPropsUtils.getStringValue(project, "ide", "idea");
        this.applyIdePlugin(plugins, IDE.of(ide));

        /* 2.应用 JavaLibrary 插件 */
        plugins.apply(JavaLibraryPlugin.class);

        /* 3.应用 MavenPublish 插件 */
        plugins.apply(MavenPublishPlugin.class);

        /* 4.依赖管理插件 */
        plugins.apply("io.spring.dependency-management");
    }

    /**
     * 应用开发工具插件
     *
     * @param plugins PluginContainer
     * @param ide     String
     */
    private void applyIdePlugin(PluginContainer plugins, IDE ide) {
        switch (ide) {
            case ECLIPSE:
                plugins.apply(EclipsePlugin.class);
                break;
            case VISUAL_STUDIO:
                plugins.apply(VisualStudioPlugin.class);
                break;
            case XCODE:
                plugins.apply(XcodePlugin.class);
                break;
            case IDEA:
            default:
                plugins.apply(IdeaPlugin.class);
        }
    }

    /**
     * 配置打包策略
     *
     * @param project Project
     */
    private void configureJar(Project project) {
        project.getTasks().withType(Jar.class, jar -> {
            /* 重复文件策略，排除 */
            jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
            jar.setIncludeEmptyDirs(false);
            /* 排除 JRebel 配置文件 */
            jar.exclude("rebel.xml");
        });
    }

    /**
     * 注解处理器配置添加到编译环境 & 依赖缓存时间配置
     *
     * @param project Project
     */
    public void configureConfigurations(Project project) {
        ConfigurationContainer configurations = project.getConfigurations();
        Configuration compileOnly = configurations.getByName(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);
        Configuration testCompileOnly = configurations.getByName(JavaPlugin.TEST_COMPILE_ONLY_CONFIGURATION_NAME);
        Configuration annotationProcessor = configurations.getByName(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME);

        /* 编译依赖继承至注解依赖 */
        compileOnly.extendsFrom(annotationProcessor);

        /* 测试编译依赖继承至编译依赖 */
        testCompileOnly.extendsFrom(compileOnly);

        configurations.all(configuration -> configuration.resolutionStrategy(resolutionStrategy -> {
            /* 动态版本依赖缓存 10 minutes */
            resolutionStrategy.cacheDynamicVersionsFor(10, TimeUnit.MINUTES);
            /* SNAPSHOT版本依赖缓存 0 seconds */
            resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS);
        }));
    }

    /**
     * 清理任务增加删除目录
     *
     * @param project Project
     */
    public void configureClean(Project project) {
        /* 清理任务增加删除目录 */
        project.getTasks().withType(Delete.class, delete -> delete.delete("out"));
    }


    /**
     * 配置默认仓库
     *
     * @param project Project
     */
    public void configureRepositories(Project project) {
        DevToolsExtension extension = project.getExtensions().getByType(DevToolsExtension.class);
        project.afterEvaluate(pro -> {

            /* 本地仓库地址 */
            this.addMavenLocal(extension.getLocalMavenRepository());

            /* Ali 云效 */
            extension.getAli().all(aliYun -> {
                project.getLogger().warn("aliYun: " + aliYun);
                this.addMavenRepository(aliYun.getUrl(), aliYun.getUsername(), aliYun.getPassword());
            });

            /* 公共仓库 */
            extension.getMaven().all(maven -> {
                project.getLogger().warn("maven: " + maven);
                this.addMavenRepository(maven.getUrl(), maven.getUsername(), maven.getPassword());
            });
        });
    }

    /**
     * 配置默认依赖
     *
     * @param project Project
     */
    public void configureDependencies(Project project) {
        DevToolsExtension extension = project.getExtensions().getByType(DevToolsExtension.class);
        project.afterEvaluate(pro -> {
            /* lombok */
            if (extension.isLombok()) {
                this.addAnnotationProcessor(DepEnum.LOMBOK);
            }

            /* Java tools */
            if (extension.isJavaTools()) {
                String tools = System.getProperty("java.home").replace("jre", "") + "/lib/tools.jar";
                this.addDependency(JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME, project.files(tools));
            }

            /* Google Auto Service */
            if (extension.isGoogleAutoService()) {
                this.addAnnotationProcessor(DepEnum.GOOGLE_AUTO_SERVICE);
            }

            /* Junit */
            String junitVersion = ExtraPropsUtils.getStringValue(project, ExtraProps.JUNIT_VERSION);
            if (JUNIT_4_PATTERN.matcher(junitVersion).matches()) {
                this.addTestImplementation(DepEnum.JUNIT_4);
            } else if (JUNIT_5_PATTERN.matcher(junitVersion).matches()) {
                this.addTestImplementation(DepEnum.JUNIT_5);
            }

            /* Kotlin */
            if (extension.isKotlin()) {
                JavaVersion kotlinJvmVersion = JavaVersion.current();
                switch (kotlinJvmVersion){
                    case VERSION_1_7:
                        this.addImplementation(DepEnum.KOTLIN_JDK_7);
                        break;
                    case VERSION_1_8:
                        this.addImplementation(DepEnum.KOTLIN_JDK_8);
                        break;
                    default:
                        this.addImplementation(DepEnum.KOTLIN_JDK);
                }
            }

        });
    }
}

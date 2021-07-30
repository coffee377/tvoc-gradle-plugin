//package com.voc.gradle.plugin;
//
//import com.voc.gradle.plugin.extension.BootExtension;
//import org.gradle.api.Project;
//import org.gradle.api.plugins.PluginContainer;
//import org.gradle.api.tasks.TaskContainer;
//import org.gradle.jvm.tasks.Jar;
//import org.springframework.boot.gradle.tasks.bundling.BootJar;
//
///**
// * @author Wu Yujie
// * @email coffee377@dingtalk.com
// * @time 2020/12/09 14:57
// */
//public class BootPlugin extends AbstractPlugin {
//    public static final String BOOT_EXTENSION_NAME = "boot";
//
//    @Override
//    public void onApply(Project project) {
//        project.getExtensions().create(BOOT_EXTENSION_NAME, BootExtension.class, project);
//        PluginContainer plugins = project.getPlugins();
//        plugins.apply("org.springframework.boot");
//        plugins.apply("io.spring.dependency-management");
//        this.configureBoot(project);
//    }
//
//    private void configureBoot(Project project) {
//        project.afterEvaluate(p -> {
//            BootExtension extension = p.getExtensions().getByType(BootExtension.class);
//            boolean library = extension.getLibrary().getOrElse(Boolean.FALSE);
//            TaskContainer tasks = p.getTasks();
//            tasks.withType(BootJar.class, bootJar -> bootJar.setEnabled(!library));
//            tasks.withType(Jar.class, jar -> jar.setEnabled(library));
//        });
//
//    }
//
//}

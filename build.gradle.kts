plugins {
    java
    groovy
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}

group = "com.voc"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    /* Ali Yun central仓和jcenter仓的聚合仓 */
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://repo.maven.apache.org/maven2") {
        name = "Maven Central"
    }
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-gradle-plugin", "2.3.5.RELEASE")
    //    api('io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE')
    annotationProcessor("org.projectlombok", "lombok", "1.18.20")
}

configurations {
    compileOnly.get().extendsFrom(annotationProcessor.get())
    testCompileOnly.get().extendsFrom(compileOnly.get())
}

tasks.jar {
    archiveBaseName.set("gradle-plugin")
}

gradlePlugin {
    plugins {
        create("devtools") {
            id = "com.voc.devtools"
            implementationClass = "com.voc.gradle.plugin.DevToolsPlugin"
        }
    }
}

publishing {
    repositories {
        val snapshots = version.toString().endsWith("SNAPSHOT", true)
        val shortUrl = if (snapshots) "snapshots" else "releases"

        maven("$buildDir/repos/$shortUrl") {
            name = "Local_Development_Repository"
        }

        maven("http://192.168.44.155/nexus/content/repositories/$shortUrl") {
            name = "JinQi_Repository"
        }
    }
}

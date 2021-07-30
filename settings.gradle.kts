pluginManagement {
    repositories {
        /* 本地仓库地址 */
        maven("D:/SoftWare/Maven/repository")
        /* 公司仓库地址 */
        maven("http://192.168.44.155/nexus/content/groups/public/")
        /* 插件门户地址 */
        gradlePluginPortal()
    }
}

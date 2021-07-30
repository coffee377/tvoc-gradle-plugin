import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/10 09:08
 */
class SettingsPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    def p = project.rootProject
    println(p.name)
//    FileTree fileTree
//    settings.rootProject.file
//    settings.include()
  }
}

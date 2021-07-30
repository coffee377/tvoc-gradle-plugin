package com.voc.gradle.plugin.extension;

import com.voc.gradle.plugin.repository.AliYunMavenRepository;
import com.voc.gradle.plugin.repository.MavenRepository;
import lombok.Getter;
import lombok.Setter;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2019/07/23 09:11
 */
@Getter
@Setter
public class DevToolsExtension {

    private final NamedDomainObjectContainer<MavenRepository> maven;
    private final NamedDomainObjectContainer<AliYunMavenRepository> ali;

    /**
     * 使用阿里代理的仓库 {@link "https://maven.aliyun.com/mvn/guide"}
     */
    private boolean aliMaven = true;

    /**
     * 本地 maven 地址
     */
    private String localMavenRepository;

    /**
     * 是否使用 kotlin
     */
    private boolean kotlin;

    /**
     * 是否使用 groovy
     */
    private boolean groovy;

    /**
     * 是否使用 Google AutoService
     */
    private boolean googleAutoService;

    /**
     * 是否使用 java tools
     */
    private boolean javaTools;

    /**
     * 是否使用 lombok，默认使用
     */
    private boolean lombok = true;

    public DevToolsExtension(Project project) {
        this.maven = project.container(MavenRepository.class);
        this.ali = project.container(AliYunMavenRepository.class);
    }

    public void maven(Action<? super NamedDomainObjectContainer<MavenRepository>> action) {
        action.execute(this.maven);
    }

    public void ali(Action<? super NamedDomainObjectContainer<AliYunMavenRepository>> action) {
        action.execute(this.ali);
    }

}

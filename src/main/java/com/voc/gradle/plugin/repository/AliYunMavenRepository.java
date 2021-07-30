package com.voc.gradle.plugin.repository;

import com.voc.gradle.plugin.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/10 14:49
 */
@Getter
@Setter
public class AliYunMavenRepository extends MavenRepository {

    /**
     * 云效仓库前缀
     */
    private String prefix = "https://packages.aliyun.com/maven/repository";

    /**
     * 云效仓库 ID
     */
    private String id;

    /**
     * 是否快照仓库
     */
    private boolean snapshot;

    /**
     * 仓库验证码
     */
    private String hash;

    public AliYunMavenRepository(String name) {
        super(name);
    }

    @Override
    public String getUrl() {
        if (StringUtils.isNotEmpty(prefix) && StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(hash)) {
            return String.format("%s/%s-%s-%s/", prefix, id, snapshot ? "snapshot" : "release", hash);
        }
        return super.getUrl();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

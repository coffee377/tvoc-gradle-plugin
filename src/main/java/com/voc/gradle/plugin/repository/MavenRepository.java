package com.voc.gradle.plugin.repository;

import org.gradle.api.Named;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author Wu Yujie
 * @email coffee377@dingtalk.com
 * @time 2020/12/10 14:49
 */
public class MavenRepository implements Named, Serializable {

    /**
     * 名称
     */
    private final String name;

    /**
     * 仓库地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public MavenRepository(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return url.hashCode() + username.hashCode() + password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MavenRepository && this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return getUrl();
    }
}

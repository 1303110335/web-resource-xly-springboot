/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author xuleyan
 * @version WebResourceProperties.java, v 0.1 2021-05-22 10:52 上午
 */
@ConfigurationProperties(prefix = "web-resource")
public class WebResourceProperties implements InitializingBean {

    /**
     * 文件模式
     */
    private String fileDic;
    private List<String> filePrefix;

    @Value("${web-resource.cache-enable:true}")
    private boolean cacheEnable;
    @Value("${web-resource.cache-refresh-time:30}")
    private int cacheRefreshTime;

    private List<String> resourcePattern;

    public String getFileDic() {
        return fileDic;
    }

    public void setFileDic(String fileDic) {
        this.fileDic = fileDic;
    }

    public List<String> getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(List<String> filePrefix) {
        this.filePrefix = filePrefix;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public int getCacheRefreshTime() {
        return cacheRefreshTime;
    }

    public void setCacheRefreshTime(int cacheRefreshTime) {
        this.cacheRefreshTime = cacheRefreshTime;
    }

    public List<String> getResourcePattern() {
        return resourcePattern;
    }

    public void setResourcePattern(List<String> resourcePattern) {
        this.resourcePattern = resourcePattern;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (filePrefix == null) {
            filePrefix = new ArrayList<>();
        }
        this.filePrefix = fix(this.filePrefix);
        if (resourcePattern == null) {
            resourcePattern = new ArrayList<>();
        }
    }

    private List<String> fix(List<String> source) {
        return source.stream().map(prefix -> !prefix.startsWith("/") ? "/" + prefix : prefix).collect(Collectors.toList());
    }
}
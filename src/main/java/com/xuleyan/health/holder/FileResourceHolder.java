/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.holder;

import com.xuleyan.health.WebResourceProperties;
import com.xuleyan.health.cache.CacheHolder;
import com.xuleyan.health.cache.WebResourceCacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.util.List;

/**
 *
 * @author xuleyan
 * @version FileResourceHolder.java, v 0.1 2021-05-22 11:42 上午
 */
@Order(0)
@Slf4j
public class FileResourceHolder implements ResourceHolder, WebResourceCacheable {
    private final WebResourceProperties webResourceProperties;
    private CacheHolder cacheHolder;

    public FileResourceHolder(WebResourceProperties webResourceProperties) {
        this.webResourceProperties = webResourceProperties;
    }

    @Override
    public void setCacheHolder(CacheHolder cacheHolder) {
        this.cacheHolder = cacheHolder;
    }

    @Override
    public List<String> prefix() {
        return webResourceProperties.getFilePrefix();
    }

    @Override
    public Resource get(String path) {
        return cacheHolder.get(path, () -> {
            try {
                return new FileUrlResource(webResourceProperties.getFileDic() + path);
            } catch (MalformedURLException e) {
                log.error("文件不存在", e);
                return null;
            }
        });
    }
}
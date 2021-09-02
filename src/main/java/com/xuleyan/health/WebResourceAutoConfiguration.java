/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health;

import com.xuleyan.health.cache.CacheHolder;
import com.xuleyan.health.cache.DefaultCacheHolder;
import com.xuleyan.health.cache.DisabledCacheHolder;
import com.xuleyan.health.cache.WebResourceCacheBeanPostProcessor;
import com.xuleyan.health.holder.CommonHolder;
import com.xuleyan.health.holder.FileResourceHolder;
import com.xuleyan.health.holder.ResourceHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 *
 * @author xuleyan
 * @version WebResourceAutoConfiguration.java, v 0.1 2021-05-22 10:57 上午
 */
public class WebResourceAutoConfiguration {

    @Bean
    public WebResourceProperties webResourceProperties() {
        return new WebResourceProperties();
    }

    @Bean
    @ConditionalOnMissingBean(CacheHolder.class)
    public CacheHolder cacheHolder(WebResourceProperties webResourceProperties) {
        CacheHolder cacheHolder = null;
        if (webResourceProperties.isCacheEnable()) {
            cacheHolder = new DefaultCacheHolder(webResourceProperties.getCacheRefreshTime());
        } else {
            cacheHolder = new DisabledCacheHolder();
        }
        return cacheHolder;
    }

    @Bean
    public WebResourceCacheBeanPostProcessor webResourceCacheBeanFactoryPostProcessor(CacheHolder cacheHolder, WebResourceProperties webResourceProperties) {
        WebResourceCacheBeanPostProcessor webResourceCacheBeanFactoryPostProcessor = new WebResourceCacheBeanPostProcessor();
        if (webResourceProperties.isCacheEnable()) {
            webResourceCacheBeanFactoryPostProcessor.setCacheHolder(cacheHolder);
        } else {
            webResourceCacheBeanFactoryPostProcessor.setCacheHolder(cacheHolder);
        }
        return webResourceCacheBeanFactoryPostProcessor;
    }

    @Bean
    public WebStaticResourceFilter webStaticResourceFilter(CommonHolder commonHolder, WebResourceProperties webResourceProperties) {
        return new WebStaticResourceFilter(commonHolder, webResourceProperties);
    }

    @Bean
    public CommonHolder commonHolder(List<ResourceHolder> resourceHolders) {
        CommonHolder commonHolder = new CommonHolder();
        commonHolder.setResourceHolders(resourceHolders);
        return commonHolder;
    }

    @Bean
    @ConditionalOnMissingBean(FileResourceHolder.class)
    public ResourceHolder fileResourceHolder(WebResourceProperties webResourceProperties) {
        return new FileResourceHolder(webResourceProperties);
    }




}
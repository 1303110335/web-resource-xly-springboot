/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * @author xuleyan
 * @version WebResourceCacheBeanPostProcessor.java, v 0.1 2021-05-22 12:11 下午
 */
@Slf4j
public class WebResourceCacheBeanPostProcessor implements BeanPostProcessor {

    private CacheHolder cacheHolder;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebResourceCacheable) {
            WebResourceCacheable cacheable = (WebResourceCacheable) bean;
            cacheable.setCacheHolder(cacheHolder);
        }
        return bean;
    }

    public void setCacheHolder(CacheHolder cacheHolder) {
        log.info("当前使用的 CacheHolder 是[{}]", cacheHolder.getClass().getName());
        this.cacheHolder = cacheHolder;
    }
}
/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.cache;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

import java.util.function.Supplier;

/**
 *
 * @author xuleyan
 * @version DefaultCacheHolder.java, v 0.1 2021-05-22 12:19 下午
 */
public class DefaultCacheHolder extends AbstractReadWriteCache<String, Resource> implements CacheHolder, DisposableBean {

    public DefaultCacheHolder(int expire) {
        super(expire);
    }

    @Override
    public void put(String key, Resource resource) {
        super.put(key, resource);
    }

    @Override
    public void invalidate(String key) {
        super.invalidate(key);
    }

    @Override
    public Resource get(String key, Supplier<Resource> supplier) {
        return super.get(key, supplier);
    }

    @Override
    public void destroy() throws Exception {
        this.close();
    }
}
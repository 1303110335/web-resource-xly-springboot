/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.cache;

import org.springframework.core.io.Resource;

import java.util.function.Supplier;

/**
 *
 * @author xuleyan
 * @version DisabledCacheHolder.java, v 0.1 2021-05-23 10:18 上午
 */
public class DisabledCacheHolder implements CacheHolder {
    @Override
    public void put(String key, Resource resource) {

    }

    @Override
    public void invalidate(String key) {

    }

    @Override
    public Resource get(String key, Supplier<Resource> supplier) {
        return supplier.get();
    }
}
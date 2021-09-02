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
 * @version CacheHolder.java, v 0.1 2021-05-22 11:10 上午
 */
public interface CacheHolder {
    void put(String key, Resource resource);

    void invalidate(String key);

    Resource get(String key, Supplier<Resource> supplier);
}
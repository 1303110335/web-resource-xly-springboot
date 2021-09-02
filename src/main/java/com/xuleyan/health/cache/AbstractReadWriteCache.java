/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 *
 * @author xuleyan
 * @version AbstractReadWriteCache.java, v 0.1 2021-05-22 12:19 下午
 */
@Slf4j
public abstract class AbstractReadWriteCache<K, V> {
    /**
     * 只读缓存
     */
    private final Map<K, V> readOnlyCache;
    /**
     * 读写缓存
     */
    private final Cache<K, V> readWriteCache;
    protected static ScheduledExecutorService scheduledExecutorService;

    private final int expire;

    public AbstractReadWriteCache(int expire) {
        this.expire = expire;

        if (scheduledExecutorService == null) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "read-write-cache-thread");
                }
            });
        }

        readOnlyCache = new ConcurrentHashMap<>();
        readWriteCache = Caffeine.newBuilder()
                .expireAfterWrite(expire, TimeUnit.SECONDS)
                .expireAfterAccess(expire, TimeUnit.SECONDS)
                .maximumSize(1 << 10)
                .build();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            if (log.isDebugEnabled()) {
                log.debug("刷新只读缓存");
            }
            ConcurrentMap<K, V> all = readWriteCache.asMap();
            readOnlyCache.clear();
            all.forEach(readOnlyCache::put);
        }, expire, expire, TimeUnit.SECONDS);
    }

    public void put(K key, V v) {
        readOnlyCache.put(key, v);
    }

    public void invalidate(String key) {
        readOnlyCache.remove(key);
    }

    public V get(K key, Supplier<V> supplier) {
        V v = readOnlyCache.get(key);
        if (v == null) {
            v = readWriteCache.get(key, s -> supplier.get());
            readOnlyCache.put(key, v);
        }
        return v;
    }

    public void close() throws Exception {
        scheduledExecutorService.shutdown();
    }

}
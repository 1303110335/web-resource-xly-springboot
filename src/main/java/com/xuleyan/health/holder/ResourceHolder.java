/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.holder;

import org.springframework.core.io.Resource;

import java.util.List;

/**
 *
 * @author xuleyan
 * @version ResourceHolder.java, v 0.1 2021-05-22 11:21 上午
 */
public interface ResourceHolder {
    List<String> prefix();

    Resource get(String path);
}
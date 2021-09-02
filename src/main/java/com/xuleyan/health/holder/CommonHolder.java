/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.health.holder;

import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xuleyan
 * @version CommonHolder.java, v 0.1 2021-05-22 11:22 上午
 */
public class CommonHolder implements ResourceHolder {

    private List<ResourceHolder> resourceHolders;

    @Override
    public List<String> prefix() {
        List<String> list = new ArrayList<>();
        for (ResourceHolder resourceHolder : resourceHolders) {
            list.addAll(resourceHolder.prefix());
        }
        return list;
    }

    @Override
    public Resource get(String path) {
        Resource resource = null;
        for (ResourceHolder resourceHolder : resourceHolders) {
            for (String prefix : resourceHolder.prefix()) {
                if (path.startsWith(prefix)) {
                    resource = resourceHolder.get(path);
                    if (resource != null && resource.exists()) {
                        break;
                    }
                }
            }
        }
        return resource;
    }

    public void setResourceHolders(List<ResourceHolder> resourceHolders) {
        this.resourceHolders = resourceHolders;
    }
}
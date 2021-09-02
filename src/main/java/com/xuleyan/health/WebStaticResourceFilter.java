package com.xuleyan.health;

import com.xuleyan.health.holder.CommonHolder;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuleyan
 */
@Order(Integer.MIN_VALUE + 1)
public class WebStaticResourceFilter implements Filter {

    private CommonHolder commonHolder;
    private WebResourceProperties webResourceProperties;

    public WebStaticResourceFilter() {
    }

    public WebStaticResourceFilter(CommonHolder commonHolder, WebResourceProperties webResourceProperties) {
        this.commonHolder = commonHolder;
        this.webResourceProperties = webResourceProperties;
    }

    private final AntPathMatcher matcher = new AntPathMatcher();
    private final List<String> staticResources = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) {
        staticResources.addAll(Arrays.asList(StaticResourcePattern.DEFAULT_STATIC_RESOURCES));
        staticResources.addAll(webResourceProperties.getResourcePattern());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getRequestURI();
        for (String staticResource : staticResources) {
            if (matcher.match(staticResource, path)) {
                Resource resource = commonHolder.get(path);
                // 资源未找到
                if (resource == null || !resource.exists()) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void setWebResourceProperties(WebResourceProperties webResourceProperties) {
        this.webResourceProperties = webResourceProperties;
    }
}
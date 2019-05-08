package com.rjp.expandframework.route.interfaces;

import com.rjp.expandframework.route.bean.RouteMeta;

import java.util.Map;

public interface IRoute {
    void loadRoute(Map<String, RouteMeta> groups);
}

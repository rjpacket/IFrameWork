package com.rjp.expandframework.route;

import com.rjp.expandframework.route.bean.RouteMeta;
import com.rjp.expandframework.route.interfaces.IRoute;

import java.util.HashMap;
import java.util.Map;

public class RouteFactory {

    public static Map<String, RouteMeta> groups = new HashMap<>();

    public static Map<String, Class<? extends IRoute>> roots = new HashMap<>();
}

package com.rjp.logincomponent;

import com.rjp.expandframework.route.bean.RouteMeta;
import com.rjp.expandframework.route.interfaces.IRoute;

import java.util.Map;

public class LoginActivity$Route implements IRoute {
    @Override
    public void loadRoute(Map<String, RouteMeta> groups) {
        groups.put("/login/login", new RouteMeta(LoginActivity.class, "/login/login"));
        groups.put("/login/register", new RouteMeta(RegisterActivity.class, "/login/register"));
    }
}

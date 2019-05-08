package com.rjp.expandframework.route.bean;

public class RouteMeta {

    /**
     * 注解使用的类对象
     */
    private Class<?> destination;

    /**
     * 路由地址
     */
    private String path;

    public RouteMeta(Class<?> destination, String path) {
        this.destination = destination;
        this.path = path;
    }

    public Class<?> getDestination() {
        return destination;
    }

    public void setDestination(Class<?> destination) {
        this.destination = destination;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

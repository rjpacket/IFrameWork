package com.rjp.fastframework.mvvm;

public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "我的信息{" +
                "姓名='" + name + '\'' +
                ", 年纪='" + age + '\'' +
                '}';
    }
}

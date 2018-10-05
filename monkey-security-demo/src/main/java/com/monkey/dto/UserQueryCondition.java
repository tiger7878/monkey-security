package com.monkey.dto;

/**
 * 查询参数实体
 * @author: monkey
 * @date: 2018/10/5 17:34
 */
public class UserQueryCondition {

    private String username;
    /**
     * 年龄起始值
     */
    private int age;

    /**
     * 年龄结束值
     */
    private int ageTo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }
}

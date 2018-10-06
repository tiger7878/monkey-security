package com.monkey.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

/**
 * @author: monkey
 * @date: 2018/10/5 17:13
 */
public class User {

    //JsonView需要用到的两个接口视图
    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{};

    private String id;

    private String username;

    private String password;

    private Date birthday;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //UserSimpleView视图展示该字段
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //UserDetailView视图展示该字段
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}


package com.monkey.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.monkey.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
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

    @MyConstraint(message = "自定义错误校验")
    private String username;

    //不能为空
    @NotBlank(message = "密码不能为空")
    private String password;

    //过去的时间
    @Past(message = "生日必须为过去的时间")
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


package com.monkey.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 自定义用户信息
 * token使用redis时存储时可以authentication.getPrincipal()中获取，或者直接在action中注入UserDetails获取
 * token存在jwt中时authentication.getPrincipal()得到的是用户名，这个跟源码后发现
 * User: monkey
 * Date: 2020/6/10 10:42
 */
@Data
public class MyUserDetails extends User {

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 用户在数据库中的主键
     */
    private Long userId;
}

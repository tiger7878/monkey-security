package com.monkey.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义用户信息获取类
 *
 * @author: monkey
 * @date: 2018/10/7 16:41
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("登录的用户名：" + username);

        //真实情况是去数据库中查询
        //密码：123456，权限：admin（后面会重构）
//        return new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        //根据查找到的用户信息判断用户是否被冻结
        return new User(username, "123456",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));


    }
}

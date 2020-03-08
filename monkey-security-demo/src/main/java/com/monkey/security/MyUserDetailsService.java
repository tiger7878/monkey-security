package com.monkey.security;

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
//        return new User(username, "123456",
//                true, true, true, true,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        //配置了PasswordEncoder以后对明文密码加解密操作
        //测试发现，每次对于相同的字符串加密得到的是不同的密文，数据库中也不用保存加密用的盐，这种方法可以有效防止相同密码被识别出来
        //注册时保存一次加密的字符串到数据库中即可，以后比对密码就交给spring security来进行，
        String password=passwordEncoder.encode("123456");//真实场景这一步在注册的时候做加密，然后保存到数据库中。
        // 这里实际是从数据库中读取加密后的密码。
        logger.info("数据库中的密码是："+password);
        return new User(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));//这里一般这么写ROLE_作为前缀，ROLE_ADMIN
    }
}

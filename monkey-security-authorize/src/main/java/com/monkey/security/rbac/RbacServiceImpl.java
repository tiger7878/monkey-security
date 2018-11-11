package com.monkey.security.rbac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: monkey
 * @date: 2018/11/11 15:02
 */
@Component("rbacServiceImpl")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();//匹配器

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();

        boolean result = false;

        if (principal instanceof UserDetails) {

            String username = ((UserDetails) principal).getUsername();

            //TODO: 根据用户名去数据库中查询所有的权限
            Set<String> urls = new HashSet<>();

            urls.add("/user/*");//伪造一条，测试

            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    result = true;
                    break;
                }
            }

        }

        return result;
    }
}

package com.monkey.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 动态权限验证
 * @author: monkey
 * @date: 2018/11/11 15:00
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}

package com.monkey.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.monkey.dto.User;
import com.monkey.dto.UserQueryCondition;
import com.monkey.exception.UserNotExistException;
import com.monkey.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: monkey
 * @date: 2018/10/5 17:14
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserController", description = "用户控制器")
public class UserController {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 获取认证信息 Authentication
     * 这个信息比较全
     *
     * @param authentication 自动注入的认证后信息
     * @return
     */
    @GetMapping("/authentication")
    public Object getCurrentAuthentication(Authentication authentication, HttpServletRequest request) throws Exception {
        //方式一：
//        return SecurityContextHolder.getContext().getAuthentication();

        //方式二：
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");
        Claims claims = Jwts
                .parser()
                .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token)
                .getBody();

        //获取扩展信息
        String company = (String) claims.get("company");
        System.out.println("--->" + company);

        return authentication;
    }

    /**
     * 获取认证后的用户信息 UserDetails
     * 这个没有上面那个那么全，根据需求决定响应什么
     *
     * @param userDetails 认证后的用户信息
     * @return
     */
    @GetMapping("/userDetails")
    public Object getCurrentUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    /**
     * 请求参数写到方法中
     *
     * @param username
     * @return
     */
    @GetMapping("/query")
    @ApiOperation(value = "用户列表查询服务1", notes = "根据用户名查询")
    @ApiImplicitParam(name = "username", value = "姓名")
    public List<User> query(@RequestParam(name = "username", required = false, defaultValue = "tom") String username) {
        //打印请求参数
        System.out.println("username = " + username);

        List<User> users = new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 请求参数封装到实体中
     *
     * @param userQueryCondition
     * @return
     */
    @GetMapping("/query2")
    @ApiOperation(value = "用户列表查询服务2", notes = "根据查询实体查询")
    public List<User> query2(UserQueryCondition userQueryCondition) {
        //打印请求参数
        System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> users = new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 用Pageable作为参数，方便传入分页相关的参数
     *
     * @param pageable
     * @return
     */
    @GetMapping("/query3")
    public List<User> query3(@PageableDefault(page = 1, size = 15, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
        //打印请求参数
        System.out.println("getPageNumber:" + pageable.getPageNumber());
        System.out.println("getPageSize:" + pageable.getPageSize());
        System.out.println("getSort:" + pageable.getSort());

        List<User> users = new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User getInfo(@PathVariable String id) {

        System.out.println("进入getInfo服务！");

        //测试异常
        if (id.equals("3")) {
            throw new UserNotExistException(id);//抛出自定义异常
        } else if (id.equals("2")) {
            throw new RuntimeException("user not exist");//非自定义异常
        }

        User user = new User();
        user.setUsername("monkey");
        return user;
    }

    /**
     * 根据id获取用户信息
     * 请求参数用正则来限定只能是数字
     *
     * @param id
     * @return
     */
    @GetMapping("/user2/{id:\\d+}")
    public User getInfo2(@PathVariable String id) {
        User user = new User();
        user.setUsername("monkey");
        return user;
    }

    /**
     * 使用JsonView来指定要显示的字段-不显示密码
     *
     * @return
     */
    @GetMapping("/query4")
    @JsonView(User.UserSimpleView.class)
    public List<User> query4() {
        List<User> users = new ArrayList<User>();
        users.add(new User("monkey", "123"));
        users.add(new User("tom", "456"));
        users.add(new User("jack", "789"));
        return users;
    }

    /**
     * 使用JsonView来指定要显示的字段-显示密码
     *
     * @param id
     * @return
     */
    @GetMapping("/user3/{id}")
    @JsonView(User.UserDetailView.class)
    public User getInfo3(@PathVariable String id) {
        User user = new User();
        user.setUsername("monkey");
        user.setPassword("123");
        return user;
    }

    /**
     * 创建
     * RequestBody可以把请求的json字符串转换成实体对象中的属性值
     *
     * @param user
     * @return
     */
    @PostMapping
    public User create(@RequestBody User user) {
        System.out.println(user);
        user.setId("1");
        return user;
    }

    /**
     * 创建
     * RequestBody可以把请求的json字符串转换成实体对象中的属性值
     * Valid标签可以让实体进行校验，传参数错误请求就被拦截
     *
     * @param user
     * @return
     */
    @PostMapping("/create2")
    public User create2(@Valid @RequestBody User user) {
        System.out.println(user);
        user.setId("1");
        return user;
    }

    /**
     * 创建
     * RequestBody可以把请求的json字符串转换成实体对象中的属性值
     * Valid标签可以让实体进行校验，
     * BindingResult：传参数错误后请求可以进入方法，由自行控制是否往下执行
     *
     * @param user
     * @return
     */
    @PostMapping("/create3")
    public User create3(@Valid @RequestBody User user, BindingResult bindingResult) {

        //判断传入的参数是否有问题
        if (bindingResult.hasErrors()) {
            //打印所有错误信息
            bindingResult.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        System.out.println(user);
        user.setId("1");
        return user;
    }

    /**
     * update使用put方式
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PutMapping
    public User update(@Valid @RequestBody User user, BindingResult bindingResult) {

        //判断传入的参数是否有问题
        if (bindingResult.hasErrors()) {
            System.out.println("出现错误信息了=========start=========");
            //打印所有错误信息
            bindingResult.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField() + "：" + fieldError.getDefaultMessage();
                System.out.println(message);
            });
            System.out.println("出现错误信息了=========end=========");
        }

        System.out.println(user);
        user.setId("1");
        return user;
    }

    /**
     * delete使用delete方式
     *
     * @param id
     */
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println("id : " + id);
    }
}

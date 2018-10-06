package com.monkey.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: monkey
 * @date: 2018/10/5 16:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(get("/user/query") //get请求/user资源
//                .param("username","monkey") //请求参数
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) //期望响应码200
                .andExpect(jsonPath("$.length()").value(3)); //期望json集合中有3个元素
    }

    @Test
    public void whenQuerySuccess2() throws Exception {
        mockMvc.perform(get("/user/query2") //get请求/user资源
                .param("username","jack") //请求参数
                .param("age","18") //请求参数
                .param("ageTo","80") //请求参数
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) //期望响应码200
                .andExpect(jsonPath("$.length()").value(3)); //期望json集合中有3个元素
    }

    @Test
    public void whenQuerySuccess3() throws Exception {
        mockMvc.perform(get("/user/query3") //get请求/user资源
                .param("size","20") //请求参数
                .param("page","5") //请求参数
                .param("sort","age,desc") //请求参数
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) //期望响应码200
                .andExpect(jsonPath("$.length()").value(3)); //期望json集合中有3个元素
    }

    @Test
    public void testGetInfo() throws Exception {
        mockMvc.perform(get("/user/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("monkey"));
    }

    @Test
    public void testGetInfo2() throws Exception {
        mockMvc.perform(get("/user/user2/a").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenQuerySuccess4() throws Exception {
       String result= mockMvc.perform(get("/user/query4").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();//获取响应的值，放到string中
        System.out.println(result);
    }

    @Test
    public void testGetInfo3() throws Exception {
        String result=mockMvc.perform(get("/user/user3/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testCreate() throws Exception {
        Date date=new Date();
        System.out.println(date.getTime());//发请求的时候传递也是时间戳，响应回去也是时间戳
//        String content="{\"username\":\"monkey\",\"password\":\"123\",\"birthday\":"+date.getTime()+"}";//正常的参数
        String content="{\"username\":\"monkey\",\"password\":null,\"birthday\":"+date.getTime()+"}";//异常的参数
       String result= mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}

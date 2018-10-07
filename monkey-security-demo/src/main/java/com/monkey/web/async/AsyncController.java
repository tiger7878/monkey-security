package com.monkey.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * 多线程异步控制器
 * @author: monkey
 * @date: 2018/10/7 11:01
 */
@RestController
public class AsyncController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @RequestMapping("/order")
    public Callable<String> order(){
        logger.info("主线程start");

        Callable<String> result=new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("子线程start");

                Thread.sleep(1000);

                logger.info("子线程end");
                return "success";
            }
        };

        logger.info("主线程end");

        return result;
    }
}

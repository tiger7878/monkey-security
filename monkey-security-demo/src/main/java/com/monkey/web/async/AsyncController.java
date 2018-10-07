package com.monkey.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * 多线程异步控制器
 * @author: monkey
 * @date: 2018/10/7 11:01
 */
@RestController
public class AsyncController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

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

    @RequestMapping("/deferredResult")
    public DeferredResult<String> deferredResult(){
        logger.info("主线程start");

        String orderNumber= RandomStringUtils.randomNumeric(8);
        System.out.println("orderNumber : "+orderNumber);

        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result=new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber,result);//把数据放到DeferredResultHolder中

        logger.info("主线程end");

        return result;
    }
}

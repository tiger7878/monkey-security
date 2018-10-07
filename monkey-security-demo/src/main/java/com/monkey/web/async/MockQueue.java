package com.monkey.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 模拟消息队列
 *
 * @author: monkey
 * @date: 2018/10/7 11:48
 */
@Component
public class MockQueue {

    private String placeOrder;//下单的消息

    private String completeOrder;//订单完成的消息

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {

        //开一个线程来执行方法
        new Thread(() -> {

            //接到下单的消息后就进行订单完成处理
            logger.info("接到下单请求, " + placeOrder);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.placeOrder = placeOrder;
            this.completeOrder = placeOrder;
            logger.info("订单处理完毕：" + placeOrder);

        }).start();

    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}

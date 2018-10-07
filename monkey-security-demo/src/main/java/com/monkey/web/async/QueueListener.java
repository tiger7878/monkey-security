package com.monkey.web.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听器，监听消息队列-需要完善
 * @author: monkey
 * @date: 2018/10/7 12:33
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //新开一个线程来监听，不要阻塞主线程
        new Thread(()->{

            while (true){
                //判断订单是否处理了
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())){

                    String orderNumber=mockQueue.getCompleteOrder();

                    logger.info("监听到订单："+orderNumber+" 处理完成了。");

                    //setResult中就是返回的结果
                    deferredResultHolder.getMap().get(orderNumber).setResult("order success");

                    //处理一次就清空，否则死循环
                    mockQueue.setCompleteOrder(null);
                }else {
                    //没处理线程就等待
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }).start();

    }
}

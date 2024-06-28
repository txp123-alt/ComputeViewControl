package com.example.opencvforjava.ListenQueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听队列，前置处理逻辑
 * 负责调用算力，以及数据的前置处理
 */
@Component
@Slf4j
public class ListenQueuePerProcessing {


    //监听队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "MatQueue"),
            exchange = @Exchange(value = "MatExchange", type = ExchangeTypes.TOPIC),
            key = "Mat.*"
    ),  containerFactory = "rabbitListenerContainerFactory")
    public void listen(String message) {

        try {
            log.info("接收到消息：{}",message);
            Thread.sleep(1500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

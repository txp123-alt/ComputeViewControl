package com.example.opencvforjava;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpencvForJavaApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        for (int i=0; i<100; i++){
            rabbitTemplate.convertAndSend("MatExchange", "Mat.test", "测试消息:"+i);
        }
    }

}

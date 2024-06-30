package com.example.opencvforjava.ListenQueue;

import com.example.opencvforjava.Domain.ComputePointArrDto;
import com.example.opencvforjava.ImageComputingCenter.ComputeView;
import com.example.opencvforjava.PerProcessingCenter.AnalysisMessage;
import com.example.opencvforjava.PerProcessingCenter.Base64ToMat;
import com.example.opencvforjava.PostProcessingCenter.PostToQueue;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 监听队列，前置处理逻辑
 * 负责调用算力，以及数据的前置处理
 */
@Component
@Slf4j
public class ListenQueuePerProcessing {


    //监听队列
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "MatQueue"),
//            exchange = @Exchange(value = "MatExchange", type = ExchangeTypes.TOPIC),
//            key = "Mat.*"
//    ),  containerFactory = "rabbitListenerContainerFactory")
    @RabbitListener(queues = "MatQueue", containerFactory = "rabbitListenerContainerFactory")
    public void listen(String dataString, Message message, Channel channel) {

        log.info("接收到消息：{}",dataString);
        //前置处理逻辑
        ComputePointArrDto analysisDto = AnalysisMessage.analysis(dataString);

        try {
            //计算逻辑
            //List<Map<String, Double>> maps = ComputeView.templateMatching(analysisDto.getTemplateImage(), analysisDto.getTargetImage());
            //analysisDto.setResultPointArr(maps);
            //后置处理逻辑
            //PostToQueue.toSuccessQueue(analysisDto);
        }catch (Exception e){
            e.printStackTrace();
            PostToQueue.toFailQueue(analysisDto);
        }
    }
}

package com.example.opencvforjava.Domain;

import lombok.Data;
import org.opencv.core.Mat;

import java.util.List;
import java.util.Map;

/**
 * 模版匹配后的结果点集合
 */
@Data
public class ComputePointArrDto {

    /**
     * 终端设备设别码(user_ip(内网)_port(内网))
     */
    String IdentificationCode;

    /**
     * 当次请求唯一标识(ip(公网)_时间戳)
     */
    String AccessId;

    //消息发送到MQ的时间
    long mqTimeStamp;

    //计算耗时
    long computeDuration;

    //请求的图像
    Mat TemplateImage;

    //目标图像
    Mat targetImage;

    //计算结果(坐标点)
    List<Map<String,Double>> resultPointArr;
}

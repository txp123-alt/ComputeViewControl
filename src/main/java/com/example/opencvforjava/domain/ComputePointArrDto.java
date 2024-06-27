package com.example.opencvforjava.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 模版匹配后的结果点集合
 */
@Data
public class ComputePointArrDto {

    //终端设备设别码(ip + port)
    String IdentificationCode;

    //消息发送到MQ的时间
    long mqTimeStamp;

    //计算耗时
    long computeDuration;

    //计算结果(坐标点)
    List<Map<String,Integer>> resultPointArr;
}

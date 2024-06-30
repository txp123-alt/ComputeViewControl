package com.example.opencvforjava.PostProcessingCenter;

import com.example.opencvforjava.Domain.ComputePointArrDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostToQueue {
    public static void toSuccessQueue(ComputePointArrDto computePointArrDto){
        log.info("add success queue");
    }

    public static void toFailQueue(ComputePointArrDto computePointArrDto){
        log.info("add fail queue");
    }
}

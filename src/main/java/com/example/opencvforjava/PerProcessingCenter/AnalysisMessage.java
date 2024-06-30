package com.example.opencvforjava.PerProcessingCenter;

import com.example.opencvforjava.Domain.ComputePointArrDto;
import org.opencv.core.Mat;

public class AnalysisMessage {

    public static ComputePointArrDto analysis(String message){
        ComputePointArrDto computePointArrDto = new ComputePointArrDto();
        //封装目标图像
        computePointArrDto.setTemplateImage(Base64ToMat.toMat(message));
        return computePointArrDto;
    }
}

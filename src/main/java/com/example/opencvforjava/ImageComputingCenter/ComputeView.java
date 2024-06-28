package com.example.opencvforjava.ImageComputingCenter;

import com.alibaba.fastjson.JSON;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Feature2D;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SIFT;

import java.util.*;

/**
 * 图像计算中心（项目中最吃算力的位置）
 */
public class ComputeView {


    /**
     * 图像计算主逻辑
     * @param TemplateImage
     * @param targetImage
     * @return
     */
    public static List<Map<String,Double>> templateMatching(Mat TemplateImage, Mat targetImage){

        //转为灰度图
        Mat mat1 = new Mat();
        Imgproc.cvtColor(TemplateImage,mat1,Imgproc.COLOR_BGR2GRAY);
        Mat mat2 = new Mat();
        Imgproc.cvtColor(targetImage,mat2,Imgproc.COLOR_BGR2GRAY);

        System.out.println("1~~~");
        //生成特征点
        Feature2D sift = SIFT.create();
        MatOfKeyPoint matOfKeyPoint1 = new MatOfKeyPoint();
        Mat mat_point1 = new Mat();
        sift.detectAndCompute(TemplateImage,new Mat(),matOfKeyPoint1,mat_point1);

        MatOfKeyPoint matOfKeyPoint2 = new MatOfKeyPoint();
        Mat mat_point2 = new Mat();
        sift.detectAndCompute(targetImage,new Mat(),matOfKeyPoint2,mat_point2);
        //绘制关键点
        keyPoint(mat1,matOfKeyPoint1);
        keyPoint(mat2,matOfKeyPoint2);

        //初始化特征匹配器
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
        //开始匹配
        MatOfDMatch matOfDMatch = new MatOfDMatch();
        descriptorMatcher.match(mat_point1,mat_point2,matOfDMatch);

        System.out.println("结果数量："+matOfDMatch.rows());

        if (matOfDMatch.rows() > 10) {
            // 按距离排序匹配结果（默认已经是按距离排序的，但如果是其他匹配器可能需要手动排序）
            // 注意：某些匹配器（如FLANN）可能返回未排序的匹配结果
            List<DMatch> matchesList = matOfDMatch.toList();
            Collections.sort(matchesList, new Comparator<DMatch>() {
                @Override
                public int compare(DMatch m1, DMatch m2) {
                    return Double.compare(m1.distance, m2.distance);
                }
            });

            // 保留前十个最佳匹配
            List<DMatch> bestMatches = matchesList.subList(0, 10);

            // 如果需要，你可以将筛选后的匹配结果转换回MatOfDMatch对象
            MatOfDMatch bestMatchesMat = new MatOfDMatch();
            bestMatchesMat.fromList(bestMatches);

            // 现在你可以使用bestMatchesMat进行后续操作
            //绘制匹配结果
            //Mat imgMat = new Mat();
            //Features2d.drawMatches(TemplateImage,matOfKeyPoint1,targetImage,matOfKeyPoint2,bestMatchesMat,imgMat);
            //图像展示
            //System.out.println("匹配完成，展示图像");
            //showWindow(imgMat);

            // 访问匹配的坐标点
            List<KeyPoint> keyPoints1 = matOfKeyPoint1.toList();
            List<KeyPoint> keyPoints2 = matOfKeyPoint2.toList();
            //打印前10个坐标
            return print_point(bestMatches, keyPoints1, keyPoints2);
        } else {
            // 如果没有足够的匹配结果，你可以处理这种情况
            System.out.println("未能有足够的匹配点");
        }
        return null;
    }

    /**
     * 坐标处理
     * @param bestMatches
     * @param keyPoints1
     * @param keyPoints2
     * @return
     */
    private static List<Map<String,Double>> print_point(List<DMatch> bestMatches, List<KeyPoint> keyPoints1, List<KeyPoint> keyPoints2) {
        ArrayList<Map<String, Double>> resultList = new ArrayList<>();
        for (DMatch match : bestMatches) {
            KeyPoint queryPoint = keyPoints1.get(match.queryIdx);
            //KeyPoint trainPoint = keyPoints2.get(match.trainIdx);

            // 现在你可以使用这些坐标点进行进一步的处理或显示
            //System.out.println("Query Point: " + queryPoint.pt + ", Train Point: " + trainPoint.pt);

            Point pt = queryPoint.pt;
            HashMap<String, Double> item = new HashMap<>();
            item.put("X",pt.x);
            item.put("Y",pt.y);
            resultList.add(item);
        }
        System.out.println(JSON.toJSONString(resultList));
        return resultList;
    }

    /**
     * 展示图像, windows调试时使用
     * @param imread1
     * @param matOfKeyPoint
     */
    private static void keyPoint(Mat imread1, MatOfKeyPoint matOfKeyPoint) {
        Mat mat3 = new Mat();
        Features2d.drawKeypoints(imread1,matOfKeyPoint,mat3,new Scalar(0,255,0),Features2d.DRAW_RICH_KEYPOINTS);
        //HighGui.imshow("关键点",mat3);
        //HighGui.waitKey(0);
    }

    /**
     * 展示图像, windows调试时使用
     * @param mat
     */
    private static void showWindow(Mat mat) {
        String windowName = "Display Image";
        HighGui.namedWindow(windowName, HighGui.WINDOW_NORMAL); // 可以调整窗口大小

        // 显示图像
        HighGui.imshow(windowName, mat);
        // 等待用户按键，然后关闭窗口
        HighGui.waitKey(0); // 0表示无限等待，直到有按键事件
        // 销毁窗口
        HighGui.destroyAllWindows();
    }
}

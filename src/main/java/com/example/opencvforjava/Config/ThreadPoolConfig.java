package com.example.opencvforjava.Config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 线程池基本参数配置类
 */
@Component
@Slf4j
@Data
public class ThreadPoolConfig {

    /**
     * cpu 核心数
     */
    private static final int CPU_CORE_NUM;

    /**
     * 线程池中线程名称前缀
     */
    @Value("${thread.pool.executor.threadNamePrefix}")
    private String threadNamePrefix;

    /**
     * 线程池核心线程数
     */
    private Integer corePoolSize;

    /**
     * 线程池中的任务队列, 通过execute()方法提交的Runable线程对象会保存到这个队列中
     */
    @Value("${thread.pool.executor.queueCapacity}")
    private Integer queueCapacity;

    /**
     * 线程池所能容纳的最大线程数，当活动线程达(核心线程 + 非核心线程)到这个数值后，后续任务会根据RejectedExecutionHandler来进行拒绝处理
     */
    private Integer maxPoolSize;

    /**
     * 当任务无法被执行时(超过线程最大容量 maximum 并且 workQueue 已经被排满了)的处理策略，
     * - AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
     * - DiscardPolicy：丢弃任务，但是不抛出异常。
     * - DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务
     * - CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
     */
    @Value("${thread.pool.executor.rejectedExecutionHandler}")
    private String rejectedExecutionHandler;

    /**
     * 非核心线程 闲置时的超时时长。超过该时长，非核心线程就会被回收。
     * 若线程池通设置核心线程也允许 timeOut，即 allowCoreThreadTimeOut 为 true，则该时长同样会作用于核心线程，在超过aliveTime 时，核心线程也会被回收，AsyncTask配置的线程池就是这样设置的。
     */
    @Value("${thread.pool.executor.keepAliveSeconds}")
    private Integer keepAliveSeconds;


    public ThreadPoolConfig(){
        log.info("当前设备的CPU核心数为：{}个",CPU_CORE_NUM);
        this.corePoolSize = CPU_CORE_NUM + 1;
        this.maxPoolSize = CPU_CORE_NUM * 2;
    }


    static {
        //获取当前环境CPU核数
        CPU_CORE_NUM = Runtime.getRuntime().availableProcessors();
    }
}

package com.example.opencvforjava.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 */
@Configuration
@Slf4j
public class PoolExecutor {

    private final ThreadPoolConfig threadPoolConfig;

    public PoolExecutor(ThreadPoolConfig threadPoolConfig){
        this.threadPoolConfig = threadPoolConfig;
    }

    @Bean
    public Executor threadPoolTaskExecutor(){
        log.info("线程名称前缀：{}",threadPoolConfig.getThreadNamePrefix());
        log.info("核心线程数：{}",threadPoolConfig.getCorePoolSize());
        log.info("最大线程数：{}",threadPoolConfig.getMaxPoolSize());
        log.info("拒绝策略：{}",new ThreadPoolExecutor.AbortPolicy());
        log.info("非核心线程空闲时等待时长：{}",threadPoolConfig.getKeepAliveSeconds()+"");
        log.info("Runable线程队列长度：{}",threadPoolConfig.getQueueCapacity()+"");

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        try{
            threadPoolTaskExecutor.setThreadNamePrefix(threadPoolConfig.getThreadNamePrefix());
            threadPoolTaskExecutor.setCorePoolSize(threadPoolConfig.getCorePoolSize());
            threadPoolTaskExecutor.setMaxPoolSize(threadPoolConfig.getMaxPoolSize());
            threadPoolTaskExecutor.setQueueCapacity(threadPoolConfig.getQueueCapacity());
            threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
            threadPoolTaskExecutor.setKeepAliveSeconds(threadPoolConfig.getKeepAliveSeconds());

            //初始化线程池
            threadPoolTaskExecutor.initialize();
            log.info("-----------{}----------","线程池初始化完成");
            return threadPoolTaskExecutor;
        }catch (Exception e){
            e.printStackTrace();
            log.info("线程池初始化异常:{}",e.getMessage());
        }
        return null;
    }
}

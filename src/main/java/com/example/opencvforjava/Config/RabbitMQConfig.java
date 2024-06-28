package com.example.opencvforjava.Config;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // RabbitMQ服务器配置
    private static final String HOST = "39.107.255.39";
    private static final int PORT = 5672;
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "123456";
    private static final String VIRTUAL_HOST = "/"; // 默认虚拟主机

    // 创建连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(HOST, PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        // 如果需要，可以配置其他属性，例如连接超时、通道最大数、连接缓存大小等
        // connectionFactory.setConnectionTimeout(...);
        // connectionFactory.setChannelCacheSize(...);
        return connectionFactory;
    }

    // 创建消息监听容器工厂
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //获取当前系统cpu核心数
        int cpuCoreSize = Runtime.getRuntime().availableProcessors();
        System.out.println("当前设备的CPU核心数为:"+cpuCoreSize);
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(cpuCoreSize + 1); // 设置同时处理消息的消费者数量
        factory.setMaxConcurrentConsumers(cpuCoreSize * 2); // 设置最大并发消费者数量
        // 如果需要，可以配置其他属性，例如消费者标签策略、事务管理等
        // factory.setConsumerTagStrategy(...);
        // factory.setTransactionManager(...);
        return factory;
    }
}

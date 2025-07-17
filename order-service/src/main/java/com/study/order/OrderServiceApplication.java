package com.study.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 订单服务启动类
 * 
 * 订单服务的主要功能：
 * 1. 订单创建和管理
 * 2. 调用用户服务获取用户信息
 * 3. 演示微服务间的通信
 * 
 * 关键注解说明：
 * @EnableDiscoveryClient: 启用服务发现客户端，向注册中心注册
 * @EnableFeignClients: 启用Feign客户端，用于服务间调用
 * 
 * Feign是什么？
 * Feign是一个声明式的HTTP客户端，它使得编写HTTP客户端变得更简单
 * 只需要创建一个接口并添加注解，Feign就会自动生成实现类
 * 支持负载均衡、服务发现等功能
 * 
 * @author SpringCloud学习项目
 */
// SpringBoot应用启动注解
@SpringBootApplication
// 启用服务发现客户端，向注册中心注册
@EnableDiscoveryClient
// 启用Feign客户端，用于服务间调用
@EnableFeignClients
public class OrderServiceApplication {
    
    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动SpringBoot应用
        SpringApplication.run(OrderServiceApplication.class, args);
        
        System.out.println("\n" +
                "=================================================\n" +
                "  订单服务启动成功！\n" +
                "  服务端口: 8082\n" +
                "  API文档: http://localhost:8082/order/test\n" +
                "  该服务已注册到Eureka注册中心\n" +
                "  支持调用用户服务获取用户信息\n" +
                "=================================================\n");
    }
}
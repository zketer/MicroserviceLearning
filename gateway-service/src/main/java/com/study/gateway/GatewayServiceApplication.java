package com.study.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 * 
 * Spring Cloud Gateway是SpringCloud官方提供的API网关
 * 基于Spring 5、Spring Boot 2和Project Reactor构建
 * 
 * 网关的主要功能：
 * 1. 路由转发：将客户端请求路由到对应的微服务
 * 2. 负载均衡：在多个服务实例之间分发请求
 * 3. 统一鉴权：在网关层进行身份验证和授权
 * 4. 限流熔断：保护后端服务不被过量请求压垮
 * 5. 日志监控：统一记录请求日志和监控指标
 * 6. 协议转换：支持HTTP、WebSocket等多种协议
 * 
 * 与Zuul的区别：
 * 1. 性能更好：基于Netty，支持异步非阻塞
 * 2. 更好的集成：与SpringCloud生态集成更紧密
 * 3. 更灵活的路由：支持多种路由匹配方式
 * 4. 更强的过滤器：支持全局和局部过滤器
 * 
 * @author SpringCloud学习项目
 */
// SpringBoot应用启动注解
@SpringBootApplication
// 启用服务发现客户端，向注册中心注册
@EnableDiscoveryClient
public class GatewayServiceApplication {
    
    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动SpringBoot应用
        SpringApplication.run(GatewayServiceApplication.class, args);
        
        System.out.println("\n" +
                "=================================================\n" +
                "  API网关启动成功！\n" +
                "  网关端口: 8080\n" +
                "  用户服务: http://localhost:8080/user-service/**\n" +
                "  订单服务: http://localhost:8080/order-service/**\n" +
                "  所有请求将通过网关统一转发\n" +
                "=================================================\n");
    }
}
package com.study.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动类
 * 
 * 这是一个典型的SpringBoot微服务应用
 * 主要功能：
 * 1. 用户注册、登录
 * 2. 用户信息管理
 * 3. 用户状态管理
 * 
 * 微服务特点：
 * 1. 独立部署：可以单独部署和运行
 * 2. 数据隔离：拥有自己的数据库
 * 3. 服务注册：向Eureka注册中心注册
 * 4. 对外提供API：通过REST接口提供服务
 * 
 * @author SpringCloud学习项目
 */
// SpringBoot应用启动注解
@SpringBootApplication
// 启用服务发现客户端，向注册中心注册
@EnableDiscoveryClient
public class UserServiceApplication {
    
    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动SpringBoot应用
        SpringApplication.run(UserServiceApplication.class, args);
        
        System.out.println("\n" +
                "=================================================\n" +
                "  用户服务启动成功！\n" +
                "  服务端口: 8081\n" +
                "  API文档: http://localhost:8081/user/test\n" +
                "  该服务已注册到Eureka注册中心\n" +
                "=================================================\n");
    }
}
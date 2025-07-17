package com.study.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka注册中心启动类
 * 
 * Eureka是Netflix开源的服务注册与发现组件
 * 在微服务架构中，各个服务需要知道其他服务的位置信息
 * Eureka Server作为注册中心，管理所有微服务的注册信息
 * 
 * 主要功能：
 * 1. 服务注册：微服务启动时向Eureka注册自己的信息
 * 2. 服务发现：微服务可以从Eureka获取其他服务的位置信息
 * 3. 健康检查：定期检查注册服务的健康状态
 * 4. 服务剔除：将不健康的服务从注册表中移除
 * 
 * @author SpringCloud学习项目
 */
// SpringBoot应用启动注解
@SpringBootApplication
// 启用Eureka服务器功能
@EnableEurekaServer
public class EurekaServerApplication {
    
    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动SpringBoot应用
        SpringApplication.run(EurekaServerApplication.class, args);
        
        System.out.println("\n" +
                "=================================================\n" +
                "  Eureka注册中心启动成功！\n" +
                "  访问地址: http://localhost:8761\n" +
                "  在浏览器中可以查看注册的服务列表\n" +
                "=================================================\n");
    }
}
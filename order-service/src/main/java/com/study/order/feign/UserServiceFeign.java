package com.study.order.feign;

import com.study.common.entity.User;
import com.study.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务Feign客户端
 * 
 * Feign是SpringCloud提供的声明式HTTP客户端
 * 通过@FeignClient注解，可以像调用本地方法一样调用远程服务
 * 
 * 主要特点：
 * 1. 声明式：只需要定义接口，不需要实现
 * 2. 集成Ribbon：自动实现负载均衡
 * 3. 集成Hystrix：支持熔断降级
 * 4. 支持多种编码器和解码器
 * 
 * @FeignClient注解参数说明：
 * - name/value: 服务名称，对应Eureka中注册的服务名
 * - url: 直接指定服务地址（一般不用，使用服务发现）
 * - fallback: 降级处理类
 * - configuration: 自定义配置类
 * 
 * @author SpringCloud学习项目
 */
@FeignClient(
    // 要调用的服务名称，必须与用户服务在Eureka中注册的名称一致
    name = "user-service",
    // 降级处理类，当服务不可用时执行
    fallback = UserServiceFeignFallback.class
)
public interface UserServiceFeign {
    
    /**
     * 根据用户ID获取用户信息
     * 
     * 这个方法的定义必须与用户服务中对应接口的定义完全一致：
     * - 请求方式：GET
     * - 请求路径：/user/{id}
     * - 参数类型：Long
     * - 返回类型：Result<User>
     * 
     * Feign会根据这些信息自动生成HTTP请求
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    Result<User> getUserById(@PathVariable("id") Long id);
    
    /**
     * 测试用户服务连接
     * 
     * 用于验证服务间通信是否正常
     * 
     * @return 测试结果
     */
    @GetMapping("/user/test")
    Result<String> testUserService();
}
package com.study.order.feign;

import com.study.common.entity.User;
import com.study.common.result.Result;
import org.springframework.stereotype.Component;

/**
 * 用户服务Feign客户端降级处理类
 * 
 * 降级（Fallback）是微服务容错的重要机制
 * 当目标服务不可用时（网络异常、服务宕机、超时等），会执行降级方法
 * 
 * 降级的作用：
 * 1. 提高系统可用性：即使依赖服务不可用，当前服务仍能正常运行
 * 2. 防止雪崩效应：避免因一个服务故障导致整个系统崩溃
 * 3. 提供友好的错误信息：给用户返回有意义的提示
 * 
 * 实现要点：
 * 1. 必须实现对应的Feign接口
 * 2. 添加@Component注解，让Spring管理
 * 3. 降级方法的签名必须与原方法完全一致
 * 4. 返回合理的默认值或错误信息
 * 
 * @author SpringCloud学习项目
 */
// Spring组件注解，让Spring容器管理这个类
@Component
public class UserServiceFeignFallback implements UserServiceFeign {
    
    /**
     * 获取用户信息的降级方法
     * 
     * 当用户服务不可用时，会执行这个方法
     * 返回一个表示服务不可用的错误结果
     * 
     * @param id 用户ID
     * @return 降级结果
     */
    @Override
    public Result<User> getUserById(Long id) {
        // 记录日志（实际项目中应该使用日志框架）
        System.err.println("用户服务调用失败，执行降级方法，用户ID: " + id);
        
        // 返回降级结果
        return Result.error(500, "用户服务暂时不可用，请稍后重试");
    }
    
    /**
     * 测试用户服务的降级方法
     * 
     * @return 降级结果
     */
    @Override
    public Result<String> testUserService() {
        // 记录日志
        System.err.println("用户服务连接测试失败，执行降级方法");
        
        // 返回降级结果
        return Result.error(500, "用户服务连接失败");
    }
}
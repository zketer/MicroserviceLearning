package com.study.user.controller;

import com.study.common.entity.User;
import com.study.common.result.Result;
import com.study.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 
 * Controller层负责：
 * 1. 接收HTTP请求
 * 2. 参数验证和转换
 * 3. 调用Service层处理业务逻辑
 * 4. 返回统一格式的响应结果
 * 
 * RESTful API设计原则：
 * - GET: 查询操作
 * - POST: 创建操作
 * - PUT: 更新操作
 * - DELETE: 删除操作
 * 
 * @author SpringCloud学习项目
 */
// @Controller + @ResponseBody，返回JSON格式数据
@RestController
// 请求路径前缀
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;
    
    /**
     * 测试接口
     * 用于验证服务是否正常运行
     * 
     * 访问地址：GET http://localhost:8081/user/test
     * 
     * @return 测试结果
     */
    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("用户服务运行正常！");
    }
    
    /**
     * 用户注册
     * 
     * 访问地址：POST http://localhost:8081/user/register
     * 请求体：JSON格式的用户信息
     * 
     * 示例请求体：
     * {
     *   "username": "testuser",
     *   "password": "123456",
     *   "email": "test@example.com",
     *   "phone": "13800138000"
     * }
     * 
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        try {
            boolean success = userService.registerUser(user);
            if (success) {
                return Result.success("注册成功");
            } else {
                return Result.error("注册失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 用户登录
     * 
     * 访问地址：POST http://localhost:8081/user/login
     * 请求参数：username, password
     * 
     * 示例：POST http://localhost:8081/user/login?username=testuser&password=123456
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<User> login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.login(username, password);
            if (user != null) {
                // 不返回密码信息
                user.setPassword(null);
                return Result.success("登录成功", user);
            } else {
                return Result.error("用户名或密码错误");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据ID查询用户信息
     * 
     * 访问地址：GET http://localhost:8081/user/{id}
     * 路径参数：用户ID
     * 
     * 示例：GET http://localhost:8081/user/1
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                // 不返回密码信息
                user.setPassword(null);
                return Result.success(user);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取所有活跃用户
     * 
     * 访问地址：GET http://localhost:8081/user/active
     * 
     * @return 用户列表
     */
    @GetMapping("/active")
    public Result<List<User>> getActiveUsers() {
        try {
            List<User> users = userService.getActiveUsers();
            // 清除密码信息
            users.forEach(user -> user.setPassword(null));
            return Result.success(users);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新用户信息
     * 
     * 访问地址：PUT http://localhost:8081/user/update
     * 请求体：JSON格式的用户信息（必须包含ID）
     * 
     * 示例请求体：
     * {
     *   "id": 1,
     *   "email": "newemail@example.com",
     *   "phone": "13900139000"
     * }
     * 
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
        try {
            boolean success = userService.updateUser(user);
            if (success) {
                return Result.success("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 禁用用户
     * 
     * 访问地址：PUT http://localhost:8081/user/disable/{id}
     * 路径参数：用户ID
     * 
     * 示例：PUT http://localhost:8081/user/disable/1
     * 
     * @param id 用户ID
     * @return 操作结果
     */
    @PutMapping("/disable/{id}")
    public Result<String> disableUser(@PathVariable Long id) {
        try {
            boolean success = userService.disableUser(id);
            if (success) {
                return Result.success("用户已禁用");
            } else {
                return Result.error("操作失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
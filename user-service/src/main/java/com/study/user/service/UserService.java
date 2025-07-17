package com.study.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.entity.User;
import com.study.user.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户业务逻辑层
 * 
 * Service层是业务逻辑的核心，负责：
 * 1. 业务规则的实现
 * 2. 数据验证
 * 3. 事务管理
 * 4. 调用Mapper层进行数据操作
 * 
 * 继承ServiceImpl可以获得MyBatis Plus提供的基础服务方法
 * 
 * @author SpringCloud学习项目
 */
// Spring注解，标识这是一个服务类
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    
    @Resource
    private UserMapper userMapper;
    
    /**
     * 用户注册
     * 
     * 业务逻辑：
     * 1. 验证用户名是否已存在
     * 2. 验证邮箱是否已存在
     * 3. 设置默认值
     * 4. 保存用户信息
     * 
     * @param user 用户信息
     * @return 注册结果
     */
    public boolean registerUser(User user) {
        // 1. 参数验证
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new RuntimeException("用户名和密码不能为空");
        }
        
        // 2. 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 3. 检查邮箱是否已存在（如果提供了邮箱）
        if (StringUtils.hasText(user.getEmail())) {
            User existingEmailUser = userMapper.selectByEmail(user.getEmail());
            if (existingEmailUser != null) {
                throw new RuntimeException("邮箱已被注册");
            }
        }
        
        // 4. 设置默认值
        // 默认状态为正常
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 5. 保存用户（实际项目中密码应该加密）
        int result = userMapper.insert(user);
        return result > 0;
    }
    
    /**
     * 用户登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 用户信息（登录成功）或null（登录失败）
     */
    public User login(String username, String password) {
        // 1. 参数验证
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return null;
        }
        
        // 2. 根据用户名查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            // 用户不存在
            return null;
        }
        
        // 3. 验证密码（实际项目中应该验证加密后的密码）
        if (!password.equals(user.getPassword())) {
            // 密码错误
            return null;
        }
        
        // 4. 检查用户状态
        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }
        
        return user;
    }
    
    /**
     * 根据用户ID查询用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    public User getUserById(Long userId) {
        if (userId == null) {
            return null;
        }
        return userMapper.selectById(userId);
    }
    
    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 更新结果
     */
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        
        // 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        
        int result = userMapper.updateById(user);
        return result > 0;
    }
    
    /**
     * 获取所有活跃用户
     * 
     * @return 用户列表
     */
    public List<User> getActiveUsers() {
        return userMapper.selectActiveUsers();
    }
    
    /**
     * 禁用用户
     * 
     * @param userId 用户ID
     * @return 操作结果
     */
    public boolean disableUser(Long userId) {
        if (userId == null) {
            return false;
        }
        
        User user = new User();
        user.setId(userId);
        // 设置为禁用状态
        user.setStatus(0);
        user.setUpdateTime(LocalDateTime.now());
        
        int result = userMapper.updateById(user);
        return result > 0;
    }
}
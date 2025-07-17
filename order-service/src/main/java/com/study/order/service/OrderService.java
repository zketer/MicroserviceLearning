package com.study.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.entity.Order;
import com.study.common.entity.User;
import com.study.common.result.Result;
import com.study.order.feign.UserServiceFeign;
import com.study.order.mapper.OrderMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * 订单业务逻辑层
 * 
 * 订单服务的核心业务逻辑，包括：
 * 1. 订单创建和管理
 * 2. 调用用户服务获取用户信息
 * 3. 订单状态管理
 * 
 * 微服务调用示例：
 * 通过Feign客户端调用用户服务，获取用户信息
 * 这是微服务架构中服务间通信的典型场景
 * 
 * @author SpringCloud学习项目
 */
// Spring服务注解，标识这是一个业务服务类
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
    
    // 注入OrderMapper
    @Resource
    private OrderMapper orderMapper;

    // 注入Feign客户端
    @Resource
    private UserServiceFeign userServiceFeign;
    
    /**
     * 创建订单
     * 
     * 业务流程：
     * 1. 验证用户是否存在（调用用户服务）
     * 2. 生成订单编号
     * 3. 计算订单总金额
     * 4. 保存订单信息
     * 
     * @param order 订单信息
     * @return 创建结果
     */
    public boolean createOrder(Order order) {
        // 1. 参数验证
        if (order == null || order.getUserId() == null) {
            throw new RuntimeException("订单信息不完整");
        }
        
        if (!StringUtils.hasText(order.getProductName()) || order.getQuantity() == null || order.getPrice() == null) {
            throw new RuntimeException("商品信息不完整");
        }
        
        // 2. 验证用户是否存在（微服务调用示例）
        try {
            Result<User> userResult = userServiceFeign.getUserById(order.getUserId());
            if (!userResult.isSuccess() || userResult.getData() == null) {
                throw new RuntimeException("用户不存在或用户服务不可用");
            }
            
            User user = userResult.getData();
            System.out.println("获取到用户信息：" + user.getUsername());
            
        } catch (Exception e) {
            // 这里演示了微服务调用失败的处理
            System.err.println("调用用户服务失败：" + e.getMessage());
            throw new RuntimeException("验证用户信息失败：" + e.getMessage());
        }
        
        // 3. 生成订单编号
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        
        // 4. 计算总金额
        BigDecimal totalAmount = order.getPrice().multiply(new BigDecimal(order.getQuantity()));
        order.setTotalAmount(totalAmount);
        
        // 5. 设置默认值
        // 默认状态：待支付
        order.setStatus(1);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        // 6. 保存订单
        int result = orderMapper.insert(order);
        return result > 0;
    }
    
    /**
     * 根据订单ID查询订单详情（包含用户信息）
     * 
     * 这个方法演示了如何在查询订单的同时获取关联的用户信息
     * 
     * @param orderId 订单ID
     * @return 订单详情
     */
    public Order getOrderWithUserInfo(Long orderId) {
        if (orderId == null) {
            return null;
        }
        
        // 1. 根据ID查询订单基本信息
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return null;
        }
        
        // 2. 通过Feign客户端调用用户服务获取用户信息（微服务调用示例）
        try {
            Result<User> userResult = userServiceFeign.getUserById(order.getUserId());
            if (userResult.isSuccess() && userResult.getData() != null) {
                User user = userResult.getData();
                System.out.println("订单关联用户：" + user.getUsername());
                // 这里可以将用户信息设置到订单对象中，或者创建一个包含用户详情的订单DTO返回
            }
        } catch (Exception e) {
            System.err.println("获取用户信息失败：" + e.getMessage());
            // 即使获取用户信息失败，也返回订单基本信息，保证核心功能可用
        }
        
        return order;
    }
    
    /**
     * 根据用户ID查询订单列表
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    public List<Order> getOrdersByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return orderMapper.selectByUserId(userId);
    }
    
    /**
     * 更新订单状态
     * 
     * @param orderId 订单ID
     * @param status 新状态
     * @return 更新结果
     */
    public boolean updateOrderStatus(Long orderId, Integer status) {
        if (orderId == null || status == null) {
            return false;
        }
        
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        order.setUpdateTime(LocalDateTime.now());
        
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    /**
     * 根据订单编号查询订单
     * 
     * @param orderNo 订单编号
     * @return 订单信息
     */
    public Order getOrderByOrderNo(String orderNo) {
        if (!StringUtils.hasText(orderNo)) {
            return null;
        }
        return orderMapper.selectByOrderNo(orderNo);
    }
    
    /**
     * 测试用户服务连接
     * 
     * 这个方法用于测试微服务间的通信是否正常
     * 
     * @return 测试结果
     */
    public String testUserServiceConnection() {
        try {
            Result<String> result = userServiceFeign.testUserService();
            if (result.isSuccess()) {
                return "用户服务连接正常：" + result.getData();
            } else {
                return "用户服务连接异常：" + result.getMessage();
            }
        } catch (Exception e) {
            return "用户服务连接失败：" + e.getMessage();
        }
    }
    
    /**
     * 生成订单编号
     * 
     * 格式：yyyyMMddHHmmss + 4位随机数
     * 
     * @return 订单编号
     */
    private String generateOrderNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        
        Random random = new Random();
        // 生成4位随机数(1000-9999)作为订单号后缀
        int randomNum = random.nextInt(9999) + 1000;
        
        return timestamp + randomNum;
    }
}
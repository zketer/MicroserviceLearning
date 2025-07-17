package com.study.order.controller;

import com.study.common.entity.Order;
import com.study.common.result.Result;
import com.study.order.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 * 
 * 提供订单相关的REST API接口
 * 演示微服务架构中的服务间调用
 * 
 * @author SpringCloud学习项目
 */
// @Controller + @ResponseBody
@RestController
// 请求路径前缀
@RequestMapping("/order")
public class OrderController {
    
    @Resource
    private OrderService orderService;
    
    /**
     * 测试接口
     * 
     * 访问地址：GET http://localhost:8082/order/test
     * 
     * @return 测试结果
     */
    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("订单服务运行正常！");
    }
    
    /**
     * 测试用户服务连接
     * 
     * 这个接口演示了微服务间的调用
     * 订单服务通过Feign客户端调用用户服务
     * 
     * 访问地址：GET http://localhost:8082/order/test-user-service
     * 
     * @return 测试结果
     */
    @GetMapping("/test-user-service")
    public Result<String> testUserService() {
        try {
            String result = orderService.testUserServiceConnection();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("测试失败：" + e.getMessage());
        }
    }
    
    /**
     * 创建订单
     * 
     * 访问地址：POST http://localhost:8082/order/create
     * 请求体：JSON格式的订单信息
     * 
     * 示例请求体：
     * {
     *   "userId": 1,
     *   "productName": "iPhone 14",
     *   "quantity": 1,
     *   "price": 5999.00
     * }
     * 
     * 注意：这个接口会调用用户服务验证用户是否存在
     * 
     * @param order 订单信息
     * @return 创建结果
     */
    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody Order order) {
        try {
            boolean success = orderService.createOrder(order);
            if (success) {
                return Result.success("订单创建成功，订单号：" + order.getOrderNo());
            } else {
                return Result.error("订单创建失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据订单ID查询订单详情
     * 
     * 访问地址：GET http://localhost:8082/order/{id}
     * 路径参数：订单ID
     * 
     * 示例：GET http://localhost:8082/order/1
     * 
     * 这个接口会同时获取订单信息和关联的用户信息
     * 
     * @param id 订单ID
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderWithUserInfo(id);
            if (order != null) {
                return Result.success(order);
            } else {
                return Result.error("订单不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据用户ID查询订单列表
     * 
     * 访问地址：GET http://localhost:8082/order/user/{userId}
     * 路径参数：用户ID
     * 
     * 示例：GET http://localhost:8082/order/user/1
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据订单编号查询订单
     * 
     * 访问地址：GET http://localhost:8082/order/no/{orderNo}
     * 路径参数：订单编号
     * 
     * 示例：GET http://localhost:8082/order/no/20231201120000001
     * 
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @GetMapping("/no/{orderNo}")
    public Result<Order> getOrderByOrderNo(@PathVariable String orderNo) {
        try {
            Order order = orderService.getOrderByOrderNo(orderNo);
            if (order != null) {
                return Result.success(order);
            } else {
                return Result.error("订单不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新订单状态
     * 
     * 访问地址：PUT http://localhost:8082/order/status
     * 请求参数：orderId, status
     * 
     * 示例：PUT http://localhost:8082/order/status?orderId=1&status=2
     * 
     * 订单状态说明：
     * 1: 待支付
     * 2: 已支付
     * 3: 已发货
     * 4: 已完成
     * 5: 已取消
     * 
     * @param orderId 订单ID
     * @param status 新状态
     * @return 更新结果
     */
    @PutMapping("/status")
    public Result<String> updateOrderStatus(@RequestParam Long orderId, @RequestParam Integer status) {
        try {
            boolean success = orderService.updateOrderStatus(orderId, status);
            if (success) {
                return Result.success("订单状态更新成功");
            } else {
                return Result.error("订单状态更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
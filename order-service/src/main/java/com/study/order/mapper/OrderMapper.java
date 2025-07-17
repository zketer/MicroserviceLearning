package com.study.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.common.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单数据访问层接口
 * 
 * 继承MyBatis Plus的BaseMapper接口，自动获得基础的CRUD操作
 * 同时定义一些订单相关的自定义查询方法
 * 
 * @author SpringCloud学习项目
 */
/**
 * MyBatis注解，标识这是一个Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 根据用户ID查询订单列表
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Order> selectByUserId(Long userId);
    
    /**
     * 根据订单编号查询订单
     * 
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @Select("SELECT * FROM orders WHERE order_no = #{orderNo}")
    Order selectByOrderNo(String orderNo);
    
    /**
     * 根据订单状态查询订单列表
     * 
     * @param status 订单状态
     * @return 订单列表
     */
    @Select("SELECT * FROM orders WHERE status = #{status} ORDER BY create_time DESC")
    List<Order> selectByStatus(Integer status);
    
    /**
     * 查询用户的订单数量
     * 
     * @param userId 用户ID
     * @return 订单数量
     */
    @Select("SELECT COUNT(*) FROM orders WHERE user_id = #{userId}")
    Integer countByUserId(Long userId);
}
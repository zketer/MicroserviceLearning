package com.study.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * 对应数据库中的orders表
 * 演示微服务之间的数据关联
 * 
 * @author SpringCloud学习项目
 */
// Lombok注解，自动生成getter、setter、toString等方法
@Data
// MyBatis Plus注解，指定对应的数据库表名
@TableName("orders")
public class Order {
    
    /**
     * 订单ID（主键）
     * @TableId注解指定主键字段，IdType.AUTO表示自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 订单编号
     * 业务唯一标识，用于对外展示
     */
    private String orderNo;
    
    /**
     * 用户ID
     * 关联用户表，表示该订单属于哪个用户
     * 这里演示微服务之间的数据关联
     */
    private Long userId;
    
    /**
     * 商品名称
     * 简化处理，实际项目中可能有单独的商品表
     */
    private String productName;
    
    /**
     * 商品数量
     */
    private Integer quantity;
    
    /**
     * 单价
     * 使用BigDecimal确保金额计算精度
     */
    private BigDecimal price;
    
    /**
     * 总金额
     * 计算公式：quantity * price
     */
    private BigDecimal totalAmount;
    
    /**
     * 订单状态
     * 1: 待支付
     * 2: 已支付
     * 3: 已发货
     * 4: 已完成
     * 5: 已取消
     */
    private Integer status;
    
    /**
     * 创建时间
     * 记录订单创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     * 记录订单最后修改时间
     */
    private LocalDateTime updateTime;
}
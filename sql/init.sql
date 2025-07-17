-- SpringCloud学习项目数据库初始化脚本
-- 创建数据库和表结构

-- 创建数据库
CREATE DATABASE IF NOT EXISTS springcloud_study DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE springcloud_study;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态：1-正常，0-禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_phone` (`phone`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 创建订单表
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `product_name` varchar(200) NOT NULL COMMENT '商品名称',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `price` decimal(10,2) NOT NULL COMMENT '单价',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '订单状态：1-待支付，2-已支付，3-已发货，4-已完成，5-已取消',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 插入测试数据

-- 插入测试用户
INSERT INTO `user` (`username`, `password`, `email`, `phone`, `status`) VALUES
('admin', '123456', 'admin@example.com', '13800138000', 1),
('testuser', '123456', 'test@example.com', '13800138001', 1),
('john', '123456', 'john@example.com', '13800138002', 1),
('jane', '123456', 'jane@example.com', '13800138003', 1);

-- 插入测试订单
INSERT INTO `orders` (`order_no`, `user_id`, `product_name`, `quantity`, `price`, `total_amount`, `status`) VALUES
('20231201120000001', 1, 'iPhone 14', 1, 5999.00, 5999.00, 2),
('20231201120000002', 1, 'MacBook Pro', 1, 12999.00, 12999.00, 3),
('20231201120000003', 2, 'iPad Air', 2, 3999.00, 7998.00, 1),
('20231201120000004', 3, 'AirPods Pro', 1, 1999.00, 1999.00, 4),
('20231201120000005', 4, 'Apple Watch', 1, 2999.00, 2999.00, 2);

-- 查询验证数据
SELECT '用户数据' as '表名', COUNT(*) as '记录数' FROM user
UNION ALL
SELECT '订单数据' as '表名', COUNT(*) as '记录数' FROM orders;

-- 显示表结构
SHOW CREATE TABLE user;
SHOW CREATE TABLE orders;
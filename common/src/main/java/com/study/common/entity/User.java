package com.study.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的user表
 * 使用MyBatis Plus注解简化数据库操作
 * 
 * @author SpringCloud学习项目
 */
// Lombok注解，自动生成getter、setter、toString等方法
@Data
// MyBatis Plus注解，指定对应的数据库表名
@TableName("user")
public class User {
    
    /**
     * 用户ID（主键）
     * @TableId注解指定主键字段，IdType.AUTO表示自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户名
     * 用于用户登录，要求唯一
     */
    private String username;
    
    /**
     * 密码
     * 实际项目中应该加密存储
     */
    private String password;
    
    /**
     * 邮箱
     * 用于用户联系和找回密码
     */
    private String email;
    
    /**
     * 手机号
     * 用于用户联系和验证
     */
    private String phone;
    
    /**
     * 用户状态
     * 1: 正常
     * 0: 禁用
     */
    private Integer status;
    
    /**
     * 创建时间
     * 记录用户注册时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     * 记录用户信息最后修改时间
     */
    private LocalDateTime updateTime;
}
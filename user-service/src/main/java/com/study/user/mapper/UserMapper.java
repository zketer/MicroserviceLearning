package com.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户数据访问层接口
 * 
 * 继承MyBatis Plus的BaseMapper接口，自动获得基础的CRUD操作
 * BaseMapper提供的方法包括：
 * - insert(T entity): 插入一条记录
 * - deleteById(Serializable id): 根据ID删除
 * - updateById(T entity): 根据ID更新
 * - selectById(Serializable id): 根据ID查询
 * - selectList(Wrapper<T> queryWrapper): 条件查询
 * 
 * 除了基础方法外，还可以自定义查询方法
 * 
 * @author SpringCloud学习项目
 */
/**
 * MyBatis注解，标识这是一个Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户
     * 
     * 使用@Select注解直接编写SQL语句
     * 这是MyBatis的注解方式，适合简单查询
     * 复杂查询建议使用XML方式
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectByEmail(String email);
    
    /**
     * 查询所有正常状态的用户
     * 
     * @return 用户列表
     */
    @Select("SELECT * FROM user WHERE status = 1 ORDER BY create_time DESC")
    List<User> selectActiveUsers();
    
    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User selectByPhone(String phone);
}
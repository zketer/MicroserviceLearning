package com.study.common.result;

import lombok.Data;

/**
 * 统一响应结果封装类
 * 用于统一所有接口的返回格式，包含状态码、消息和数据
 * 
 * @param <T> 返回数据的类型
 * @author SpringCloud学习项目
 */
@Data
public class Result<T> {
    
    /**
     * 响应状态码
     * 200: 成功
     * 400: 客户端错误
     * 500: 服务器错误
     */
    private Integer code;
    
    /**
     * 响应消息
     * 用于描述请求处理结果
     */
    private String message;
    
    /**
     * 响应数据
     * 泛型类型，可以是任意类型的数据
     */
    private T data;
    
    /**
     * 私有构造方法
     * 防止外部直接实例化，统一通过静态方法创建
     */
    private Result() {}
    
    /**
     * 成功响应（无数据）
     * 
     * @return 成功的响应结果
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }
    
    /**
     * 成功响应（带数据）
     * 
     * @param data 要返回的数据
     * @return 成功的响应结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    /**
     * 成功响应（自定义消息和数据）
     * 
     * @param message 自定义消息
     * @param data 要返回的数据
     * @return 成功的响应结果
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    /**
     * 失败响应
     * 
     * @param message 错误消息
     * @return 失败的响应结果
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 失败响应（自定义状态码）
     * 
     * @param code 状态码
     * @param message 错误消息
     * @return 失败的响应结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 判断是否成功
     * 
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
}
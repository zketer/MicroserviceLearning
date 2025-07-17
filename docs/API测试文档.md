# API接口测试文档

本文档提供了完整的API接口测试示例，帮助你快速验证微服务功能。

## 测试工具推荐

- **Postman**: 图形化接口测试工具
- **curl**: 命令行工具
- **IDEA HTTP Client**: IntelliJ IDEA内置工具

## 服务启动顺序

1. Eureka注册中心 (端口: 8761)
2. 用户服务 (端口: 8081)
3. 订单服务 (端口: 8082)
4. 网关服务 (端口: 8080)

## 1. 基础连通性测试

### 1.1 测试Eureka注册中心

```bash
# 访问Eureka管理界面
GET http://localhost:8761
```

### 1.2 测试用户服务

```bash
# 测试用户服务是否正常
GET http://localhost:8081/user/test

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "用户服务运行正常！"
}
```

### 1.3 测试订单服务

```bash
# 测试订单服务是否正常
GET http://localhost:8082/order/test

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "订单服务运行正常！"
}
```

### 1.4 测试网关服务

```bash
# 通过网关访问用户服务
GET http://localhost:8080/user/test

# 通过网关访问订单服务
GET http://localhost:8080/order/test
```

## 2. 用户服务接口测试

### 2.1 用户注册

```bash
# 注册新用户
POST http://localhost:8081/user/register
Content-Type: application/json

{
  "username": "testuser2024",
  "password": "123456",
  "email": "testuser2024@example.com",
  "phone": "13900139999"
}

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "注册成功"
}
```

### 2.2 用户登录

```bash
# 使用已存在的用户登录
POST http://localhost:8081/user/login?username=admin&password=123456

# 预期响应
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "id": 1,
    "username": "admin",
    "password": null,
    "email": "admin@example.com",
    "phone": "13800138000",
    "status": 1,
    "createTime": "2023-12-01T12:00:00",
    "updateTime": "2023-12-01T12:00:00"
  }
}
```

### 2.3 查询用户信息

```bash
# 根据用户ID查询
GET http://localhost:8081/user/1

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "admin",
    "email": "admin@example.com",
    "phone": "13800138000",
    "status": 1
  }
}
```

### 2.4 查询活跃用户列表

```bash
# 获取所有正常状态的用户
GET http://localhost:8081/user/active

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "username": "admin",
      "email": "admin@example.com",
      "phone": "13800138000",
      "status": 1
    }
  ]
}
```

### 2.5 更新用户信息

```bash
# 更新用户信息
PUT http://localhost:8081/user/update
Content-Type: application/json

{
  "id": 1,
  "email": "newemail@example.com",
  "phone": "13900139000"
}

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "更新成功"
}
```

## 3. 订单服务接口测试

### 3.1 测试服务间调用

```bash
# 测试订单服务调用用户服务
GET http://localhost:8082/order/test-user-service

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "用户服务连接正常：用户服务运行正常！"
}
```

### 3.2 创建订单

```bash
# 创建新订单（会调用用户服务验证用户）
POST http://localhost:8082/order/create
Content-Type: application/json

{
  "userId": 1,
  "productName": "iPhone 15 Pro",
  "quantity": 1,
  "price": 8999.00
}

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "订单创建成功，订单号：20231201120000006"
}
```

### 3.3 查询订单详情

```bash
# 根据订单ID查询（会获取关联的用户信息）
GET http://localhost:8082/order/1

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "orderNo": "20231201120000001",
    "userId": 1,
    "productName": "iPhone 14",
    "quantity": 1,
    "price": 5999.00,
    "totalAmount": 5999.00,
    "status": 2,
    "createTime": "2023-12-01T12:00:00",
    "updateTime": "2023-12-01T12:00:00"
  }
}
```

### 3.4 查询用户订单列表

```bash
# 根据用户ID查询订单列表
GET http://localhost:8082/order/user/1

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "orderNo": "20231201120000001",
      "userId": 1,
      "productName": "iPhone 14",
      "quantity": 1,
      "price": 5999.00,
      "totalAmount": 5999.00,
      "status": 2
    }
  ]
}
```

### 3.5 根据订单号查询

```bash
# 根据订单编号查询
GET http://localhost:8082/order/no/20231201120000001

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "orderNo": "20231201120000001",
    "userId": 1,
    "productName": "iPhone 14",
    "quantity": 1,
    "price": 5999.00,
    "totalAmount": 5999.00,
    "status": 2
  }
}
```

### 3.6 更新订单状态

```bash
# 更新订单状态
PUT http://localhost:8082/order/status?orderId=1&status=3

# 预期响应
{
  "code": 200,
  "message": "操作成功",
  "data": "订单状态更新成功"
}
```

## 4. 通过网关访问测试

### 4.1 带服务前缀访问

```bash
# 通过网关访问用户服务
GET http://localhost:8080/user-service/user/test

# 通过网关访问订单服务
GET http://localhost:8080/order-service/order/test

# 通过网关创建订单
POST http://localhost:8080/order-service/order/create
Content-Type: application/json

{
  "userId": 1,
  "productName": "MacBook Pro",
  "quantity": 1,
  "price": 15999.00
}
```

### 4.2 直接路径访问

```bash
# 直接通过网关访问（不带服务前缀）
GET http://localhost:8080/user/test
GET http://localhost:8080/order/test
```

## 5. 错误场景测试

### 5.1 测试服务降级

```bash
# 停止用户服务，然后测试订单服务调用
# 1. 停止用户服务
# 2. 访问订单服务的用户服务调用接口
GET http://localhost:8082/order/test-user-service

# 预期响应（降级）
{
  "code": 500,
  "message": "用户服务连接失败",
  "data": null
}
```

### 5.2 测试参数验证

```bash
# 用户注册 - 缺少必要参数
POST http://localhost:8081/user/register
Content-Type: application/json

{
  "username": "",
  "password": ""
}

# 预期响应
{
  "code": 500,
  "message": "用户名和密码不能为空",
  "data": null
}
```

### 5.3 测试业务逻辑验证

```bash
# 创建订单 - 用户不存在
POST http://localhost:8082/order/create
Content-Type: application/json

{
  "userId": 999,
  "productName": "Test Product",
  "quantity": 1,
  "price": 100.00
}

# 预期响应
{
  "code": 500,
  "message": "用户不存在或用户服务不可用",
  "data": null
}
```

## 6. Postman测试集合

可以将以上接口导入Postman创建测试集合：

1. 创建新的Collection
2. 添加环境变量：
   - `base_url`: http://localhost
   - `user_port`: 8081
   - `order_port`: 8082
   - `gateway_port`: 8080

3. 使用变量：`{{base_url}}:{{user_port}}/user/test`

## 7. 测试顺序建议

1. **基础连通性测试**: 确保所有服务正常启动
2. **用户服务测试**: 注册用户、登录验证
3. **订单服务测试**: 创建订单、查询订单
4. **服务间调用测试**: 验证微服务通信
5. **网关测试**: 通过网关访问各服务
6. **错误场景测试**: 验证异常处理和降级

## 8. 常见问题排查

### 8.1 连接被拒绝
- 检查服务是否启动
- 确认端口是否正确
- 查看服务日志

### 8.2 服务调用失败
- 检查Eureka注册状态
- 验证服务名称配置
- 查看Feign客户端配置

### 8.3 数据库相关错误
- 确认数据库连接
- 检查表结构是否正确
- 验证数据是否存在

### 8.4 MyBatis Plus与Spring Boot 3.2.x兼容性问题
- 错误信息：`Invalid value type for attribute 'factoryBeanObjectType': java.lang.String`
- 确认使用的是`mybatis-plus-spring-boot3-starter`而非`mybatis-plus-boot-starter`
- 确保MyBatis Plus版本为3.5.7或更高版本
- 检查所有模块的依赖配置是否一致

通过这些测试，你可以全面验证微服务架构的各个组件是否正常工作，并理解服务间的调用关系。
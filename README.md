# SpringBoot + SpringCloud 微服务学习项目

## 项目简介

这是一个完整的SpringBoot和SpringCloud微服务学习项目，专门为Java新手设计，包含详细的中文注释和完整的项目结构。通过这个项目，你可以学习到微服务架构的核心概念和实际应用。

## 技术栈

- **SpringBoot 3.2.5**: 简化Spring应用开发的框架
- **SpringCloud 2023.0.0**: 微服务架构解决方案
- **Eureka**: 服务注册与发现
- **Gateway**: API网关
- **OpenFeign**: 声明式HTTP客户端
- **MyBatis Plus 3.5.7**: 数据库操作框架
- **MySQL 8.0.33**: 关系型数据库
- **Maven**: 项目管理工具
- **Java 17**: 编程语言

## 项目结构

```
springcloud-study/
├── eureka-server/          # Eureka注册中心
├── gateway-service/        # API网关服务
├── user-service/          # 用户微服务
├── order-service/         # 订单微服务
├── common/               # 公共模块
├── sql/                  # 数据库脚本
├── pom.xml              # 父项目Maven配置
└── README.md            # 项目说明文档
```

## 核心概念学习

### 1. 微服务架构

微服务架构是一种将单一应用程序分解为多个小型、独立服务的架构模式。每个服务：
- 运行在自己的进程中
- 通过轻量级机制（通常是HTTP API）进行通信
- 围绕业务功能构建
- 可以独立部署

### 2. SpringBoot核心特性

- **自动配置**: 根据类路径中的依赖自动配置Spring应用
- **起步依赖**: 简化Maven依赖管理
- **内嵌服务器**: 内置Tomcat、Jetty等服务器
- **生产就绪**: 提供监控、健康检查等功能

### 3. SpringCloud组件

#### Eureka（服务注册与发现）
- **作用**: 管理微服务的注册和发现
- **原理**: 服务启动时向Eureka注册，其他服务可以从Eureka获取服务列表
- **优势**: 实现服务的动态发现，支持负载均衡

#### Gateway（API网关）
- **作用**: 作为系统的统一入口，路由请求到对应的微服务
- **功能**: 路由转发、负载均衡、鉴权、限流、监控
- **优势**: 统一管理外部请求，简化客户端调用

#### OpenFeign（服务调用）
- **作用**: 简化微服务之间的HTTP调用
- **特点**: 声明式、支持负载均衡、集成Hystrix熔断
- **优势**: 像调用本地方法一样调用远程服务

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- IDE（推荐IntelliJ IDEA）

### 2. 数据库初始化

```sql
-- 执行sql/init.sql文件
mysql -u root -p < sql/init.sql
```

### 3. 修改数据库配置

在`user-service`和`order-service`的`application.yml`中修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springcloud_study
    username: your_username  # 修改为你的数据库用户名
    password: your_password  # 修改为你的数据库密码
```

### 4. 启动服务

按以下顺序启动服务：

1. **启动Eureka注册中心**
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```
   访问: http://localhost:8761

2. **启动用户服务**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```
   测试: http://localhost:8081/user/test

3. **启动订单服务**
   ```bash
   cd order-service
   mvn spring-boot:run
   ```
   测试: http://localhost:8082/order/test

4. **启动网关服务**
   ```bash
   cd gateway-service
   mvn spring-boot:run
   ```
   通过网关访问: http://localhost:8080/user/test

## API接口测试

### 用户服务接口

1. **用户注册**
   ```bash
   POST http://localhost:8081/user/register
   Content-Type: application/json
   
   {
     "username": "newuser",
     "password": "123456",
     "email": "newuser@example.com",
     "phone": "13900139000"
   }
   ```

2. **用户登录**
   ```bash
   POST http://localhost:8081/user/login?username=admin&password=123456
   ```

3. **查询用户**
   ```bash
   GET http://localhost:8081/user/1
   ```

### 订单服务接口

1. **创建订单**
   ```bash
   POST http://localhost:8082/order/create
   Content-Type: application/json
   
   {
     "userId": 1,
     "productName": "iPhone 15",
     "quantity": 1,
     "price": 6999.00
   }
   ```

2. **查询订单**
   ```bash
   GET http://localhost:8082/order/1
   ```

3. **测试服务调用**
   ```bash
   GET http://localhost:8082/order/test-user-service
   ```

### 通过网关访问

所有接口都可以通过网关访问，只需将端口改为8080：

```bash
# 通过网关访问用户服务
GET http://localhost:8080/user/test

# 通过网关访问订单服务
GET http://localhost:8080/order/test
```

## 学习重点

### 1. 注解学习

- `@SpringBootApplication`: SpringBoot应用启动类
- `@EnableEurekaServer`: 启用Eureka服务器
- `@EnableEurekaClient`: 启用Eureka客户端
- `@EnableFeignClients`: 启用Feign客户端
- `@RestController`: REST控制器
- `@Service`: 服务层组件
- `@Mapper`: MyBatis映射器
- `@FeignClient`: Feign客户端接口

### 2. 配置文件学习

- `application.yml`: 应用配置文件
- 数据源配置
- Eureka配置
- Gateway路由配置
- MyBatis Plus配置

### 3. 微服务通信

- 服务注册与发现机制
- Feign声明式调用
- 负载均衡
- 服务降级和熔断

### 4. 数据库操作

- MyBatis Plus基础用法
- 实体类映射
- CRUD操作
- 自定义查询

## 常见问题

### 1. 服务启动失败

- 检查端口是否被占用
- 确认数据库连接配置正确
- 查看控制台错误日志
- 检查依赖版本兼容性问题

### 2. 服务调用失败

- 确认Eureka注册中心正常运行
- 检查服务是否成功注册到Eureka
- 验证Feign客户端配置

### 3. 数据库连接问题

- 确认MySQL服务正在运行
- 检查数据库用户名密码
- 验证数据库是否存在

### 4. MyBatis Plus与Spring Boot 3.2.5兼容性问题

- 错误信息：`Invalid value type for attribute 'factoryBeanObjectType': java.lang.String`
- 解决方案：使用`mybatis-plus-spring-boot3-starter`替代`mybatis-plus-boot-starter`
- 确保MyBatis Plus版本为3.5.7或更高版本

## 扩展学习

### 1. 添加更多微服务

可以参考现有服务的结构，创建更多的微服务，如：
- 商品服务（product-service）
- 支付服务（payment-service）
- 库存服务（inventory-service）

### 2. 集成更多组件

- **Config**: 配置中心
- **Sleuth**: 链路追踪
- **Hystrix**: 熔断器
- **Ribbon**: 负载均衡

### 3. 添加安全认证

- Spring Security
- JWT Token
- OAuth2

### 4. 监控和日志

- Actuator健康检查
- Micrometer指标收集
- ELK日志分析

## 面试重点

### 1. 微服务架构优缺点

**优点**:
- 服务独立部署和扩展
- 技术栈多样化
- 故障隔离
- 团队独立开发

**缺点**:
- 系统复杂性增加
- 网络通信开销
- 数据一致性挑战
- 运维复杂度提高

### 2. SpringCloud核心组件

- **Eureka**: 服务注册发现
- **Ribbon**: 客户端负载均衡
- **Feign**: 声明式服务调用
- **Hystrix**: 熔断器
- **Gateway**: API网关
- **Config**: 配置中心

### 3. 服务间通信方式

- **同步通信**: HTTP REST、RPC
- **异步通信**: 消息队列
- **服务发现**: Eureka、Consul、Zookeeper

### 4. 分布式事务

- **两阶段提交（2PC）**
- **补偿事务（TCC）**
- **事件驱动**
- **Saga模式**

## 总结

这个项目涵盖了SpringBoot和SpringCloud的核心概念和实际应用。通过学习和实践这个项目，你将掌握：

1. 微服务架构的基本概念
2. SpringBoot的核心特性和用法
3. SpringCloud各组件的作用和配置
4. 微服务间的通信机制
5. 实际项目的开发流程

建议按照以下步骤学习：
1. 理解项目整体架构
2. 逐个启动和测试各个服务
3. 阅读代码中的详细注释
4. 尝试修改和扩展功能
5. 总结核心概念和面试要点

祝你学习愉快，面试成功！
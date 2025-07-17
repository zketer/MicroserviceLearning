#!/bin/bash

# SpringCloud微服务启动脚本
# 用于快速启动所有微服务

echo "======================================"
echo "  SpringCloud微服务学习项目启动脚本"
echo "======================================"

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java环境，请先安装JDK 8+"
    exit 1
fi

# 检查Maven环境
if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到Maven环境，请先安装Maven 3.6+"
    exit 1
fi

echo "Java版本:"
java -version
echo ""
echo "Maven版本:"
mvn -version
echo ""

# 编译项目
echo "正在编译项目..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "错误: 项目编译失败"
    exit 1
fi
echo "项目编译成功！"
echo ""

# 启动服务函数
start_service() {
    local service_name=$1
    local service_port=$2
    local service_path=$3
    
    echo "正在启动 $service_name (端口: $service_port)..."
    
    # 检查服务目录是否存在
    if [ ! -d "$service_path" ]; then
        echo "错误: 服务目录 $service_path 不存在"
        return 1
    fi
    
    # 获取当前目录的绝对路径
    local current_dir=$(pwd)
    
    cd "$service_path"
    
    # 后台启动服务
    nohup mvn spring-boot:run > "$current_dir/logs/${service_name}.log" 2>&1 &
    local pid=$!
    echo $pid > "$current_dir/logs/${service_name}.pid"
    
    # 等待服务启动
    echo "等待 $service_name 启动..."
    for i in {1..30}; do
        if curl -s http://localhost:$service_port > /dev/null 2>&1; then
            echo "✓ $service_name 启动成功！"
            cd "$current_dir"
            break
        fi
        if [ $i -eq 30 ]; then
            echo "✗ $service_name 启动超时"
            cd "$current_dir"
            return 1
        fi
        sleep 2
    done
    
    echo ""
}

# 创建日志目录
mkdir -p logs

echo "开始启动微服务..."
echo ""

# 1. 启动Eureka注册中心
start_service "Eureka注册中心" 8761 "eureka-server"
if [ $? -ne 0 ]; then
    echo "Eureka注册中心启动失败，退出启动流程"
    exit 1
fi

# 等待Eureka完全启动
echo "等待Eureka注册中心完全启动..."
sleep 10

# 2. 启动用户服务
start_service "用户服务" 8081 "user-service"

# 3. 启动订单服务
start_service "订单服务" 8082 "order-service"

# 4. 启动网关服务
start_service "网关服务" 8080 "gateway-service"

echo "======================================"
echo "  所有服务启动完成！"
echo "======================================"
echo ""
echo "服务访问地址:"
echo "  Eureka注册中心: http://localhost:8761"
echo "  用户服务:       http://localhost:8081/user/test"
echo "  订单服务:       http://localhost:8082/order/test"
echo "  API网关:        http://localhost:8080/user/test"
echo ""
echo "通过网关访问服务:"
echo "  用户服务: http://localhost:8080/user-service/user/test"
echo "  订单服务: http://localhost:8080/order-service/order/test"
echo ""
echo "日志文件位置: ./logs/"
echo "停止服务请运行: ./stop-services.sh"
echo ""
echo "======================================"
echo "  启动完成，开始你的微服务学习之旅！"
echo "======================================"
#!/bin/bash

# SpringCloud微服务停止脚本
# 用于停止所有正在运行的微服务

echo "======================================"
echo "  SpringCloud微服务停止脚本"
echo "======================================"

# 停止服务函数
stop_service() {
    local service_name=$1
    local pid_file="logs/${service_name}.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat $pid_file)
        if ps -p $pid > /dev/null 2>&1; then
            echo "正在停止 $service_name (PID: $pid)..."
            kill $pid
            
            # 等待进程结束
            for i in {1..10}; do
                if ! ps -p $pid > /dev/null 2>&1; then
                    echo "✓ $service_name 已停止"
                    break
                fi
                if [ $i -eq 10 ]; then
                    echo "强制停止 $service_name..."
                    kill -9 $pid
                    echo "✓ $service_name 已强制停止"
                fi
                sleep 1
            done
        else
            echo "$service_name 进程不存在"
        fi
        rm -f $pid_file
    else
        echo "未找到 $service_name 的PID文件"
    fi
    echo ""
}

# 按相反顺序停止服务
echo "开始停止微服务..."
echo ""

# 停止网关服务
stop_service "网关服务"

# 停止订单服务
stop_service "订单服务"

# 停止用户服务
stop_service "用户服务"

# 停止Eureka注册中心
stop_service "Eureka注册中心"

# 清理可能残留的Java进程
echo "检查是否有残留的Spring Boot进程..."
spring_processes=$(ps aux | grep "spring-boot:run" | grep -v grep | awk '{print $2}')
if [ ! -z "$spring_processes" ]; then
    echo "发现残留进程，正在清理..."
    echo $spring_processes | xargs kill -9
    echo "✓ 残留进程已清理"
else
    echo "✓ 没有发现残留进程"
fi
echo ""

echo "======================================"
echo "  所有服务已停止！"
echo "======================================"
echo ""
echo "如需重新启动服务，请运行: ./start-services.sh"
echo ""
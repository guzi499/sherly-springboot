#!/bin/bash
# 这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=sherly-upr-admin-1.1-RELEASE.jar
# 如果是config需要使用spring.cloud.config.server.native 则使用ENV=test,native
JAVA_OPTS='-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5002'
ENV=test

# 使用说明，用来提示输入参数
usage(){
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    exit 1
}
# 检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

# 检查端口是否被占用
is_bind(){
  port=`netstat -tln | grep 8888 `
  # 如果被占用1，否则返回0
  if [ -z "${port}" ]; then
   return 0
  else
    echo "${port}"
    return 1
  fi
}

# 启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    # nohup java -jar $APP_NAME --spring.profiles.active=$ENV >/dev/null 2>&1 &
    # nohup java $JAVA_OPTS -jar $APP_NAME --spring.profiles.active=$ENV >> catalina.out 2>&1 &
    nohup java -jar $APP_NAME > log.file 2>&1 &
	echo "${APP_NAME} is running"
  fi
}

# 停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

# 输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

# 重启
restart(){
  stop
  is_bind
  while [ $? -eq "1" ];
  do
    sleep 0.1
    is_bind
  done
  start
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  "")
    restart
    ;;
  *)
    usage
    ;;
esac

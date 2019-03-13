#!/bin/bash

action=$1
project_name=songkai-gradle
profile=test
dir_path=/JavaWeb/pms2.wltest.com/comSongkai

jar_name="songkai-gradle-0.1.0.jar"
pid=$dir_path"/shared/tmp/pids/*.pid"

start(){
    if [ -d $dir_path/$project_name ]
    then
        cd $dir_path/$project_name
        if [ -f $pid ]
        then
            echo ">>>>>>启动失败，项目已经启动"
        else
            echo ">>>>>>启动中..."
            JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
            JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xss512k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
            chmod +x $jar_name
            nohup java $JAVA_OPTS $JAVA_MEM_OPTS  -jar -Dspring.profiles.active=$profile $jar_name >/dev/null 2>&1 & sleep 5
            echo ">>>>>>启动成功，pid：`cat $pid`"
        fi
    else
        echo "$dir_path/$project_name 目录不存在"
    fi
}

stop(){
    if [ -d $dir_path/$project_name ]
    then
        cd $dir_path/$project_name
        if [ -f $pid ]
        then
            echo ">>>>>>关闭中..."
            if [ $(ps --no-heading $(cat $pid) | wc -l) -gt 0 ]
            then
                kill `cat $pid`
            else
                echo ">>>>>>进程不存在，程序未运行"
            fi
            if [ -f $pid ]
            then
                kill -9 `cat $pid`
            fi
            rm $pid
            echo ">>>>>>进程已停止"
        else
            echo ">>>>>>关闭失败，未发现pid文件"
        fi
    else
        echo "$dir_path/$project_name 目录不存在"
    fi
}

deploy(){
    if [ -d $dir_path/$project_name ]
    then
        stop
        mv $dir_path/$project_name'_temp.jar' $dir_path/$project_name/$jar_name
        start
    else
        cd $dir_path
        mkdir $project_name
        stop
        mv $dir_path/$project_name'_temp.jar' $dir_path/$project_name/$jar_name
        start
    fi
}

restart(){
    stop && start
}

backup(){
    if [ -d $dir_path/$project_name ]
    then
        cd $dir_path/$project_name
        if [ -d backup ]
        then
            if [ -f $jar_name ]
            then
                mv $dir_path/$project_name/$jar_name $dir_path/$project_name/backup/ie-$project_name'_'$(date "+%Y-%m-%d_%H%M%S")'.jar'
            fi
        else
            mkdir backup
            if [ -f $jar_name ]
            then
                mv $dir_path/$project_name/$jar_name $dir_path/$project_name/backup/ie-$project_name'_'$(date "+%Y-%m-%d_%H%M%S")'.jar'
            fi
        fi
        echo ">>>>>>备份完成"
        ##清理旧文件
        cd backup
        backup_file_count=$(ls -l | grep "?*.jar" | wc -l)
        rm_count=$[ backup_file_count-10 ]
        if [ $backup_file_count -gt 10 ]
        then
            echo ">>>>>>备份文件存在$backup_file_count 个,多于10个，清理旧文件"
            rm -r $(ls -rt | head -n$rm_count)
        fi
    else
        echo "$dir_path/$project_name 目录不存在"
    fi
}


case $action in
    deploy)
        deploy
    ;;
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        restart
    ;;
    *)
        echo "无效操作！"
    ;;
esac
exit 0
#!/bin/bash
DEBUG=false 

host="18.8.0.245"
port="4141"
startCmd="/etc/init.d/logNode start"
stopCmd="/etc/init.d/logNode stop"
restartCmd="/etc/init.d/logNode restart"
cnt=0
progName="logNode"
sendTime=600
confDir="."
archDir="./arch"
delDate=30
lastCount=0
logFile="logNode.log"
 
 _printUsage(){
	echo "作用：守护日志采集进程"
	echo "参数：-h打印帮助信息"
	echo "		-H指定日志收集器ip"
	echo "		-P指定日志收集器端口"
	echo "		-p指定进程名称，默认值为logNode"
	echo "		-s指定发送给collector的时间间隔，单位为S，只可为正整数，默认值为600s"
	echo "		-d指定日志归档目录，默认值为当前目录"
	echo "		-t指定删除多少天以前的归档日志，只可为正整数，默认值为30天"
	echo "eg:./guard -p logNode -d /tmp -t 30"
}
 
 # 启动
_execStart(){
	if [ "$DEBUG" = "true" ]
	then
		echo -e ${startCmd}
	fi
	exec ${startCmd}
}

# 停止 
_execStop(){
	if [ "$DEBUG" = "true" ]
	then
		echo -e ${stopCmd}
	fi
	exec ${stopCmd}
}

# 重启
_execRestart(){
	if [ "$DEBUG" = "true" ]
	then
		echo -e ${restartCmd}
	fi
	exec ${restartCmd}
}

while getopts "hs:p:d:t:" optName
do
	case $optName in
	"h")
		_printUsage
		exit 0
		;;
	"?")
		_printUsage
		exit -1
		;;
	"H")
		host=$OPTARG
		;;
	"P")
		port=$OPTARG
		;;
	"s")
		sendTime=$OPTARG
		;;
	"p")
		progName=$OPTARG
		;;
	"d")
		archDir=$OPTARG
		;;
	"t")
		delDate=$OPTARG
		;;
	esac
done

#检查是否为正整数
echo $sendTime | grep -Eq "^[0-9]*[1-9][0-9]*$"
if [ $? -ne 0 ]
then
	_printUsage
	exit -1
fi

#检查是否为正整数
echo $delDate | grep -Eq "^[0-9]*[1-9][0-9]*$"
if [ $? -ne 0 ]
then
	_printUsage
	exit -1
fi

#检查目录是否存在
if [ ! -d ${archDir} ]
then
  echo dir \[${archDir}\] is not exist
  exit -1
fi

#检查目录是否具有读写执行权限
if [ ! -x ${archDir} -o ! -r ${archDir} -o ! -w ${archDir} ]
then
  echo ${archDir} permission deny 
  exit -1
fi

#检查是否有记录最后一次采集到的日志行数
if [ -f ${confDir}/lastCount ]
then
	lastCount=`cat ${lastCount}`
fi

# 清除归档目录下指定日期之前的文件
_delArchFile(){
	echo "delete archive log files ${delDate} days ago in dir ${archDir}"
	find ${archDir} -mtime +${delDate} -exec rm -rf {} \;
}

# 发送日志到收集器
_sendLog(){
	#fcnt=`expr ${lastCount} + 1`
	#tcnt=`cat ${logFile} | wc -l`
	#sed -n ${fcnt},${tcnt}p ${logFile} >> ${confDir}/tmp.log
	mv ${confDir}/${logFile} ${confDir}/tmp.log
	cat ${confDir}/tmp.log >> ${confDir}/needSend.log
	flume-ng avro-client -H ${host} -p ${port} -F ${confDir}/needSend.log
	if [ $? -eq 0 ]
	then
		# 若发送成功，则移动日志文件到归档目录
		dstr=`date '+%Y%m%d%H%M%S'`
		mv ${confDir}/tmp.log ${archDir}/iuni${dstr}.log
	fi
}

while true
do
	sleep 1
	if [ $(ps -C logNode --no-header | wc -l) -eq 0 ]
	then
        _execStart
        sleep 10
        if [ $(ps -C logNode --no-header | wc -l) -eq 0 ]
		then
			_execRestart
        fi
	fi
	cnt=`expr ${cnt} + 1`
	tn=`expr ${cnt} % ${sendTime}`
	if [ tn -eq 0 ]
	then
		_sendLog
		_delArchFile
	fi
done

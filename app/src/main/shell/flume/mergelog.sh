#!/bin/bash
# merge all small file to a big file everyday.

function merge(){
	date=$1
	# merge useless log
	echo merge /flume/logs/use=useless/time=${date}
	hadoop fs -getmerge /flume/logs/use=useless/time=${date} /tmp/iuni.${date}.useless.log
	if [ $? -ne 0 ]
	then
		echo "error -- ${date}"
		exit;
	fi
	hadoop fs -rm -skipTrash /flume/logs/use=useless/time=${date}/*
	if [ $? -ne 0 ]
	then
		echo "error -- ${date}"
		exit;
	fi
	hadoop fs -put /tmp/iuni.${date}.useless.log /flume/logs/use=useless/time=${date}
	if [ $? -ne 0 ]
	then
		echo "error -- ${date}"
		exit;
	fi
	# merge usefull log
	echo merge /flume/logs/use=usefull/time=${date}
	hadoop fs -getmerge /flume/logs/use=usefull/time=${date} /tmp/iuni.${date}.usefull.log
	if [ $? -ne 0 ]
	then
		echo "error -- ${date}"
		exit;
	fi
	hadoop fs -rm -skipTrash /flume/logs/use=usefull/time=${date}/*
	if [ $? -ne 0 ]
	then
		echo "error -- ${date}"
		exit;
	fi
	hadoop fs -put /tmp/iuni.${date}.usefull.log /flume/logs/use=usefull/time=${date}
	if [ $? -ne 0 ]
	then
		echo "error -- ${date}"
		exit;
	fi
}

if [ $# -gt 0 ]
then
	for date in $@
	do
		echo "merge ${date}"
		merge ${date}
	done
else
	date=`date -d "-1 day" "+%Y%m%d"`
	echo "merge ${date}"
	merge ${date}
fi

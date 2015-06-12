#!/bin/sh
cdir=`pwd`
sed s{confDir=\".\"{confDir=\"$cdir\"{g logNode > /etc/init.d/logNode
sed s{confDir=\".\"{confDir=\"$cdir\"{g guard.sh > guard.sh
sed s{archDir=\"./arch\"{archDir=\"$cdir/arch\"{g guard.sh > guard.sh
sed s{/tmp/{$cdir{g logNode.conf > logNode.conf

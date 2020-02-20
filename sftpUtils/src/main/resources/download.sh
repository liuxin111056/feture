#!/bin/bash
set -e
#本程序目录位置
sftputil_home=/home/loan/dhs/ods/sftputil
#sftp目标服务器连接信息
sftp_ip=10.130.1.14
sftp_port=22
sftp_timeout=60000
sftp_usr=loan_sftp
sftp_passwd=loan123

echo "需参数信息(下载文件所在远程目录/send/xxxx/, 文件下载本地目录:/location/, 下载后路径后缀：2019-10-22(无则填N)， 可选-默认目录下单层目录中所有文件（远程文件夹下某个或多个文件名：TM_XXX_INFO.txt.OK TM_XXX_INFO.txt）)"
echo "参数信息：$*"

echo $*
echo "java -cp \"${sftputil_home}/libs/sftputil-1.1.jar:${sftputil_home}/libs/jsch-0.1.55.jar\" com.lihfinance.sftputil.process.OdsDownloadFiles ${sftp_ip} ${sftp_port} ${sftp_timeout} ${sftp_usr} ${sftp_passwd} $* "
java -cp "${sftputil_home}/libs/sftputil-1.1.jar:${sftputil_home}/libs/jsch-0.1.55.jar" com.lihfinance.sftputil.process.OdsDownloadFiles ${sftp_ip} ${sftp_port} ${sftp_timeout} ${sftp_usr} ${sftp_passwd} $*
#!/bin/bash
set -e
#本程序目录位置
sftputil_home=/home/loan/whrcb/sftputil
#sftp目标服务器连接信息
sftp_ip=10.130.1.14
sftp_port=22
sftp_timeout=60000
sftp_usr=loan_sftp_402526030003
sftp_passwd=loan403
#上传基准目录，后边会跟日期
base_dir=/send/

echo $*
echo "java -cp \"${sftputil_home}/libs/sftputil-1.1.jar:${sftputil_home}/libs/jsch-0.1.55.jar\" com.lihfinance.sftputil.process.OdsUploadFiles ${sftp_ip} ${sftp_port} ${sftp_timeout} ${sftp_usr} ${sftp_passwd} ${base_dir} $* "
java -cp "${sftputil_home}/libs/sftputil-1.1.jar:${sftputil_home}/libs/jsch-0.1.55.jar" com.lihfinance.sftputil.process.OdsUploadFiles ${sftp_ip} ${sftp_port} ${sftp_timeout} ${sftp_usr} ${sftp_passwd} ${base_dir} $*
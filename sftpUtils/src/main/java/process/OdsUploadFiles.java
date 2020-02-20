package process;

import com.jcraft.jsch.SftpException;
import utils.Sftp;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.TreeSet;

public class OdsUploadFiles
{
    public static void main(String[] args) throws SftpException {
        if (args.length < 8) {
            System.err.println("参数信息数量不够，至少应包括 host,port,timeout,username,passwd,baseDir,loacalDir,fileDate");
            return;
        }

        String host = args[0];

        String username = args[3];

        String password = args[4];

        String baseDir = args[5];

        String localDir = args[6];
        String fileDate = args[7];
        TreeSet waitingUpload = new TreeSet();
        int port;
        if (args[1].matches("[1-9]\\d{1,4}"))
            port = Integer.parseInt(args[1]);
        else
            port = 22;
        int timeout;
        if (args[2].matches("[1-9]\\d{4,5}"))
            timeout = Integer.parseInt(args[2]);
        else {
            timeout = 60000;
        }

        System.out.println("-----------参数信息-----------");
        System.out.println("host:" + host);
        System.out.println("port:" + port);
        System.out.println("timeout:" + timeout);
        System.out.println("usrName:" + username);
        System.out.println("password:" + password);
        System.out.println("localDir:" + localDir);
        System.out.println("baseDir:" + baseDir);
        System.out.println("fileDate:" + fileDate);
        if (args.length > 8) {
            System.out.println("待上传文件：");
            for (int i = 8; i < args.length; i++) {
                System.out.println("-" + localDir + args[i]);
                waitingUpload.add(localDir + args[i]);
            }
        } else {
            System.out.println("未指定上传文件，上传文件夹下所有文件");
        }

        System.out.println("-----------------------------");

        System.out.println("检查本地文件内容是否存在");
        File localFile = new File(localDir);
        boolean localexists = (localFile.exists()) && (localFile.isDirectory());
        if (!localexists) {
            System.out.println("本地文件夹不存在或此处非文件夹类型：" + localFile.getName());
            return;
        }

        System.out.println("检查文件夹下文件信息");
        File temp;
        if (waitingUpload.isEmpty()) {
            File[] files = localFile.listFiles();
            for (File file : files)
                waitingUpload.add(file.getAbsolutePath());
        }
        else {
            temp = null;
            for (Object fileName  = waitingUpload.iterator(); ((Iterator)fileName).hasNext(); ) {
                fileName = ((Iterator)fileName).next();
                temp = new File((String)fileName);
                if ((!temp.exists()) || (!temp.isFile())) {
                    System.err.println((String)fileName + "文件不存在或非文件类型");
                    return;
                }
            }

        }

        Sftp sftp = new Sftp(host, port, timeout, username, password);
        sftp.login();
        sftp.changeDir(baseDir);

        boolean existDir = sftp.existDir(fileDate);
        System.out.println(existDir ? "文件夹已存在，将替换文件夹下内容" : "文件夹不存在，创建文件夹上传文件内容");
        if (!existDir) {
            sftp.makeDir(fileDate);
            System.out.println(fileDate + "文件夹已创建");
        }

        System.out.println("开始上传文件");
        for (Object fileName = waitingUpload.iterator(); ((Iterator)fileName).hasNext(); ) {
             fileName = ((Iterator)fileName).next();
            System.out.println(fileName);
            System.out.println(((String) fileName).substring(((String)fileName).lastIndexOf(File.separator) + 1));
            boolean uploadFile = sftp.uploadFile(baseDir + fileDate, ((String) fileName).substring(((String)fileName).lastIndexOf(File.separator) + 1), (InputStream) fileName);
            if (uploadFile)
                System.out.println("上传成功");
            else {
                System.err.println("上传失败");
            }
        }
        System.out.println("上传任务结束");

        sftp.logout();
    }
}
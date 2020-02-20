package process;

import utils.Sftp;
import java.util.TreeSet;

public class CheckFileExist
{
    public static void main(String[] args)
    {
        if (args.length < 8) {
            System.err.println("参数信息数量不够，至少应包括 host,port,timeout,username,passwd,baseDir,loacalDir,fileDate");
            return;
        }

        String host = args[0];

        String username = args[3];

        String password = args[4];

        String baseDir = args[5];

        String localDir = args[6];
        String fileDate = null;

        if ((args.length > 7) && (!"N".equals(args[7]))) {
            fileDate = args[7];
        }
        TreeSet waitingDownload = new TreeSet();
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

        System.out.println("-----------------------------");
        if (args.length > 8) {
            System.out.println("待下载文件：");
            for (int i = 8; i < args.length; i++) {
                System.out.println("-" + args[i]);
                waitingDownload.add(args[i]);
            }
        }
        boolean checkFlag = false;
        while (!checkFlag) {
            checkFlag = check(host, port, timeout, username, password, baseDir, waitingDownload);
            if (!checkFlag) {
                System.out.println("文件未送达，三分钟后重新检查");
                try {
                    Thread.sleep(180000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        }
        System.out.println("待下载任务检查结束，文件都已存在");
    }

    public static boolean check(String host, int port, int timeout, String username, String password, String baseDir, TreeSet<String> waitingDownload) {
        Sftp sftp = null;
        try {
            System.out.println("检查远程文件夹下文件信息");
            sftp = new Sftp(host, port, timeout, username, password);
            sftp.login();
            sftp.changeDir(baseDir);

            if (waitingDownload == null) {
                System.out.println("未指定下载文件，下载文件夹下所有文件");
                String[] files = sftp.lsFiles();
                if ((files == null) || (files.length == 0)) {
                    System.err.println("文件夹下没有内容");
                    return false;
                }
                for (String file : files) {
                    System.out.println("-" + file);
                    waitingDownload.add(file);
                }
            }

            System.out.println("开始检查下载文件");
            for (String fileName : waitingDownload) {
                System.out.println("->" + fileName);
                boolean existFile = sftp.existFile(fileName);
                if (existFile) {
                    System.out.println(fileName + "文件存在");
                } else {
                    System.err.println(fileName + "文件不存在");
                    return false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } finally {
            if (sftp != null) {
                sftp.logout();
            }
        }
        return true;
    }
}
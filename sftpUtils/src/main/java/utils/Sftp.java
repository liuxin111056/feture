package utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class Sftp
{
    private Session session = null;

    private ChannelSftp channel = null;
    private String host;
    private int port;
    private int timeout;
    private String username;
    private String password;

    public Sftp(String host, int port, int timeout, String username, String password)
    {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.username = username;
        this.password = password;
    }

    public boolean login()
    {
        try
        {
            JSch jsch = new JSch();
            this.session = jsch.getSession(this.username, this.host, this.port);
            if (this.password != null) {
                this.session.setPassword(this.password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            this.session.setConfig(config);
            this.session.setTimeout(this.timeout);
            this.session.connect();
            System.out.println("sftp session connected");

            System.out.println("opening channel");
            this.channel = ((ChannelSftp)this.session.openChannel("sftp"));
            this.channel.connect();

            System.out.println("connected successfully");
            return true;
        } catch (JSchException e) {
            System.err.println("sftp login failed" + e);
        }return false;
    }

    public boolean uploadFile(String pathName, String fileName, InputStream input) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return false;
        }
        try
        {
            this.channel.put(input, fileName, 0);
            if (!existFile(fileName)) {
                System.out.println("upload failed");
                return false;
            }
            System.out.println("upload successful");
            return true;
        } catch (SftpException e) {
            System.err.println("upload failed" + e);
            return false;
        } finally {
            changeDir(currentDir);
        }
    }

    public boolean uploadFile(String pathName, String fileName, String localFile) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return false;
        }
        try
        {
            this.channel.put(localFile, fileName, 0);
            boolean bool1;
            if (!existFile(fileName)) {
                System.out.println("upload failed");
                return false;
            }
            System.out.println("upload successful");
            return true;
        } catch (SftpException e) {
            System.err.println("upload failed" + e);
            return false;
        } finally {
            changeDir(currentDir);
        }
    }

    public boolean downloadFile(String remotePath, String fileName, String localPath) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(remotePath)) {
            return false;
        }
        try
        {
            String localFilePath = localPath + File.separator + fileName;
            this.channel.get(fileName, localFilePath);

            File localFile = new File(localFilePath);
            boolean bool;
            if (!localFile.exists()) {
                System.out.println("download file failed");
                return false;
            }
            System.out.println("download successful");
            return true;
        }
        catch (SftpException e)
        {
            System.err.println("download file failed" + e);
            return false;
        } finally {
            changeDir(currentDir);
        }
    }

    public boolean changeDir(String pathName)
    {
        if ((pathName == null) || (pathName.trim().equals(""))) {
            System.out.println("invalid pathName");
            return false;
        }
        try
        {
            this.channel.cd(pathName.replaceAll("\\\\", "/"));
            System.out.println("directory successfully changed,current dir=" + this.channel.pwd());
            return true;
        } catch (SftpException e) {
            System.err.println("failed to change directory" + e);
        }return false;
    }

    public boolean changeToParentDir()
    {
        return changeDir("..");
    }

    public boolean changeToHomeDir()
    {
        String homeDir = null;
        homeDir = this.channel.getHome();
        return changeDir(homeDir);
    }

    public boolean makeDir(String dirName)
    {
        try
        {
            this.channel.mkdir(dirName);
            System.out.println("directory successfully created,dir=" + dirName);
            return true;
        } catch (SftpException e) {
            System.err.println("failed to create directory" + e);
        }return false;
    }

    public boolean delDir(String dirName)
    {
        if (!changeDir(dirName)) {
            return false;
        }

        Vector list = null;
        try {
            list = this.channel.ls(this.channel.pwd());
        } catch (SftpException e) {
            System.err.println("can not list directory" + e);
            return false;
        }

        for (Object object : list) {
            ChannelSftp.LsEntry entry=(ChannelSftp.LsEntry)object;
            String fileName = entry.getFilename();
            if ((!fileName.equals(".")) && (!fileName.equals(".."))) {
                if (entry.getAttrs().isDir())
                    delDir(fileName);
                else {
                    delFile(fileName);
                }
            }
        }

        if (!changeToParentDir()) {
            return false;
        }
        try
        {
            this.channel.rmdir(dirName);
            System.out.println("directory " + dirName + " successfully deleted");
            return true;
        } catch (SftpException e) {
            System.err.println("failed to delete directory " + dirName + e);
        }return false;
    }

    public boolean delFile(String fileName)
    {
        if ((fileName == null) || (fileName.trim().equals(""))) {
            System.out.println("invalid filename");
            return false;
        }
        try
        {
            this.channel.rm(fileName);
            System.out.println("file " + fileName + " successfully deleted");
            return true;
        } catch (SftpException e) {
            System.err.println("failed to delete file " + fileName + e);
        }return false;
    }

    public String[] ls()
    {
        return list(Filter.ALL);
    }

    public String[] ls(String pathName) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return new String[0];
        }

        String[] result = list(Filter.ALL);
        if (!changeDir(currentDir)) {
            return new String[0];
        }
        return result;
    }

    public String[] lsFiles()
    {
        return list(Filter.FILE);
    }

    public String[] lsFiles(String pathName) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return new String[0];
        }

        String[] result = list(Filter.FILE);
        if (!changeDir(currentDir)) {
            return new String[0];
        }
        return result;
    }

    public String[] lsDirs()
    {
        return list(Filter.DIR);
    }

    public String[] lsDirs(String pathName) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return new String[0];
        }

        String[] result = list(Filter.DIR);
        if (!changeDir(currentDir)) {
            return new String[0];
        }
        return result;
    }

    public boolean exist(String name)
    {
        return exist(ls(), name);
    }

    public boolean exist(String path, String name) throws SftpException {
        return exist(ls(path), name);
    }

    public boolean existFile(String name)
    {
        return exist(lsFiles(), name);
    }

    public boolean existFile(String path, String name) throws SftpException {
        return exist(lsFiles(path), name);
    }

    public boolean existDir(String name)
    {
        return exist(lsDirs(), name);
    }

    public boolean existDir(String path, String name) throws SftpException {
        return exist(lsDirs(path), name);
    }

    public String currentDir() throws SftpException {
        return this.channel.pwd();
    }

    public void logout()
    {
        if (this.channel != null) {
            this.channel.quit();
            this.channel.disconnect();
        }
        if (this.session != null) {
            this.session.disconnect();
        }
        System.out.println("logout successfully");
    }

    private String[] list(Filter filter)
    {
        Vector list = null;
        try
        {
            list = this.channel.ls(this.channel.pwd());
        } catch (SftpException e) {
            System.err.println("can not list directory" + e);
            return new String[0];
        }

        List resultList = new ArrayList();
        for (Object object : list) {
            ChannelSftp.LsEntry entry=(ChannelSftp.LsEntry)object;
            if (filter(entry, filter)) {
                resultList.add(entry.getFilename());
            }
        }
        return (String[])resultList.toArray(new String[0]);
    }

    private boolean filter(ChannelSftp.LsEntry entry, Filter f)
    {
        if (f.equals(Filter.ALL))
            return (!entry.getFilename().equals(".")) && (!entry.getFilename().equals(".."));
        if (f.equals(Filter.FILE))
            return (!entry.getFilename().equals(".")) && (!entry.getFilename().equals("..")) && (!entry.getAttrs().isDir());
        if (f.equals(Filter.DIR)) {
            return (!entry.getFilename().equals(".")) && (!entry.getFilename().equals("..")) && (entry.getAttrs().isDir());
        }
        return false;
    }

    private String homeDir() throws SftpException {
        return this.channel.getHome();
    }

    private boolean exist(String[] strArr, String str)
    {
        if ((strArr == null) || (strArr.length == 0)) {
            return false;
        }
        if ((str == null) || (str.trim().equals(""))) {
            return false;
        }
        for (String s : strArr) {
            if (s.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    private static enum Filter
    {
        ALL,

        FILE,

        DIR;
    }
}
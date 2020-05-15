import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;

/**
 * 基础的服务Daemon.
 * 可以带一个参数，指定在classpath中加载的spring配置文件名，程序会自动加上 "-context.xml"后缀
 * @author licj
 *
 */
public class ServiceDaemon implements Daemon {

    private ConfigurableApplicationContext ctx;

    private static volatile boolean running = true;

    @Override
    public void init(DaemonContext context) throws DaemonInitException,	Exception
    {

        ctx = new ClassPathXmlApplicationContext(getContextFilename(context.getArguments()));

        ctx.registerShutdownHook();
    }


    @Override
    public void start() throws Exception
    {
    }

    @Override
    public void stop() throws Exception
    {
    }

    @Override
    public void destroy()
    {
        ctx.close();
    }

    /**
     * 这样可以直接跑，要关闭就杀掉进程
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        @SuppressWarnings("resource")
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(getContextFilename(args));
        ctx.registerShutdownHook();
        synchronized (ServiceDaemon.class) {
            while (running)
                try {
                    ServiceDaemon.class.wait();
                } catch (Throwable localThrowable) {
                }
        }
    }

    public static String getContextFilename(String args[])
    {
        String filename = "/service-context.xml";
        if (args.length >= 1 )
            filename = "/" + args[0] + "-context.xml";
        return filename;
    }
}

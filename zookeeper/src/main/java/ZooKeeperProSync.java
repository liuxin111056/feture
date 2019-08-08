import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * liuxin
 * 2019/8/8 0008
 */
public class ZooKeeperProSync {
    private static final String connectionString = "127.0.0.1:2181";
    public static final Integer sessionTimeout = 2000;
    public static ZooKeeper zkClient = null;

    @Before
    public void init() throws Exception {
        //三个参数分别为连接的zookeeper集群服务器的ip，超时时间，监听器
        zkClient = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            //收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
            public void process(WatchedEvent event) {
                System.out.println(event.getType() + "-------" + event.getPath());
            }
        });
    }
    //创建数据节点到zk中
    @Test
    public void createNode() throws Exception{
        /*
         * 传入四个参数
         * 1、创建的节点
         * 2、节点数据
         * 3、节点的权限，OPEN_ACL_UNSAFE表示内部应用权限
         * 4、节点类型，4种：持久化节点，带序列持久化节点，临时节点，带序列的临时节点
         */
        String path = zkClient.create("/dubbo",
                "helloworldd".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(path);
    }
    //获取子节点
    @Test
    public void getChildren()  throws Exception{
        /*
         * 传入2个参数
         * 1、指定获取哪个节点的孩子
         * 2、是否使用监听器(watcher)，true表示使用以上的监听功能
         */
        List<String> children = zkClient.getChildren("/",true);
        for (String child : children) {
            System.out.println(child);
        }
        System.in.read();
    }
    @Test
    public void update() throws Exception{
        //原 /idea节点的数据为helloworld
        zkClient.setData("/idea", "zookeeper-h".getBytes(), -1);
        //查看修改数据是否成功
        byte[] data = zkClient.getData("/idea", false, null);
        System.out.println(new String(data));
    }
    @Test
    public void delete()  throws Exception{
        //第一个参数为要删除的节点，第二个参数表示版本，-1表示所有版本
        zkClient.delete("/idea",-1);
    }
    @Test
    public void getData()  throws Exception{
        byte[] data = zkClient.getData("/idea", false, null);
        System.out.println(new String(data));
    }
    @Test
    public void testExist()  throws Exception{
        //一个参数是节点，一个是是否用监听功能,Stat封装了该节点的相关信息比如：czxid，mzxid，ctime，mtime等
        Stat stat = zkClient.exists("/idea", false);
        System.out.println(stat==null?"不存在":"存在");
    }
}

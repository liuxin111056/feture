package t1;
//新建一个项目经理类：
public class ProjectManager implements Notice{

    private String name;

    public ProjectManager(String name)
    {
        this.name =name;
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * 通知方法
     * @param msg
     */
    @Override
    public void noticeMe(String msg)
    {
        System.err.println(msg);
    }

    /**
     * 安排任务
     * @param task
     */
    public void arrange(String task)
    {
        //安排程序员干活
        new Programmer().receiveTask(task, this);
    }

    public void doOtherWork()
    {
        System.err.println("项目经理干其他事情...");
    }

}

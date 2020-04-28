package t1;

public class CallbackTest {
    public static void main(String[] args) {
        ProjectManager prjMgr = new ProjectManager("王响");
        prjMgr.arrange("今晚完成数据库设计...");
        prjMgr.doOtherWork();
    }
}

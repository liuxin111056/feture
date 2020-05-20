package t2;

public  abstract class AbstractScheduleService implements ScheduleService {

    public void execute() throws Exception {
        System.out.println("doJob"+"执行前");
        this.doJob();
        System.out.println("doJob"+"执行后");
    }
    protected abstract void doJob() throws Exception;
}

package t2;

public abstract class ZaYbResendTask extends AbstractScheduleService {
    @Override
    protected void doJob() throws Exception {
        System.out.println("reSend"+"执行前");
        reSend();
        System.out.println("reSend"+"执行后");
    }

    protected abstract void reSend() ;
}

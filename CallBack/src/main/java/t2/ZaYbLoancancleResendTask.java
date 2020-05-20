package t2;

public class ZaYbLoancancleResendTask extends ZaYbResendTask {
    @Override
    protected void reSend() {
        System.out.println("gogogogogo");
    }

    public static void main(String[] args) throws Exception {
        ZaYbLoancancleResendTask t=new ZaYbLoancancleResendTask();
        t.execute();
    }
}

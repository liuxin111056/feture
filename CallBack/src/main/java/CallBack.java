public class CallBack {
    public static void meNeed() {
        //开启线程--->点外买
        new Thread(new Runnable() {

            @Override
            public void run() {
                //带什么外卖
                String food = "你好,请帮点一份蛋炒饭盖饭,要辣,不要麻,要放鸡精,不放味精,盐要低钠盐,还要多放点葱花";
                //下单
                takeouts(food);

            }
        }).start();


        //玩游戏去
        playGames();
    }

    /**
     * @throws @Description:点完外卖,我就去玩游戏去了
     */
    public static void playGames() {
        System.err.println("我玩游戏去了");
    }

    /**
     * @throws @Description:外卖送到门外,敲门提示我外卖到了,叫我去拿外卖----这个方法就是所谓的--->回调函数
     */
    public static void callback(String message) {
        //这里就是需要等待之后才能进行的后续业务逻辑
        System.err.println(message);
    }

    /**
     * @throws @Description:
     * @param
     */
    public static void takeouts(String food) {
        // 模拟带外卖需要的时间
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 10000; k++) {
                    for (int l = 0; l < 100; l++) {

                    }
                }
            }
        }
        // 外卖送到了门口,敲门
        String message = "你的外卖到了";
        callback(message);
    }

    //测试回调
    public static void main(String[] args) {
        meNeed();
    }
}










package 精编p272;

public class Example implements Runnable {

    private Thread carrier, manager;

    public void setThread(Thread carrier, Thread manager) {
        this.carrier = carrier;
        this.manager = manager;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if (name.equals("运货司机")) {
            try {
                System.out.println("我是" + name + "，等待装运工...");
                carrier.join();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(name + "运走货物");
            } catch (InterruptedException e) {
            }
        } else if (name.equals("装运工")) {
            try {
                System.out.println("我是" + name + "，等待仓库管理员...");
                manager.join();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(name + "装好货物");
            } catch (InterruptedException e) {
            }
        } else if (name.equals("仓库管理员")) {
            System.out.println("我是" + name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(name + "打开仓库");
        }
    }
}

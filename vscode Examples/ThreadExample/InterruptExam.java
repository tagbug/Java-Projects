package ThreadExample;

public class InterruptExam {
    public static void main(String[] args) {
        Road road = new Road();
        Thread driver, police;
        driver = new Thread(road);
        police = new Thread(road);
        driver.setName("司机");
        police.setName("警察");
        road.setAttachThread(driver);
        driver.start();
        police.start();
    }
}

class Road implements Runnable {

    Thread attachThread;

    public void setAttachThread(Thread t) {
        attachThread = t;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if (name.equals("司机")) {
            System.out.println("我是司机，在马路上开车");
            System.out.println("我想睡一个小时后再开车");
            try {
                Thread.sleep(1000*60*60);
            } catch (InterruptedException e) {
                System.out.println("司机被叫醒了");
            }
            System.out.println("司机继续开车");
        } else if (name.equals("警察")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("警察喊：开车！");
            attachThread.interrupt();
        }
    }
    
}
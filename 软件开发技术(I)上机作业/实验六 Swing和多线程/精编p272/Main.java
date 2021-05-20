package 精编p272;

public class Main {
    public static void main(String[] args) {
        Example example = new Example();
        Thread driver, carrier, manager;
        driver = new Thread(example);
        carrier = new Thread(example);
        manager = new Thread(example);
        driver.setName("运货司机");
        carrier.setName("装运工");
        manager.setName("仓库管理员");
        example.setThread(carrier, manager);
        driver.start();
        carrier.start();
        manager.start();
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

package 随机红包;

public class Main {
    public static void main(String[] args) {
        RandomRedEnvelope rre = new RandomRedEnvelope(5.2, 6);//5.2圆，给6个人抢
        int now = 1;
        while (rre.hasNext()) {
            System.out.println("第" + now + "个人抢到：" + rre.giveMoney() + "圆");
            now += 1;
        }
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

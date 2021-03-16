package 数字时钟;

public class Main {
    public static void main(String args[]) {
        ClockExample clockExample = new ClockExample(23, 59, 50);
        while (true) {
            System.out.println(clockExample.show());
            try{
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
            clockExample.run();
        }
    }
}

package 通讯录;

public class Main {
    public static void main(String[] args) {
        try {
            var out = new OutWindow();
            var show = new ShowWindow();
            out.setListenedFrame(out, show, show.getShowArea());
            show.setListenedFrame(out, show, show.getShowArea());
            System.out.println("学号：3200608080，姓名：陈欣阳");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

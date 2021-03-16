public class MainClass {
    public static void main(String[] args) {
        Triangle triangle = new Triangle(20, 10);
        Rectangle rectangle = new Rectangle(30, 40);
        Circle circle = new Circle(10);
        People chen = new People();
        System.out.println("chen计算三角形的面积：");
        System.out.println(chen.computerArea(triangle));
        System.out.println("chen计算矩形的面积：");
        System.out.println(chen.computerArea(rectangle));
        System.out.println("chen计算圆形的面积：");
        System.out.println(chen.computerArea(circle));
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

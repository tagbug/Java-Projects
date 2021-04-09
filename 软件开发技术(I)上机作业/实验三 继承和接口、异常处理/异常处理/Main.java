package 异常处理;

public class Main {
    public static void main(String[] args) {
        Triangle triangle = new Triangle(3, 3, 7);
        System.out.println("错误数据：");
        try {
            System.out.println("三角形面积为：" + triangle.computeArea());//抛出异常
        } catch (TriangleException e) {
            System.out.println("捕获到异常！");
            System.out.println(e.message);
        }
        System.out.println("传入正常数据：");
        triangle.setBorder(5, 6, 7);
        try {
            System.out.println("三角形面积为：" + triangle.computeArea());//正常运行
        } catch (TriangleException e) {
            System.out.println("捕获到异常！");
            System.out.println(e.message);
        }
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

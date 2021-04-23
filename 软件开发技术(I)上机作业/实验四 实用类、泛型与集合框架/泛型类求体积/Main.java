package 泛型类求体积;
public class Main {
    public static void main(String[] args) {
        Triangle triangle = new Triangle(10, 20);
        Trapezium trapezium = new Trapezium(2, 18, 30);
        Circle circle = new Circle(50);
        Column<?> column = new Column<Geometry>(() -> 1, 2);
        System.out.println("三棱柱的体积：");
        column = new Column<Triangle>(triangle, 10);
        System.out.println(column.computeVolume());
        System.out.println("四棱柱的体积：");
        column = new Column<Trapezium>(trapezium, 10);
        System.out.println(column.computeVolume());
        System.out.println("圆柱的体积：");
        column = new Column<Circle>(circle, 10);
        System.out.println(column.computeVolume());
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

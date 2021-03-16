public class MainClass {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle();
        Circle circle = new Circle();
        Geometry geometry = new Geometry(rect, circle);
        geometry.setRectanglePosition(30, 40);
        geometry.setRectangleWidthAndHeight(120, 80);
        geometry.setCirclePosition(260, 300);
        geometry.setCircleRadius(60);
        System.out.print("几何图形中圆和矩形的位置关系是：");
        geometry.showState();
        System.out.println("几何图形重新调整了圆和矩形的位置。");
        geometry.setRectanglePosition(220, 500);
        geometry.setCirclePosition(40, 200);
        System.out.print("调整后，几何图形中圆和矩形的位置关系是；");
        geometry.showState();
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

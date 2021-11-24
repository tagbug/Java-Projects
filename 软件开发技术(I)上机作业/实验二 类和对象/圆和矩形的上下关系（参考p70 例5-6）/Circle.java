public class Circle extends Shape {// 圆类，继承Shape
    // 圆x，y约定为圆心坐标
    private double radius;

    public void setRadius(double radius) {
        if (radius < 0)
            throw new IllegalArgumentException("我的半径不能小于0！");
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}

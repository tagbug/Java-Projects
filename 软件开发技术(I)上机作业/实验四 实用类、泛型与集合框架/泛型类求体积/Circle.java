package 泛型类求体积;
public class Circle implements Geometry{
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    public double computeArea() {
        return radius * radius * Math.PI;
    }
}

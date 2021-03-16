public class Triangle {
    double bottom, height;

    Triangle(double bottom, double height) {
        this.bottom = bottom;
        this.height = height;
    }

    public double getArea() {
        return 0.5 * bottom * height;
    }
}

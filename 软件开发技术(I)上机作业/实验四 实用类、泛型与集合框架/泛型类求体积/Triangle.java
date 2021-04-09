package 泛型类求体积;
public class Triangle implements Geometry{
    private double width;
    private double height;

    Triangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double computeArea() {
        return 0.5 * width * height;
    }
}

package 开闭原则求体积;
public class Trapezium implements Geometry{
    private double upWidth;
    private double downWidth;
    private double height;

    Trapezium(double upWidth, double downWidth, double height) {
        this.upWidth = upWidth;
        this.downWidth = downWidth;
        this.height = height;
    }

    public double computeArea() {
        return (upWidth + downWidth) * height * 0.5;
    }
}

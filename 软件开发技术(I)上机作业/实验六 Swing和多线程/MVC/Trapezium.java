package MVC;

public class Trapezium implements Geometry {
    private double upWidth;
    private double downWidth;
    private double height;
    private boolean canCompute;

    Trapezium(double upWidth, double downWidth, double height) {
        if (upWidth < 0 || downWidth < 0 || height < 0) {
            throw new IllegalArgumentException("底或高不能为负数！");
        }
        this.upWidth = upWidth;
        this.downWidth = downWidth;
        this.height = height;
        canCompute = true;
    }

    public double computeArea() {
        if (canCompute) {
            return (upWidth + downWidth) * height * 0.5;
        }
        throw new IllegalStateException("参数未被正确赋值！");
    }
}

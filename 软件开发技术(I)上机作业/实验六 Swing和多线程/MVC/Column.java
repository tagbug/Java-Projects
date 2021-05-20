package MVC;

public class Column {
    private double area;
    private double height;
    private boolean canCompute;

    Column() {
    }

    public void setArgs(Geometry shape, double height) {
        area = shape.computeArea();
        if (area < 0) {
            throw new IllegalArgumentException("面积不能为负数！");
        }
        if (height < 0) {
            throw new IllegalArgumentException("柱体高不能为负数！");
        }
        this.height = height;
        canCompute = true;
    }

    public double computeVolume() {
        if (canCompute) {
            return area * height;
        }
        throw new IllegalStateException("参数未被正确赋值！");
    }
}
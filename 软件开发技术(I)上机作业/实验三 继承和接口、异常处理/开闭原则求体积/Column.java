package 开闭原则求体积;
public class Column {
    private double area;
    private double height;
    
    Column() {
        area = 0;
        height = 0;
    }
    
    Column(Geometry shape, double height) {
        area = shape.computeArea();
        this.height = height;
    }

    public double computeVolume() {
        return area * height;
    }

    public static double computeVolume(Geometry shape, double height) {
        return shape.computeArea() * height;
    }
}

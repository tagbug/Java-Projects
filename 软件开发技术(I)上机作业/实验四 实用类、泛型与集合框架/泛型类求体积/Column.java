package 泛型类求体积;
public class Column<T extends Geometry> {
    private double area;
    private double height;
    
    Column() {
        area = 0;
        height = 0;
    }
    
    Column(T shape, double height) {
        area = shape.computeArea();
        this.height = height;
    }

    public double computeVolume() {
        return area * height;
    }
}

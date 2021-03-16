public class Rectangle extends Shape {// 矩形类，继承Shape
    // 矩形x，y约定为左上角位置的坐标
    double width, height;

    public void setWidth(double width) {
        if (width > 0)
            this.width = width;
    }

    public void setHeight(double height) {
        if (height > 0)
            this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

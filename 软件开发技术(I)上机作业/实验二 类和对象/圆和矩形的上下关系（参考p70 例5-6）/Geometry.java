public class Geometry {// 几何集合类
    Rectangle rect;
    Circle circle;

    Geometry(Rectangle rect, Circle circle) {
        this.rect = rect;
        this.circle = circle;
    }

    public void setRectanglePosition(double x, double y) {
        rect.setX(x);
        rect.setY(y);
    }

    public void setRectangleWidthAndHeight(double width, double height) {
        rect.setWidth(width);
        rect.setHeight(height);
    }

    public void setCirclePosition(double x, double y) {
        circle.setX(x);
        circle.setY(y);
    }

    public void setCircleRadius(double radius) {
        circle.setRadius(radius);
    }

    public void showState() {
        if (rect.getY() - rect.getHeight() > circle.getY() + circle.getRadius()) {
            System.out.println("矩形在圆上面");
        } 
        else if (rect.getY() < circle.getY() - circle.getRadius()) {
            System.out.println("矩形在圆下面");
        } else {
            System.out.println("矩形与圆重合");
        }
    }
}

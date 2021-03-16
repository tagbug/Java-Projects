class Circle {
    public enum Mode {
        C, S
    };

    private static final double PI = 3.14;
    private double r;

    Circle(double r) {
        this.r = r;
    }

    public double caculate(Mode m) {
        switch (m) {
        case C:
            return 2 * PI * this.r;
        case S:
            return PI * Math.pow(r, 2);
        default:
            return -1;
        }
    }
}

class Test {
    private int a;
    public void fun() {
        System.out.printf("%d", a);
    }
}

public class HelloWorld {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.printf("circle的周长为：%.2f，面积为：%.2f", circle.caculate(Circle.Mode.C), circle.caculate(Circle.Mode.S));
    }
}

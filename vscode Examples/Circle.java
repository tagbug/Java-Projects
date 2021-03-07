public class Circle{
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

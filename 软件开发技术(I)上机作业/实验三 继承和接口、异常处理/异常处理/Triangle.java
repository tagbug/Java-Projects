package 异常处理;

public class Triangle {
    private double a, b, c;

    Triangle(double a, double b, double c) {
        if (a > 0 && b > 0 && c > 0) {
            this.a = a;
            this.b = b;
            this.c = c;
        } else {
            this.a = 0;
            this.b = 0;
            this.c = 0;
        }
    }
    
    public double computeArea() throws TriangleException {
        if (a + b > c && a - b < c) {
            double halfC = (a + b + c) / 2;
            return Math.sqrt(halfC * (halfC - a) * (halfC - b) * (halfC - c));
        } else {
            throw new TriangleException(a, b, c);
        }
    }
    
    public void setBorder(double a, double b, double c) {
        if (a > 0 && b > 0 && c > 0) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}

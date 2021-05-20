package MVC;

public class Triangle implements Geometry {
    private double a, b, c;
    private boolean canCompute;

    Triangle() {
    }

    public double computeArea() throws IllegalArgumentException, IllegalStateException {
        if (!canCompute) {
            throw new IllegalStateException("边长未被正确赋值！");
        }
        if (a + b > c && a - b < c) {
            double halfC = (a + b + c) / 2;
            return Math.sqrt(halfC * (halfC - a) * (halfC - b) * (halfC - c));
        } else {
            throw new IllegalArgumentException(a + ", " + b + ", " + c + " 不符合构成三角形的条件！");
        }
    }

    public void setBorder(double a, double b, double c) throws IllegalArgumentException {
        if (a > 0 && b > 0 && c > 0) {
            this.a = a;
            this.b = b;
            this.c = c;
            canCompute = true;
        } else {
            canCompute = false;
            throw new IllegalArgumentException("边长不能为负数！");
        }
    }
}
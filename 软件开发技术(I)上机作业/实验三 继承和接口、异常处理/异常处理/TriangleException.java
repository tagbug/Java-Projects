package 异常处理;

public class TriangleException extends Exception{
    /**
     * 处理三角形三条边不能构成三角的异常
     */
    private static final long serialVersionUID = 1830094305821702688L;
    String message;

    public TriangleException(double a,double b,double c) {
        message = a + "," + b + "," + c + "不符合构成三角形的条件！";
    }
    public String warnMess() {
        return message;
    }
}

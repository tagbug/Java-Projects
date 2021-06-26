package Example;

public class IntegerTest {
    public static void main(String[] args) {
		// System.setProperty("java.lang.Integer.IntegerCache.high", "128");
        Integer a = 128;
        Integer b = 128;
        System.out.println(a.intValue() == b);
        Integer x = 127;
        Integer y = 127;
        System.out.println(x == y);
    }
}

public class HelloWorld {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.printf("circle的周长为：%.2f，面积为：%.2f", circle.caculate(Circle.Mode.C), circle.caculate(Circle.Mode.S));
    }
}

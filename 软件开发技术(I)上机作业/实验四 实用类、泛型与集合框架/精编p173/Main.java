package 精编p173;

public class Main {
    public static void main(String[] args) {
        String str = "酱油：6.7圆，精盐：0.8圆，榨菜：9.8圆";
        System.out.println("原始字符串：" + str);
        String regex = "：[\\d.]+圆";
        String reStr = str.replaceAll(regex, "");
        reStr = reStr.replaceAll("，", " ");
        System.out.println("过滤后：" + reStr);
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

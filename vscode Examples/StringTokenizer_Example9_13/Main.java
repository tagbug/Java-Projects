package StringTokenizer_Example9_13;

import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        String str = "you are welcome(thank you),nice to meet you";
        StringTokenizer fenxi = new StringTokenizer(str, "() ,");
        int number = fenxi.countTokens();
        while (fenxi.hasMoreTokens()) {
            String s = fenxi.nextToken();
            System.out.println(s + " ");
        }
        System.out.println("共有单词："+number+"个");
    }
}

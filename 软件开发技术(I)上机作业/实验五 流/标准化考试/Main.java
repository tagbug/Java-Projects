package 标准化考试;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File txt = new File("标准化考试/test.txt");
        Scanner reader = new Scanner(System.in);
        try {
            Tester tester = new Tester(txt);
            while (tester.hasNext()) {
                System.out.println(tester.next());
                System.out.println("输入选择的答案：");
                tester.submitAnswer(reader.next().charAt(0));
            }
            reader.close();
            System.out.println("最后的得分：" + tester.getTotalScore());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

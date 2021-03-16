import java.util.*;
public class GuessNumber {
    public static void main(String args[]) {
        Scanner reader = new Scanner(System.in);
        Random random = new Random();
        System.out.println("给你一个1至100之间的整数，请猜测这个数");
        int realNumber = random.nextInt(100) + 1;// nextInt(100)返回[0,100)中的一个随机整数
        System.out.print("输入您的猜测：");
        int userGuess = reader.nextInt();// nextInt()读取输入流的下一个Int型（此处为System.in--标准输入流）
        while (userGuess != realNumber) {// 当用户输入的数不等于系统随机生成的数，循环
            if (userGuess > realNumber) {// 猜大了
                System.out.print("猜大了，再猜猜：");
                userGuess = reader.nextInt();
            } else {// 猜小了
                System.out.print("猜小了，再猜猜：");
                userGuess = reader.nextInt();
            }
        }
        System.out.println("恭喜你，猜对了！");
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

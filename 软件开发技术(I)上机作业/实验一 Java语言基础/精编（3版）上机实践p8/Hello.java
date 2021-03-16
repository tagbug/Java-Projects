public class Hello {
    public static void main(String args[]) {
        System.out.println("你好，很高兴学习Java");// 命令行窗口输出"你好，很高兴学习Java"
        Student zhang = new Student();//定义Student类变量zhang，并将其实例化
        zhang.speak();//调用实例方法
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

class Student {
    void speak() {
        System.out.println("We are students");// 命令行窗口输出"We are students"
    }
}
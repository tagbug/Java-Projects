package 链表排序查找;

public class Main {
    public static void main(String[] args) {
        String[] names = { "小张", "小王", "小李", "小周", "小赵" };
        int[] scores = { 350, 270, 303, 290, 377 };
        StudentInfo[] studentInfos = new StudentInfo[5];
        StudentList studentList = new StudentList();
        for (int i = 0; i < 5; i++) {
            studentInfos[i] = new StudentInfo(names[i], 4200608000L + i, scores[i]);
            studentList.add(studentInfos[i]);
        }
        System.out.println("排序前的学生信息表：");
        System.out.println(studentList.toString());
        System.out.println("排序后的学生信息表：");
        studentList.sortByScore();
        System.out.println(studentList.toString());
        System.out.println("找出总成绩为 350 的学生：");
        var sInfo = studentList.searchByScore(350);
        if (sInfo != null) {
            System.out.print(sInfo.toString());
            System.out.println("其姓名为：" + sInfo.getName() + "\n");
        } else {
            System.out.println("查无此人！");
        }
        System.out.println("找出姓名为 小王 的学生：");
        sInfo = studentList.searchByName("小王");
        if (sInfo != null) {
            System.out.print(sInfo.toString());
            System.out.println("其总成绩为：" + sInfo.getScore() + "\n");
        } else {
            System.out.println("查无此人！");
        }
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

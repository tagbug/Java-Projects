public class MainClass {
    public static void main(String args[]) {
        Village zhaoZhuang, maJiaHeZhi;
        zhaoZhuang = new Village("赵庄", 100);
        maJiaHeZhi = new Village("马家河子", 150);
        Village.treeAmount = 200;
        int leftTree = Village.treeAmount;
        System.out.println("森林中有" + leftTree + "棵树");
        zhaoZhuang.treePlanting(50);
        leftTree = maJiaHeZhi.lookTreeAmount();
        System.out.println("森林中有" + leftTree + "棵树");
        maJiaHeZhi.fellTree(70);
        leftTree = Village.lookTreeAmount();
        System.out.println("森林中有" + leftTree + "棵树");
        System.out.println("赵庄的人口：" + zhaoZhuang.peopleNumber);
        zhaoZhuang.addPeopleNumber(12);
        System.out.println("赵庄的人口：" + zhaoZhuang.peopleNumber);
        System.out.println("马家河子的人口：" + maJiaHeZhi.peopleNumber);
        maJiaHeZhi.addPeopleNumber(10);
        System.out.println("马家河子的人口：" + maJiaHeZhi.peopleNumber);
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

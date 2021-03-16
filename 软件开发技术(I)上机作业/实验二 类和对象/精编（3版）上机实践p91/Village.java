public class Village {
    static int treeAmount;// 模拟森林中树木的数量
    int peopleNumber;// 村庄的人数
    String name;// 村庄的名字

    Village(String name,int peopleNumber) {
        this.name = name;
        this.peopleNumber = peopleNumber;
    }

    void treePlanting(int n) {
        treeAmount += n;
        System.out.println(name + "植树" + n + "棵");
    }

    void fellTree(int n) {
        if (treeAmount - n >= 0) {
            treeAmount -= n;
            System.out.println(name + "伐树" + n + "棵");
        } else {
            System.out.println("无树木可伐");
        }
    }

    static int lookTreeAmount() {
        return treeAmount;
    }

    void addPeopleNumber(int n) {
        peopleNumber += n;
        System.out.println(name + "增加了" + n + "人");
    }
}

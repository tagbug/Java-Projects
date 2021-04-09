package 精编p127;

public class E {
    public static void main(String[] args) {
        Dog yellowDog = new Dog();
        yellowDog.setState(new SoftlyState());
        yellowDog.cry();
        yellowDog.setState(new MeetEnemyState());
        yellowDog.cry();
        yellowDog.setState(new MeetFriendState());
        yellowDog.cry();
        yellowDog.setState(new MeetAnotherDog());
        yellowDog.cry();
        System.out.println("学号：3200608080，姓名：陈欣阳");
    }
}

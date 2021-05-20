package ThreadExample;

public class ThreadExtend {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();
        Pig pig = new Pig();
        dog.start();
        cat.start();
        pig.start();
        for (int i = 0; i < 20; i++) {
            System.out.print(" 我是主人" + i);
        }
    }
}

class Dog extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.print(" 我是狗" + i);
        }
    }
}

class Cat extends Thread {
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.print(" 我是猫" + i);
        }
    }
}

class Pig extends Thread {
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.print(" 我是猪" + i);
        }
    }
}
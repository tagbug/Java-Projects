package Example;

import java.util.ArrayList;
import java.io.InputStream;

class Animal {
    void eat() {

    }

    void sleep() {
        System.out.println("I'm sleep");
    }
}

class Dog extends Animal {
    public static String NAME = "DOG";
    public String DOG_NAME = "DOG";
    private int age;

    public Dog(int age) { // init
        this.age = age;
    }

    public Dog(String name) {

    }

    String getMyName() {
        return DOG_NAME;
    }

    void setMyName(String DOG_NAME) {
        this.DOG_NAME = DOG_NAME;
    }

    void eat() {
        System.out.println("Dog eat");
    }

    void eat(String food) {
        System.out.println("Dog eat" + food);
    }
}

class Number {
    private Dog dog;// dog1: 0x21233
    private int[] arr = { 1, 2, 3 };// 1 2 3

    public Number() {
        arr = new int[3];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
    }

    public Dog getNum() {
        return dog;// dog2: 0x21233
    }

    public void setNum(Dog num) {
        if (num != null)
            this.dog = num;
    }
}

package ThreadExample;

public class RunnableExam {
    public static void main(String[] args) {
        House house = new House(20);
        Thread cat, dog, pig;
        cat = new Thread(house);
        dog = new Thread(house);
        pig = new Thread(house);
        cat.start();
        dog.start();
        pig.start();
    }
}

class House implements Runnable {
    private int count;

    House(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        while (true) {
            if (count <= 0) {
                return;
            }
            eatAndShow();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }

    private synchronized void eatAndShow() {
        count--;
        System.out.println("剩" + count + "克");
    }
}
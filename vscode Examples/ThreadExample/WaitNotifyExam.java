package ThreadExample;

public class WaitNotifyExam {
    public static void main(String[] args) {
        TicketHouse ticketHouse = new TicketHouse();
        Thread zhang, li, zhao;
        zhang = new Thread(ticketHouse);
        li = new Thread(ticketHouse);
        zhao = new Thread(ticketHouse);
        zhang.setName("张");
        li.setName("李");
        zhao.setName("赵");
        zhang.start();
        li.start();
        zhao.start();
    }
}

class TicketHouse implements Runnable {
    private int moneyCount[] = { 0, 3, 0, 0, 0, 0 };
    private int moneyWorth[] = { 1, 5, 10, 20, 50, 100 };
    // private String moneyName[] = { "一块", "五块", "十块", "二十块", "五十块", "一百块" };
    private int moneyBack;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        sale(name);
    }
    
    public synchronized void sale(String name) {
        if (name.equals("张")) {
            while (!saleTicket(20)) {
                System.out.println(name + "靠边等...");
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            System.out.print(name + "给20");
        } else if (name.equals("李")) {
            while (!saleTicket(10)) {
                System.out.println(name + "靠边等...");
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            System.out.print(name + "给10");
        } else if (name.equals("赵")) {
            while (!saleTicket(5)) {
                System.out.println(name + "靠边等...");
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            System.out.print(name + "给5");
        }
        System.out.print("，找赎" + moneyBack + "元");
        System.out.println("给" + name + "入场券");
        notifyAll();
    }

    private synchronized boolean saleTicket(int money) {
        if (money - 5 < 0)
            throw new IllegalArgumentException("不能买票！");
        int moneyClone = money;
        for (int i = moneyCount.length - 1; i >= 0; i--) {
            while (money - moneyWorth[i] >= 0) {
                money -= moneyWorth[i];
                moneyCount[i] += 1;
            }
        }
        money = moneyClone;
        money -= 5;
        moneyBack = 0;
        int moneyLeft[] = moneyCount.clone();
        for (int i = moneyLeft.length - 1; i >= 0; i--) {
            while (moneyLeft[i] > 0) {
                if (money - moneyWorth[i] >= 0) {
                    moneyBack += moneyWorth[i];
                    moneyLeft[i] -= 1;
                } else {
                    break;
                }
            }
        }
        if (money != 0) {
            return false;
        }
        moneyCount = moneyLeft;
        return true;
    }
}
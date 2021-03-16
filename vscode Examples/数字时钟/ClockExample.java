package 数字时钟;

public class ClockExample {
    private int hour, min, sec;//成员变量

    ClockExample(int hour, int min, int sec) {
        //类构造方法
        this.hour = hour >= 0 ? hour : 0;
        this.min = min >= 0 ? min : 0;
        this.sec = sec >= 0 ? sec : 0;
    }
    
    public void run() {
        //走时
        sec += 1;
        if (sec == 60) {
            sec = 0;
            min += 1;
            if (min == 60) {
                min = 0;
                hour += 1;
                if (hour == 24) {
                    hour = 0;
                }
            }
        }
    }
    
    public String show() {
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
}

package Calendar_Example_9;

import java.util.Calendar;
// 静态导入Calendar类的静态常量
import static java.util.Calendar.*;

public class Example9_15 {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int year = 1949, month = 9, date = 1;
        calendar.set(year, month, date);
        show(calendar);
        calendar.set(year, month - 1, date);
        show(calendar);
    }

    private static void show(Calendar calendar) {
        int year = calendar.get(YEAR), month = calendar.get(MONTH) + 1, date = calendar.get(DAY_OF_MONTH);
        System.out.println(year + "年" + month + "月" + date + "日");
    }
}

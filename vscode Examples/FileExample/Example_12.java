package FileExample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

public class Example_12 {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println("calendar对象时间：");
        System.out.println(calendar.getTime().toString());
        File file = new File("time.dat");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(fos);
            objOut.writeObject(calendar);
            objOut.close();
            System.out.println("写入成功！");
            System.out.println("休眠1s...");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(fis);
            Calendar calendar2 = (Calendar) objIn.readObject();
            objIn.close();
            System.out.println("读取成功！");
            Date date = new Date();
            System.out.println("现在时间：" + date.toString());
            System.out.println("读取calendar对象：");
            System.out.println(calendar2.getTime().toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

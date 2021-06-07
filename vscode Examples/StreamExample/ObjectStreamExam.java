package StreamExample;

import java.io.*;
import java.util.Date;

public class ObjectStreamExam {
    public static void main(String[] args) {
        Date date = new Date();
        try (FileOutputStream fos = new FileOutputStream("date.dat", true);
                FileInputStream fis = new FileInputStream("date.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // 将对象信息写入文件
            oos.writeObject(date);
            oos.writeBoolean(true);
            oos.flush();
            // 读对象
            try {
                Date good2 = (Date) ois.readObject();
                System.out.println(good2);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(ois.readBoolean());
            oos.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}

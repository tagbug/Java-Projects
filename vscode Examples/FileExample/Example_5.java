package FileExample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Example_5 {
    public static void main(String[] args) {
        byte[] str1 = "刘一松".getBytes();
        byte[] str2 = "是傻逼".getBytes();
        File file = new File("new.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str1);
            fos.write(str2, 0, str2.length);
            fos.close();
            System.out.println("生成成功！");
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[100];
            int n;
            while ((n = fis.read(b)) != -1) {
                System.out.print(new String(b, 0, n));
            }
            fis.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

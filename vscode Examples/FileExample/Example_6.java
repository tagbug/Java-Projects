package FileExample;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Example_6 {
    public static void main(String[] args) {
        File file = new File("new.txt");
        char a[] = "刘一松是傻逼".toCharArray();
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(a, 0, a.length);
            fw.flush();// 将缓冲区立即写入到磁盘
            fw.close();
            System.out.println("写入成功！");
            FileReader fr = new FileReader(file);
            int n;
            while ((n = fr.read(a)) != -1) {
                System.out.print(new String(a, 0, n));
            }
            fr.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

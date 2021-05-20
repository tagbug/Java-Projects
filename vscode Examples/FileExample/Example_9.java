package FileExample;

import java.io.File;
import java.io.RandomAccessFile;

public class Example_9 {
    public static void main(String[] args) {
        File file = new File("Java Projects/vscode Examples/FileExample/", "Example_9.java");
        try {
            String s = null;
            // 方式 1
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            while ((s = raf.readLine()) != null) {
                System.out.println(new String(s.getBytes("iso-8859-1")));
            }
            raf.close();
            // 方式 2
            raf = new RandomAccessFile(file, "rw");
            long length = raf.length();
            long position = 0;
            while (position < length) {
                s = new String(raf.readLine().getBytes("iso-8859-1"));
                position = raf.getFilePointer();
                System.out.println(s);
            }
            raf.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

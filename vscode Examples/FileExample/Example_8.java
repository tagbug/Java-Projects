package FileExample;

import java.io.File;
import java.io.RandomAccessFile;

public class Example_8 {
    public static void main(String[] args) {
        File file = new File("int.dat");
        int[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            for (var i : data) {
                raf.writeInt(i);
            }
            for (long i = data.length - 1; i >= 0; i--) {
                raf.seek(i * 4);
                System.out.printf("%d\t", raf.readInt());
            }
            raf.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

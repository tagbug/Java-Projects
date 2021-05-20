package MapExample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileTest {
    public static void main(String[] args) {
        File file = new File("./MapExample/FileTest.java");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte a[] = new byte[100];
            int n;
            while ((n = fis.read(a)) != -1) {
                System.out.print(new String(a, 0, n));
            }
            fis.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }
}

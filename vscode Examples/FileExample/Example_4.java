package FileExample;

import java.io.File;
import java.io.FileInputStream;

public class Example_4 {
    public static void main(String[] args) {
        File f = new File("Java Projects/vscode Examples/FileExample/", "Example_4.java");
        try {
            FileInputStream fis = new FileInputStream(f);
            int n;
            byte b[] = new byte[100];
            while ((n = fis.read(b)) != -1) {
                System.out.print(new String(b, 0, n));
            }
            fis.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

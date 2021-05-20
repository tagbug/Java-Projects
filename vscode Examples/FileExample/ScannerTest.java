package FileExample;

import java.io.File;
import java.util.Scanner;

public class ScannerTest {
    public static void main(String[] args) {
        File file = new File("D:/repos/Java Projects/vscode Examples/FileExample/", "ScannerTest.java");
        try {
            Scanner in = new Scanner(file);// 中文测试
            while (in.hasNext()) {
                System.out.println(in.nextLine());
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
    }
}

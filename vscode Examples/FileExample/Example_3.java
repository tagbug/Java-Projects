package FileExample;

import java.io.File;

public class Example_3 {
    public static void main(String[] args) {
        try {
            Runtime ce = Runtime.getRuntime();
            File f = new File("C:/windows", "Notepad.exe");
            ce.exec(f.getAbsolutePath());
            f = new File("C:/Program Files/Internet Explorer", "IEXPLORE www.baidu.com");
            ce.exec(f.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

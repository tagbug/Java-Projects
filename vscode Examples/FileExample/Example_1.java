package FileExample;

import java.io.File;
import java.io.IOException;

public class Example_1 {
    public static void main(String[] args) {
        File f = new File("Java Projects/vscode Examples/FileExample/Example_1.java");
        System.out.println(f.getName() + "是可读的吗：" + f.canRead());
        System.out.println(f.getName() + "的长度：" + f.length());
        System.out.println(f.getName()+"的绝对路径："+f.getAbsolutePath());
        File newFile = new File("new.txt");
        System.out.println("在当前目录下创建新文件："+newFile.getName());
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
                System.out.println("创建成功！");
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}

package FileExample;

import java.io.File;

public class Example_2 {
    public static void main(String[] args) {
        File f = new File("Java Projects/vscode Examples/FileExample/");
        var fileNames = f.list((dir, name) -> name.endsWith("java"));
        for (var fileName : fileNames) {
            System.out.println(fileName);
        }
    }
}

package Example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 目录拷贝
 * 
 * @since 10
 * @author TagBug {@link https://github.com/tagbug}
 */
public class FileCopy {
    public static void main(String[] args) {
        String fromDir = "D:\\repos\\Java Projects";
        String toDir = "D:\\repos\\Java_Pro_Copied";
        Scanner scanner = new Scanner(System.in);
        // 用户输入
        System.out.print("输入原始目录：");
        fromDir = scanner.nextLine();
        File rootDir = new File(fromDir);
        while (!rootDir.exists()) {
            System.out.print("目录或文件不存在，请重新输入：");
            fromDir = scanner.nextLine();
        }
        System.out.print("输入要拷贝到的目录：");
        toDir = scanner.nextLine().replace('/', '\\');
        scanner.close();
        // 拷贝
        copyDir(fromDir, toDir);
        System.out.println("拷贝完成！");
    }

    /**
     * 拷贝目录
     * 
     * @param fromDir 原始目录
     * @param toDir   要拷贝到的新目录
     */
    public static void copyDir(String fromDir, String toDir) {
        fromDir = fromDir.replace('/', '\\');// 替换成反斜杠是因为在File存储Path时用的反斜杠
        toDir = toDir.replace('/', '\\');
        // 递归获取目录结构
        var files = new ArrayList<File>();
        var dirs = new ArrayList<File>();
        getAllFiles(new File(fromDir), files, dirs);
        // 创建目录结构
        for (var d : dirs) {
            String to = d.getAbsolutePath().replace(fromDir, toDir);
            new File(to).mkdirs();
        }
        // 复制文件
        for (var f : files) {
            String to = f.getAbsolutePath().replace(fromDir, toDir);
            try {
                Files.copy(Path.of(f.getPath()), new FileOutputStream(new File(to)));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * 递归查找目录&文件
     * 
     * @param directory 要递归查找的目录
     * @param files     存放查找到的文件
     * @param dirs      存放查找到的目录
     */
    public static void getAllFiles(File directory, ArrayList<File> files, ArrayList<File> dirs) {
        if (directory.isDirectory()) {
            dirs.add(directory);
            for (var f : directory.listFiles()) {
                getAllFiles(f, files, dirs);
            }
        } else {
            files.add(directory);
        }
    }
}

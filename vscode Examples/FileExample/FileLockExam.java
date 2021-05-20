package FileExample;

import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Scanner;

public class FileLockExam {
    public static void main(String[] args) {
        File file = new File("D:/repos/Java Projects/vscode Examples/FileExample/FileLockExam.java");
        Scanner in = new Scanner(System.in);
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");// 不支持读取中文？
            FileChannel channel = raf.getChannel();
            FileLock lock = channel.tryLock();// 加锁
            System.out.println("输入要读的行数：");
            while (in.hasNextInt()) {
                int length = in.nextInt();
                for (int i = 0; i < length; i++) {
                    String line = raf.readLine();
                    if (line == null) {
                        lock.release();
                        lock.close();
                        throw new EOFException();
                    }
                    System.out.println(line);
                }
            }
            lock.release();
            lock.close();
        } catch (EOFException eof) {
            System.out.println("达到文件末尾！");
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            in.close();
        }
    }
}

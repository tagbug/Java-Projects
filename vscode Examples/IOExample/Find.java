package IOExample;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Find {
    public static void main(String[] args) throws IOException {
        /*
         * Files.walk(Path.of(System.getProperty("user.dir"))).forEach(path -> { if
         * (Files.isDirectory(path)) return; System.out.println(path); });
         */
        /*
         * try (var directoryStream = Files .newDirectoryStream(Path.
         * of("E:/GitCode/Java Projects/vscode Examples/SwingExample"), "**.java")) {
         * for (var path : directoryStream) { System.out.println(path); } }
         */
        Files.walkFileTree(Path.of(System.getProperty("user.dir")), new FileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("处理目录结束：" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("准备进入目录：" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("找到文件：" + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }

        });
    }
}

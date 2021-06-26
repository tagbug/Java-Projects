package Example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 递归查找指定路径下所有文件中的指定关键字
 * 
 * @since 10
 * @author TagBug,《Java核心技术 卷一》
 */
public class StrSearcher {
    private static final int FILE_QUEUE_SIZE = 100;
    private static final int SEARCH_THREADS = 100;
    private static final Path DUMMY = Path.of("");
    private static BlockingQueue<Path> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.print("输入一个路径（eg：D:/repos/Java Projects）：");
            String directory = in.nextLine();
            System.out.print("输入要查找的关键字（eg：java）：");
            String keyword = in.nextLine();

            Runnable enumerator = () -> {
                try {
                    enumerate(Path.of(directory));
                    queue.put(DUMMY);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                }
            };

            new Thread(enumerator).start();
            for (int i = 0; i < SEARCH_THREADS; i++) {
                Runnable searcher = () -> {
                    try {
                        var done = false;
                        while (!done) {
                            Path file = queue.take();
                            if (file == DUMMY) {
                                queue.put(file);
                                done = true;
                            } else {
                                search(file, keyword);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                    }
                };
                new Thread(searcher).start();
            }
        }
    }

    /**
     * 递归的枚举给定目录和其子目录中的所有文件
     * 
     * @param directory 给定目录
     * @throws IOException
     * @throws InterruptedException
     */
    public static void enumerate(Path directory) throws IOException, InterruptedException {
        try (Stream<Path> children = Files.list(directory)) {
            for (Path child : children.collect(Collectors.toList())) {
                if (Files.isDirectory(child)) {
                    enumerate(child);
                } else {
                    queue.put(child);
                }
            }
        }
    }

    /**
     * 在给定文件中查找所有与给定关键字匹配的行，并将这些行打印下来
     * 
     * @param file    给定文件
     * @param keyword 给定关键字
     * @throws IOException
     */
    public static void search(Path file, String keyword) throws IOException {
        try (var in = new Scanner(file, StandardCharsets.UTF_8)) {
            int lineNum = 0;
            while (in.hasNextLine()) {
                lineNum++;
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    System.out.printf("(%s:%d)\n%s\n", file, lineNum, line);
                }
            }
        }
    }
}

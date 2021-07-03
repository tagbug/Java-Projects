package ThreadExample;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * This program demonstrates the Callable interface and executors.
 */
public class ExecutorDemo {
    /**
     * Counts occurrences of a given word in a file.
     * 
     * @param word the given word
     * @param path the given file
     * @return the number of times the word occurs in the given file
     */
    public static long occurrences(String word, Path path) {
        try (var in = new Scanner(path)) {
            int count = 0;
            while (in.hasNext()) {
                if (in.next().equals(word))
                    count++;
            }
            return count;
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Returns all descendants of a given directory
     * 
     * @param rootDir the root directory
     * @return a set of all descendants of the root directory
     * @throws IOException
     */
    public static Set<Path> descendants(Path rootDir) throws IOException {
        try (Stream<Path> entries = Files.walk(rootDir)) {
            return entries.filter(Files::isRegularFile).collect(Collectors.toSet());
        }
    }

    /**
     * Yields a task that searches for a word in a file.
     * 
     * @param word the word to search
     * @param path the file in which to search
     * @return the search task that yields the path upon success
     */
    public static Callable<Path> searchForTask(String word, Path path) {
        return () -> {
            try (var in = new Scanner(path)) {
                while (in.hasNext()) {
                    if (in.next().equals(word))
                        return path;
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Search in " + path + " canceled.");
                    }
                }
            }
            throw new NoSuchElementException();
        };
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. D:/repos/Java Projects): ");
            String start = in.nextLine();
            System.out.print("Enter keyword (e.g. java): ");
            String word = in.nextLine();

            Set<Path> files = descendants(Path.of(start));
            var tasks = new ArrayList<Callable<Long>>();
            for (Path file : files) {
                Callable<Long> task = () -> occurrences(word, file);
                tasks.add(task);
            }
            ExecutorService executor = Executors.newCachedThreadPool();
            // use a single thread executor instead to see if multiple threads speed up the
            // search
            // ExecutorService executor = Executors.newSingleThreadExecutor();

            Instant startTime = Instant.now();
            List<Future<Long>> results = executor.invokeAll(tasks);
            long total = 0;
            for (Future<Long> result : results)
                total += result.get();
            Instant endTime = Instant.now();
            System.out.println("Occurrences of " + word + ": " + total);
            System.out.println("Time elapsed: " + Duration.between(startTime, endTime).toMillis() + " ms");

            var searchTasks = new ArrayList<Callable<Path>>();
            for (Path file : files)
                searchTasks.add(searchForTask(word, file));
            Path found = executor.invokeAny(searchTasks);
            System.out.println(word + " occurs in: " + found);

            if (executor instanceof ThreadPoolExecutor)// the single thread executor isn't
                System.out.println("Largest pool size: " + ((ThreadPoolExecutor) executor).getLargestPoolSize());
            executor.shutdown();
        }
    }
}

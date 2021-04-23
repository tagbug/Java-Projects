package SetExamples;

import java.util.*;

public class SetTest {
    public static void main(String[] args) {
        var words = new HashSet<String>();
        long totalTime = 0;
        try (var in = new Scanner(System.in)) {
            while (in.hasNext()) {
                long callTime = System.currentTimeMillis();
                words.add(in.next());
                totalTime += System.currentTimeMillis() - callTime;
            }
        }
        var iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++) {
            System.out.println(iter.next());
        }
        System.out.println("...");
        System.out.println(words.size()+" distinct words. "+totalTime+" ms");
    }
}

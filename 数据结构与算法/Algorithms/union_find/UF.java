package Algorithms.union_find;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Scanner;

/**
 * Union-Find算法 检查图连通性 参见《算法4》p1.5
 * 
 * @author TagBug
 * @date 2021-9-29 13:08:27
 */
public class UF {
    private int[] id; // 触点数组
    private int count; // 分量数
    private int[] size; // 根节点树所对应分量大小
    // private int maxDepth;

    public UF(int N) {
        id = new int[N];
        count = N;
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        // int depth = 1;
        int root = p;
        while (root != id[root]) {
            // depth++;
            root = id[root];
        }
        // 扁平化
        while (p != root) {
            int newP = id[p];
            id[p] = root;
            p = newP;
        }
        /*
         * if (depth > maxDepth) maxDepth = depth;
         */
        return root;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (size[pRoot] < size[qRoot]) {
            id[pRoot] = id[qRoot];
            size[qRoot] += size[pRoot];
        } else {
            id[qRoot] = id[pRoot];
            size[pRoot] += size[qRoot];
        }
        count--;
    }

    /**
     * 测试用例
     * <p>
     * tinyUF.txt / mediumUF.txt / largeUF.txt
     * <p>
     * Download from https://algs4.cs.princeton.edu/15uf/*.txt
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String filePath = "E:/GitCode/Java Projects/数据结构与算法/Algorithms/union_find/mediumUF.txt";
        try (var scanner = new Scanner(Path.of(filePath))) {
            // 算法计时开始
            var start = Instant.now();
            // 算法计时开始
            int N = scanner.nextInt();
            UF uf = new UF(N);
            while (scanner.hasNext()) {
                int p = scanner.nextInt();
                int q = scanner.nextInt();
                if (uf.connected(p, q)) {
                    // System.out.println("已连通触点：" + p + " " + q);182.92.67.52
                    continue;
                }
                uf.union(p, q);
            }
            System.out.println("连通分量数：" + uf.count());
            // System.out.println("最大深度：" + uf.maxDepth);
            // 算法计时结束
            var end = Instant.now();
            System.out.println("算法耗时：" + (end.toEpochMilli() - start.toEpochMilli()) + "毫秒");
            // 算法计时结束
        }
    }
}

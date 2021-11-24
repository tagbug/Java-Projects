package HomeWorkTwo;

import java.util.ArrayDeque;
import java.util.ArrayList;

import java.util.Deque;
import java.util.LinkedList;

public class HomeWork4 {
    private ArrayList<ArrayList<Integer>> res;
    Deque<Integer> stack;
    Graph G;
    int formIndex, toIndex;
    boolean[] visited;

    HomeWork4(Graph G, int fromIndex, int toIndex) {
        this.G = G;
        this.formIndex = fromIndex;
        this.toIndex = toIndex;
        res = new ArrayList<ArrayList<Integer>>();
        stack = new ArrayDeque<>();
        visited = new boolean[G.V()];
    }

    public ArrayList<ArrayList<Integer>> findPath() {
        stack.offerLast(formIndex);
        visited[formIndex] = true;
        findPath(formIndex, toIndex);
        return res;
    }

    private void findPath(int from, int to) {
        if (from == to) {
            res.add(new ArrayList<>(stack));
        }
        for (int v : G.adj(from)) {
            if (!visited[v]) {
                visited[v] = true;
                stack.offerLast(v);
                findPath(v, to);
                visited[v] = false;
                stack.pollLast();
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph("E:\\GitCode\\Java Projects\\数据结构与算法\\HomeWorkTwo\\g.txt");
        HomeWork4 homeWork4 = new HomeWork4(g, 0, 1);
        System.out.println(homeWork4.findPath());
    }
}

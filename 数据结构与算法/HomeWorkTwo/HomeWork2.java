package HomeWorkTwo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HomeWork2 {
    private ArrayList<Integer> order;
    private boolean[] visited;
    private Graph G;

    HomeWork2(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        order = new ArrayList<>();

        for(int i = 0; i < G.V(); i ++){
            if(!visited[i]){
                bfs(i);
            }
        }
    }

    private void bfs(int v){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);
        visited[v] = true;
        while(!queue.isEmpty()){
            int s = queue.remove();
            order.add(s);
            for(int j : G.adj(s)){
                if(!visited[j]){
                    visited[j] = true;
                    queue.add(j);
                }
            }
        }
    }

    public Iterable<Integer> order(){
        return order;
    }
}

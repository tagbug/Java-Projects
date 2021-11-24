package HomeWorkTwo;

import java.util.ArrayList;

public class HomeWork1 {
    private ArrayList<Integer> preOrder;
    private boolean[] visited;
    private Graph G;

    HomeWork1(Graph G){
        this.G = G;
        preOrder = new ArrayList<>();
        visited = new boolean[G.V()];
        for(int i = 0; i < G.V(); i ++){
            if(!visited[i]){
                dfs(i);
            }
        }
    }

    private void dfs(int v){
        visited[v] = true;
        preOrder.add(v);
        Iterable<Integer> adj = G.adj(v);
        for(int i : adj){
            if(!visited[i]){
                dfs(i);
            }
        }
    }

    private Iterable<Integer> res(){
        return preOrder;
    }
}

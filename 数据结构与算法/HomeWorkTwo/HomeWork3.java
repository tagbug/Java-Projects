package HomeWorkTwo;

import java.util.ArrayList;
import java.util.Stack;

public class HomeWork3 {
    private ArrayList<Integer> res;
    private boolean[] visited;
    private Graph G;

    HomeWork3(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        for(int i = 0; i < G.V(); i ++){
            if(!visited[i]){
                dfs(i);
            }
        }
    }

    private void dfs(int v){
        visited[v] = true;
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        while(!stack.isEmpty()){
            int s = stack.pop();
            res.add(s);
            for(int f : G.adj(s)){
                if(!visited[f]){
                    stack.push(f);
                    visited[f] = true;
                }
            }
        }
    }

    public Iterable<Integer> res(){
        return res;
    }
}

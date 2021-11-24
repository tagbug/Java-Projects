package DataStruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class Graph<T> {
    private static class ArcNode {
        int adjvex; // 顶点位于顶点数组的下标
        ArcNode next; // 指向下一个ArcNode

        public ArcNode(int adjvex, ArcNode next) {
            this.adjvex = adjvex;
            this.next = next;
        }
    }

    private static class VertexNode<T> implements Iterable<ArcNode> {
        T vertex; // 顶点数据
        ArcNode firstedge; // 指向顶点的第一条边

        public VertexNode(T vertex, ArcNode firstedge) {
            this.vertex = vertex;
            this.firstedge = firstedge;
        }

        /**
         * 迭代器，用来遍历VertexNode的所有ArcNode
         */
        @Override
        public Iterator<ArcNode> iterator() {
            class VerNodeIterator implements Iterator<ArcNode> {
                ArcNode node;

                VerNodeIterator(VertexNode<T> node) {
                    this.node = node.firstedge;
                }

                @Override
                public boolean hasNext() {
                    return this.node != null;
                }

                @Override
                public ArcNode next() {
                    ArcNode node = this.node;
                    this.node = this.node.next;
                    return node;
                }
            }
            return new VerNodeIterator(this);
        }
    }

    private static class ALGraph<T> {
        ArrayList<VertexNode<T>> adjlist = new ArrayList<>(); // 邻接表
        int vertexNum, arcNum; // 顶点数、边数
    }

    private ALGraph<T> graph = new ALGraph<>();

    /**
     * 输入数据规范：
     * <p>
     * 顶点数组：["ver1","ver2",...]
     * <p>
     * 边集数组：[[1,2],[],[3],[0],...] 内层数组为adjvex信息
     */
    public Graph(List<T> vertexList, List<List<Integer>> arcList) {
        this.graph.vertexNum = vertexList.size();
        // 填充顶点信息
        for (var vertex : vertexList) {
            this.graph.adjlist.add(new VertexNode<T>(vertex, null));
        }
        int arcNum = 0;
        var arcIter = arcList.iterator();
        var verNodeIter = this.graph.adjlist.iterator();
        // 填充边信息
        while (verNodeIter.hasNext() && arcIter.hasNext()) {
            var verNode = verNodeIter.next();
            var list = arcIter.next();
            arcNum += list.size(); // 统计边数
            ArcNode node = null;
            // 对应输入数据的边集数组：[→[1,2]←,[],...]
            for (var arc : list) {
                if (verNode.firstedge == null) {
                    verNode.firstedge = new ArcNode(arc, null);
                    node = verNode.firstedge;
                } else {
                    node.next = new ArcNode(arc, null);
                    node = node.next;
                }
            }
        }
        this.graph.arcNum = arcNum;
    }

    /**
     * 非递归 深度优先遍历
     * 
     * @param start 从哪个顶点开始遍历
     * @return 遍历顺序
     */
    public String DFS(int start) {
        // 定义一个verList用于判断顶点是否已遍历
        var verList = new ArrayList<Boolean>(this.graph.vertexNum);
        for (int i = 0; i < this.graph.vertexNum; i++) {
            verList.add(false);
        }
        // 定义一个栈用于存放遍历历史
        var stack = new Stack<Integer>();
        // 定义一个StringBuilder用于返回遍历结果
        var sb = new StringBuilder();
        // 开始遍历
        // 从start开始
        verList.set(start, true);
        stack.push(start);
        sb.append(this.graph.adjlist.get(start).vertex);
        // 递归 & 碰壁回溯
        while (!stack.isEmpty()) {
            int verIdx = stack.get(stack.size() - 1);
            // 遍历Vertex对应的ArcNode
            // 如果ArcNode指向的Vertex未被遍历过，则推入栈中
            // 否则，当所有的ArcNode都被遍历过时，将该Vertex出栈
            boolean isAllTraversed = true;
            for (var arcNode : this.graph.adjlist.get(verIdx)) {
                if (!verList.get(arcNode.adjvex)) {
                    // 如果对应的Vertex未被遍历过
                    isAllTraversed = false;
                    stack.push(arcNode.adjvex);
                    verList.set(arcNode.adjvex, true);
                    sb.append(", " + this.graph.adjlist.get(arcNode.adjvex).vertex);
                    break;
                }
            }
            if (isAllTraversed) {
                stack.pop();
            }
        }
        return sb.toString();
    }

    /**
     * 非递归 输出图 G 中从顶点 u 到顶点 v 的所有简单路径
     * 
     * @param start 开始顶点
     * @param end   结束顶点
     * @return 路径数组
     */
    public List<String> findPath(int start, int end) {
        // 定义一个栈用于存放遍历节点历史
        var stack = new Stack<Integer>();
        // 定义一个栈用于存放遍历节点对应边的历史
        var arcStack = new Stack<ArrayList<Integer>>();
        // 定义一个数组用于存放所有简单路径
        var pathList = new ArrayList<String>();
        // 开始遍历
        // 从start开始
        stack.push(start);
        arcStack.push(new ArrayList<Integer>());

        // 递归 & 碰壁回溯
        while (!stack.isEmpty()) {
            int verIdx = stack.get(stack.size() - 1);
            ArrayList<Integer> arcList = arcStack.get(arcStack.size() - 1);
            // 遍历Vertex对应的ArcNode
            // 如果该ArcNode已被遍历过，则跳过该ArcNode
            // 否则标记该ArcNode已被遍历
            // 如果与结束顶点重合，构造路径字符串并添加到pathList中
            // 如果ArcNode指向的Vertex不在路径中，则推入栈中
            // 否则，当所有的ArcNode都在路径中或都被遍历过，将该Vertex出栈
            boolean isAllTraversed = true;
            for (var arcNode : this.graph.adjlist.get(verIdx)) {
                // 如果在集合中，直接跳过，到下一个
                if (arcList.contains(arcNode.adjvex)) {
                    continue;
                }
                // 不在集合中，将其加入到集合内，表示这条边已经访问了
                arcList.add(arcNode.adjvex);

                // 如果找到结束顶点
                if (arcNode.adjvex == end) {
                    // 构造路径字符串
                    var sb = new StringBuilder();
                    for (var idx : stack) {
                        sb.append(this.graph.adjlist.get(idx).vertex + ", ");
                    }
                    sb.append(this.graph.adjlist.get(end).vertex);
                    var pathStr = sb.toString();
                    // 将路径字符串添加到pathList中
                    pathList.add(pathStr);
                    continue;
                }

                // 如果对应的Vertex不在路径中
                if (!stack.contains(arcNode.adjvex)) {
                    isAllTraversed = false;
                    stack.push(arcNode.adjvex);
                    arcStack.push(new ArrayList<>());
                    break;
                }

                // 都不符合
                continue;
            }
            if (isAllTraversed) {
                stack.pop();
                arcStack.pop();
            }
        }
        return pathList;
    }

    /**
     * 单元测试
     * 
     * @param args
     */
    public static void main(String[] args) {
        // 初始化有向图
        // var verList = List.of("V1", "V2", "V3", "V4");
        // List<List<Integer>> arcList = List.of(List.of(1, 2), List.of(), List.of(3),
        // List.of(0));
        var verList = List.of("V1", "V2", "V3", "V4", "V5", "V6");
        List<List<Integer>> arcList = List.of(List.of(1, 2), List.of(), List.of(3, 4, 5), List.of(4), List.of(5, 1),
                List.of(0, 1));
        var graph = new Graph<String>(verList, arcList);
        // DFS测试
        System.out.println("DFS(从V1开始):");
        System.out.println(graph.DFS(0));
        // FindPath测试
        System.out.println("FindPath(V1->V2):");
        var findResult = graph.findPath(0, 1);
        for (var path : findResult) {
            System.out.println(path);
        }
    }
}
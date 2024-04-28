package l3m.cyber.planner.utils;


//处理处理查找（Find）和合并（Union）操作
public class UnionFind {
    private int nb;
    private int[] parent;//存储每个元素的父节点
    private int[] rang;//存储每个根节点的树的深度

    public UnionFind(int n) {
        parent = new int[n];
        rang = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rang[i] = 0;
        }
    }//构造函数中，每个元素的父节点初始指向自己，这表示每个元素最开始都是自己一个集合
    //每个元素的rang（树的高度为0）

    public int find(int v) {
        if (v != parent[v]) {
            parent[v] = find(parent[v]);  // 路径压缩
        }
        return parent[v];
    }//参数v是要查找的元素，返回的是参数v的父节点

    public void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            if (rang[rootU] < rang[rootV]) {
                parent[rootU] = rootV;
            } else if (rang[rootV] < rang[rootU]) {
                parent[rootV] = rootU;
            } else {
                parent[rootV] = rootU;
                rang[rootU]++;
            }
        }
    }//先查找每个元素的根，如果根不同，则合并他们，选择rang大的作为根节点，并且更新另一个节点的父节点
    //若rang相同，则可以任意选择一个作为根

    @Override
    public String toString() {
        // 方法实现根据需要进行
        return "UnionFind{...}";
    }
}

package l3m.cyber.planner.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graphe implements Cloneable {
    private int nbSommets;
    private int[][] adj;//Et adj est un matrix utilisé pour déterminer rapidement s'il existe une connexion directe entre deux sommets.临接矩阵
    private double[][] poidsA;//poidsA est important car il fournit des informations sur le coût de chaque arête. 权重矩阵
    private ArrayList<Integer> nomSommets;//还没有被添加的点

    //crée un graphe avec n sommets, nommés 0 à n-1 et aucune arête
    public Graphe(int n){
        this.nbSommets = n;
        this.poidsA = null;
        this.nomSommets = new ArrayList<>();
        for(int i = 0; i<n; i++){
            this.nomSommets.add(i);
        }
        this.adj = new int[nbSommets][nbSommets];
        for(int i=0;i<nbSommets;i++){
            for(int j=0;j<nbSommets;j++){
                this.adj[i][j] = 0;
            }
        }
        //ici, on  creé la matrice d'adjacence dont l'elements sont 0, parce que la matrice poidsA est null.
    }
    
    //Il génère des noms de sommets par défaut en fonction du nombre de sommets. Cela convient pour une matrice entièrement pondérée
    public Graphe(double[][] poidsA,int n){
        this.nbSommets = n;
        this.poidsA = poidsA;
        this.adj = new int[n][n];
        for(int i=0;i<nbSommets;i++){
            for(int j=0;j<nbSommets;j++){
                if(this.poidsA[i][j] != 0){
                    adj[i][j] = 1;
                }else{
                    adj[i][j] = 0;
                }
            }
        }
    }

    //Ce constructeur n'accepte qu'une liste de noms de sommets et peut être utilisé pour initialiser un graphe sans arêtes
    //(c'est-à-dire que les matrices d'adjacence et de poids sont initialisées en interne pour représenter l'absence d'arêtes).
    //Un tel constructeur peut être utilisé pour construire le graphe étape par étape, en ajoutant des arêtes par la suite.
    public Graphe(ArrayList<Integer> nomSommets){
        this.nomSommets = nomSommets;
        this.nbSommets = nomSommets.size();
        this.poidsA = new double[nbSommets][nbSommets];
        this.adj = new int[nbSommets][nbSommets];
    }

    //Ce constructeur accepte une matrice de poids des arêtes et une liste de noms de sommets. 
    //Ceci s'applique à la création d'un graphe pondéré.
    public Graphe(double[][] poidsA, ArrayList<Integer> nomSommets){
        this.nomSommets = nomSommets;
        this.nbSommets = nomSommets.size();
        this.poidsA = poidsA;
        this.adj = new int[nbSommets][nbSommets];
        for(int i=0;i<nbSommets;i++){
            for(int j=0;j<nbSommets;j++){
                if(this.poidsA[i][j] != 0){
                    adj[i][j] = 1;
                }else{
                    adj[i][j] = 0;
                }
            }
        }
    }


    ///Convient pour créer un graphe non pondéré ou un graphe dont les poids sont traités séparément,
    /// en se concentrant principalement sur la structure du graphe 
    //(c'est-à-dire la connectivité entre les sommets) plutôt que sur les poids des arêtes.
    public Graphe(int[][] adj, ArrayList<Integer> nomSommets){
        this.adj = adj;
        this.nomSommets = nomSommets;
        this.nbSommets = nomSommets.size();
    }


    //+pondereAretes() : void是用来干什么的

    //创建并返回Graphe对象的一个深拷贝。这个实现确保了所有的数据结构—如数组和列表—都被逐个元素复制，而不是仅仅复制引用
    @Override
    public Graphe clone() {
        try {
            Graphe cloned = (Graphe) super.clone(); // 调用Object类的clone()方法进行浅拷贝,针对各种定义的属性
            // 深克隆数组和集合，因为
            cloned.adj = this.adj != null ? this.adj.clone() : null;
            if (this.adj != null) {
                cloned.adj = new int[this.adj.length][];
                for (int i = 0; i < this.adj.length; i++) {
                    cloned.adj[i] = this.adj[i].clone(); // 对每个子数组进行深拷贝
                }
            }
            if (this.poidsA != null) {
                cloned.poidsA = new double[this.poidsA.length][];
                for (int i = 0; i < this.poidsA.length; i++) {
                    cloned.poidsA[i] = this.poidsA[i].clone(); // 对权重矩阵进行深拷贝
                }
            }
            cloned.nomSommets = new ArrayList<>(this.nomSommets); // 对顶点名列表进行深拷贝
            return cloned;
        } catch (CloneNotSupportedException e) {
            // 此异常不应发生，因为我们实现了Cloneable接口
            throw new AssertionError(e);
        }
    }



    //ajouter l'arete dans le graphe
    //考虑有向图和无向图之间的关系，如果是有向图只需要单项设置，但是无向图需要两个都添加
    //这里的模型就是两个城市之间只有一条路可以走，且不需要考虑边的方向性带来的额外变量，使用无向图能够简化问题的复杂度。
    public void ajouterArete(int i,int j){
        if(i>=0 && i<=nbSommets && j>=0 && j<=nbSommets){
            this.adj[i][j] = 1;
            this.adj[j][i] = 1;
        }
    }

    //ajouter l'arete dans le graphe avec le poid
    public void ajouterArete(int i,int j,double poids){
        ajouterArete(i, j);
        this.poidsA[i][j] = poids;
        this.poidsA[j][i] = poids;
    }

    //两种情况：1.根据现有数据进行调整，2.根据配置或者外部输入调整
    //这里暂时使用第一种方法
    public void ajusterPoids(int i, int j){
        if (adj[i][j] == 1 && poidsA[i][j] != 0) {
            poidsA[i][j] *= 1.1; // 假设增加10%的权重
            poidsA[i][j] *= 1.1; // 假设增加10%的权重
        }
    }
    //暂时定为void，根据后续调整
    public void retirerArete(int i, int j){
        this.adj[i][j] = 0;
        this.adj[j][i] = 0;
        this.poidsA[i][j] = 0;
        this.poidsA[j][i] = 0;
    }

    //Déterminer si deux villes sont voisines
    public boolean voisin(int i, int j){
        if(this.adj[i][j] == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nbSommets; i++) {
            sb.append(i + ": ");
            for (int j = 0; j < nbSommets; j++) {
                if (adj[i][j] == 1) {
                    sb.append(j + " ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //BFS
    //队列的先进先出（FIFO, First-In-First-Out）特性有关，这个特性非常适合用来按层级（或宽度）顺序遍历图
    public ArrayList<Integer> parcoursLargeur(int debut) {
        ArrayList<Integer> visited = new ArrayList<>(); // Crée une liste pour stocker les sommets visités
        Queue<Integer> queue = new LinkedList<>();      // Utilise une file pour soutenir la recherche en largeur
        queue.add(debut);                               // Ajoute le sommet de départ à la file
    
        while (!queue.isEmpty()) {                      // Tant que la file n'est pas vide
            int vertex = queue.poll();                  // Retire un sommet de la file
            if (!visited.contains(vertex)) {            // Si ce sommet n'a pas encore été visité
                visited.add(vertex);                    // Ajoute-le à la liste des visités
                for (int i = 0; i < nbSommets; i++) {   // Parcourt tous les points adjacents
                    if (adj[vertex][i] == 1 && !visited.contains(i)) { // Si un point adjacent est connecté et pas visité
                        queue.add(i);                   // Ajoute ce point à la file pour le visiter plus tard
                    }
                }
            }
        }
        return visited;                                 // Retourne la liste des sommets visités
    }


    //DFS 画图解释是最好的
    //stack遵循后进先出
    //深度优先搜索（Depth-First Search, DFS）通常使用栈来实现，这是因为栈的后进先出（Last-In-First-Out, LIFO）特性非常适合DFS的探索方式
    public ArrayList<Integer> parcoursProfondeur(int debut) {
        ArrayList<Integer> visited = new ArrayList<>();  // 创建一个列表来存储访问过的顶点
        Stack<Integer> stack = new Stack<>();            // 使用栈来支持深度优先搜索
        stack.push(debut);                               // 将起始顶点压入栈
    
        while (!stack.isEmpty()) {                       // 只要栈不为空就继续
            int vertex = stack.pop();                    // 从栈中取出一个顶点
            if (!visited.contains(vertex)) {             // 如果这个顶点尚未被访问过
                visited.add(vertex);                     // 标记为已访问
                for (int i = nbSommets - 1; i >= 0; i--) {  // 逆序遍历这个顶点的所有邻接点
                    if (adj[vertex][i] == 1 && !visited.contains(i)) {  // 如果邻接点是连通的并且未被访问
                        stack.push(i);                   // 将邻接点压入栈
                    }
                }
            }
        }
        return visited;                                  // 返回访问顺序列表
    }




    //Déterminer si le graphe est connexe
    public boolean estConnexe(){
        ArrayList<Integer> visited = parcoursProfondeur(0);  // 从顶点0开始DFS
        return visited.size() == nbSommets;  // 如果访问的顶点数量等于图中顶点总数，则图连通
    }

    //提取图中边的集合到一个list里
    public List<Triplet> listeAretes(){
        List<Triplet> list = new ArrayList<>();
        for(int i=0;i<this.nbSommets;i++){
            for(int j=0;j<this.nbSommets;j++){
                if(this.poidsA[i][j] != 0){
                    Triplet edge = new Triplet(i, j, this.poidsA[i][j]);
                    list.add(edge);
                }
            }
        }
        return list;
    }

    //根据边的权重，进行排序
    public List<Triplet> aretesTriees(boolean croissant){
        List<Triplet> list = listeAretes();  // 调用之前定义的方法来获取所有边的列表
        if (croissant) {
            Collections.sort(list);  // 升序排序
        } else {
            Collections.sort(list, Collections.reverseOrder());  // 降序排序
        }
        return list;
    }


    //版本一为了使这部分能够与项目的其他部分集成而不会引起错误,直接按照编号递增的顺序排列输出
    public ArrayList<Integer> tsp(int debut) {
        ArrayList<Integer> tour = new ArrayList<>(nomSommets);  // 创建 nomSommets 的一个副本以避免修改原列表
        tour.remove(Integer.valueOf(debut));  // 移除 debut 元素
        Collections.sort(tour);  // 对剩余元素进行排序
        tour.add(0, debut);  // 将 debut 元素添加回列表的开头
        return tour;
    }

}

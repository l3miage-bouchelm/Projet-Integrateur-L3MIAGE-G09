package l3m.cyber.planner.utils;

import java.util.*;

public class Graphe implements Cloneable {
    private int nbSommets;
    private int[][] adj;//Et adj est un matrix utilisé pour déterminer rapidement s'il existe une connexion directe entre deux sommets.临接矩阵
    private Double[][] poidsA;//poidsA est important car il fournit des informations sur le coût de chaque arête. 权重矩阵
    private ArrayList<Integer> nomSommets;//Points non encore ajoutés

    //crée un graphe avec n sommets, nommés 0 à n-1 et aucune arête
    public Graphe(int n){
        this.nbSommets = n;
        this.poidsA = new Double[nbSommets][nbSommets];////////////无权图定义为数值为0的权重矩阵
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                poidsA[i][j] = 0.0;
            }
        }
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
        //ici, on creé la matrice d'adjacence dont l'elements sont 0, parce que la matrice poidsA est null.
    }
    
    //Il génère des noms de sommets par défaut en fonction du nombre de sommets. Cela convient pour une matrice entièrement pondérée
    public Graphe(Double[][] poidsA,int n){
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
        this.poidsA = new Double[nbSommets][nbSommets];
        this.adj = new int[nbSommets][nbSommets];
    }

    //Ce constructeur accepte une matrice de poids des arêtes et une liste de noms de sommets. 
    //Ceci s'applique à la création d'un graphe pondéré.
    //相当于这里，除开第二个参数顶点列表中的顶点，其余的都应在权重矩阵中去掉，从而构成一个新的图，方便调用tsp
    public Graphe(Double[][] poidsA_0, ArrayList<Integer> nomSommets) {
        this.nbSommets = poidsA_0.length;  // 使用originalPoidsA的大小作为新图的顶点数
        this.poidsA = new Double[nbSommets][nbSommets];
        this.adj = new int[nbSommets][nbSommets];
        this.nomSommets = nomSommets;

        // 初始化权重矩阵和邻接矩阵
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                if (nomSommets.contains(i) && nomSommets.contains(j)) {  // 只在nomSommets列表中的顶点之间设置权重
                    this.poidsA[i][j] = poidsA_0[i][j];
                    if (poidsA_0[i][j] != null && poidsA_0[i][j] != 0) {
                        this.adj[i][j] = 1;
                    } else {
                        this.adj[i][j] = 0;
                    }
                } else {
                    this.poidsA[i][j] = 0.0;  // 对不在nomSommets中的顶点，设置为无连接
                    this.adj[i][j] = 0;
                }
            }
        }
    }

    // 打印矩阵的方法
//    public void printMatrices() {
//        System.out.println("Weight Matrix (poidsA):");
//        for (Double[] row : poidsA) {
//            System.out.println(Arrays.toString(row));
//        }
//        System.out.println("\nAdjacency Matrix (adj):");
//        for (int[] row : adj) {
//            System.out.println(Arrays.toString(row));
//        }
//    }


    ///Convient pour créer un graphe non pondéré ou un graphe dont les poids sont traités séparément,
    /// en se concentrant principalement sur la structure du graphe 
    //(c'est-à-dire la connectivité entre les sommets) plutôt que sur les poids des arêtes.
    public Graphe(int[][] adj, ArrayList<Integer> nomSommets){
        this.adj = adj;
        this.nomSommets = nomSommets;
        this.nbSommets = nomSommets.size();
    }


    //将所有的非加权图（权重为null的情况）转化为加权图，所有存在的边默认权重为1
    //确认adj[][]有哪些边存在，但是没有被赋予加权
    public void pondereAretes(){
        for(int i = 0;i<nbSommets;i++){
            for(int j = 0;j<nbSommets;j++){
                if(this.poidsA[i][j] == 0.0 && adj[i][j] == 1){
                    this.poidsA[i][j] = 1.0;
                }
            }
        }

    }



    // Créer et retourner une copie profonde de l'objet Graphe. Cette implémentation garantit que toutes les structures de données - telles que les tableaux et les listes - sont copiées élément par élément, et pas seulement les références.
    @Override
    public Graphe clone() {
        try {
            Graphe cloned = (Graphe) super.clone(); // Appeler la méthode clone() de la classe Object pour faire une copie superficielle, pour diverses propriétés définies.
            // clonage profond des tableaux et des collections
            cloned.adj = this.adj != null ? this.adj.clone() : null;
            if (this.adj != null) {
                cloned.adj = new int[this.adj.length][];
                for (int i = 0; i < this.adj.length; i++) {
                    cloned.adj[i] = this.adj[i].clone(); // copie profonde de chaque sous-tableau
                }
            }
            if (this.poidsA != null) {
                cloned.poidsA = new Double[this.poidsA.length][];
                for (int i = 0; i < this.poidsA.length; i++) {
                    cloned.poidsA[i] = this.poidsA[i].clone(); // Faire une copie profonde de la matrice des poids
                }
            }
            cloned.nomSommets = new ArrayList<>(this.nomSommets); // Faire une copie profonde de la liste nomSommet
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }


    //ajouter l'arete dans le graphe
    ///Considérer la relation entre les graphes dirigés et non dirigés, dans le cas des graphes dirigés un seul paramètre est nécessaire, alors que les graphes non dirigés requièrent l'ajout des deux paramètres.
    //Le modèle ici est qu'il n'y a qu'une seule route entre les deux villes, et il n'est pas nécessaire de considérer les variables supplémentaires causées par la directionnalité des arêtes, l'utilisation d'un graphe non dirigé peut simplifier la complexité du problème.
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

    //Deux cas : 1. ajustement sur la base des données existantes, 2. ajustement sur la base de la configuration ou d'intrants externes
    //La première méthode est utilisée ici pour le moment
    public void ajusterPoids(int i, int j){
        if (adj[i][j] == 1 && poidsA[i][j] != 0) {
            poidsA[i][j] *= 1.1; // En supposant une augmentation de poids de 10
            poidsA[i][j] *= 1.1; // En supposant une augmentation de poids de 10
        }
    }

    public void retirerArete(int i, int j){
        this.adj[i][j] = 0;
        this.adj[j][i] = 0;
        this.poidsA[i][j] = 0.0;
        this.poidsA[j][i] = 0.0;
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
    //Lié à la propriété FIFO (First-In-First-Out, premier entré, premier sorti) des
    // files d'attente, qui est idéale pour parcourir les graphes dans l'ordre hiérarchique (ou en largeur).
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


    //DFS
    //les piles suivent le principe LIFO
    //La recherche en profondeur d'abord (DFS) est souvent mise en œuvre à l'aide de piles car leur nature Last-In-First-Out (LIFO) est bien adaptée à la manière dont la recherche en profondeur d'abord est explorée.
    public ArrayList<Integer> parcoursProfondeur(int debut) {
        ArrayList<Integer> visited = new ArrayList<>();  // 存储访问过的节点
        Stack<Integer> stack = new Stack<>();            // 支持DFS的栈
        stack.push(debut);                               // 将起始节点压入栈

        while (!stack.isEmpty()) {
            int vertex = stack.pop();                    // 弹出栈顶元素
            if (!visited.contains(vertex)) {
                visited.add(vertex);                     // 标记为已访问，在这里防止重复
                // 获取与vertex相连的所有边，按权重排序
                List<Triplet> connectedEdges = new ArrayList<>();
                for (int i = 0; i < nbSommets; i++) {
                    if (adj[vertex][i] == 1 && !visited.contains(i)) {
                        connectedEdges.add(new Triplet(vertex, i, poidsA[vertex][i]));
                    }
                }
                // 按权重排序
                Collections.sort(connectedEdges);
                // 将排序后的节点压入栈
                for (Triplet edge : connectedEdges) {
                    stack.push(edge.getC2());
                }
            }
        }
        // 确保路径是闭环
        ////但是没有必要闭环，毕竟最后输出的时候，每个点只能出现一次。即使不加，在计算路径的时候已经手动加上了
//        if (visited.size() == nbSommets && adj[visited.getLast()][debut] == 1) {
//            visited.add(debut);
//        }
        return visited;  // 返回访问顺序
    }





    //Déterminer si le graphe est connexe
    public boolean estConnexe(){
        ArrayList<Integer> visited = parcoursProfondeur(0);  // commence par le sommet 0
        return visited.size() == nbSommets;  // Le graphe est connecté si le nombre de sommets visités est égal au nombre total de sommets dans le graphe.
    }

    // Extraire l'ensemble des arêtes du graphe dans une liste
    public List<Triplet> listeAretes(){
        List<Triplet> list = new ArrayList<>();
        for(int i=0;i<this.nbSommets;i++){
            for (int j = i + 1; j < this.nbSommets; j++) { // 注意这里的修改，只有当 j > i 时才添加边。避免出现一条边添加两次的情况
                if(this.poidsA[i][j] != 0){
                    Triplet edge = new Triplet(i, j, this.poidsA[i][j]);
                    list.add(edge);
                }
            }
        }
        return list;
    }

    // Selon le poids des arêtes, effectuez un tri
    public List<Triplet> aretesTriees(boolean croissant){
        List<Triplet> list = listeAretes();  // Appel de la méthode précédemment définie pour obtenir la liste de toutes les arêtes
        if (croissant) {
            Collections.sort(list);  // Trier par ordre croissant
        } else {
            Collections.sort(list, Collections.reverseOrder());  // Trier par ordre décroissant
        }
        return list;
    }

    //tsp version2 KruskalInverse
    public Graphe KruskalInverse() {
        Graphe T = this.clone(); // Clone the graph
        List<Triplet> sortedEdges = T.aretesTriees(false); // Get edges sorted by decreasing weight
        UnionFind uf = new UnionFind(T.nbSommets);

        for (Triplet edge : sortedEdges) {
            T.retirerArete(edge.getC1(), edge.getC2());
            if (!T.estConnexe()) {
                T.ajouterArete(edge.getC1(), edge.getC2(), edge.getPoids());
            } else {
                uf.union(edge.getC1(), edge.getC2());
            }
        }
        return T;
    }

    public Graphe Kruskal() {
        // 创建一个空的图 T，其实是复制当前图的结构但不复制边
        //所以这里没有用到克隆
        Graphe T = new Graphe(this.nbSommets);
        List<Triplet> sortedEdges = this.aretesTriees(true); // 将当前图的边按权重升序排序
        UnionFind uf = new UnionFind(this.nbSommets);

        for (Triplet edge : sortedEdges) {
            int u = edge.getC1();
            int v = edge.getC2();
            if (uf.find(u) != uf.find(v)) { // 检查这两个顶点是否已经在同一个连通分量中
                T.ajouterArete(u, v, edge.getPoids()); // 将边添加到 T 中
                uf.union(u, v); // 在 Union-Find 结构中合并这两个顶点的连通分量
            }
            // 一旦加入足够的边就停止，即边数等于顶点数减一
            if (T.listeAretes().size() == this.nbSommets - 1) {
                break;
            }
        }
        return T;
    }

    public ArrayList<Integer> generateHamiltonianCycle(ArrayList<Integer> dfsPath) {
        Set<Integer> visited = new HashSet<>();
        ArrayList<Integer> hamiltonianPath = new ArrayList<>();

        for (int v : dfsPath) {
            if (!visited.contains(v)) {
                visited.add(v);
                hamiltonianPath.add(v);
            }
        }
        hamiltonianPath.add(hamiltonianPath.get(0)); // 回到起点

        return hamiltonianPath;
    }


    public double getPoids(int i, int j) {
        return poidsA[i][j];
    }/////diagramme中没有的方法


    // Version un pour permettre l'intégration de cette partie avec les autres parties du projet sans causer d'erreurs, triez et produisez les sorties dans l'ordre croissant des numéros
    public ArrayList<Integer> tsp(int debut) {
        Graphe minT = this.Kruskal();  // 获取最小生成树
        ArrayList<Integer> dfspath = minT.parcoursProfondeur(debut);
        return generateHamiltonianCycle(dfspath);  // 从指定的起点开始DFS遍历
        //返回的是一个tournne
    }

}

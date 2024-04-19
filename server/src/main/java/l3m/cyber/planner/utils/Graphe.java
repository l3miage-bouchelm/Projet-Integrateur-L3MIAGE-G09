package l3m.cyber.planner.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graphe implements Cloneable {
    private int nbSommets;
    private int[][] adj;//Et adj est utilisé pour déterminer rapidement s'il existe une connexion directe entre deux sommets.
    private double[][] poidsA;//poidsA est important car il fournit des informations sur le coût de chaque arête. 
    private ArrayList<Integer> nomSommets;

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


    //DFS




/* 
    //Déterminer si le graphe est connexe
    public boolean estConnexe(){

    }
*/



}

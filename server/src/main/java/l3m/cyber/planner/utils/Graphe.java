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
    private Double[][] poidsA;//poidsA est important car il fournit des informations sur le coût de chaque arête. 权重矩阵
    private ArrayList<Integer> nomSommets;//Points non encore ajoutés

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
    public Graphe(Double[][] poidsA, ArrayList<Integer> nomSommets){
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


    //将所有的非加权图（权重为null的情况）转化为加权图，所有存在的边默认权重为1
    //确认adj[][]有哪些边存在，但是没有被赋予加权
    public void pondereAretes(){
        for(int i = 0;i<nbSommets;i++){
            for(int j = 0;j<nbSommets;j++){
                if(this.poidsA == null && adj[i][j] == 1){
                    this.poidsA = new Double[nbSommets][nbSommets];
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
        ArrayList<Integer> visited = new ArrayList<>();  // Créez une liste pour stocker les sommets visités
        Stack<Integer> stack = new Stack<>();            // Utilisez une pile pour prendre en charge la recherche en profondeur
        stack.push(debut);                               // Empilez le sommet de départ

        while (!stack.isEmpty()) {                       // Continuez tant que la pile n'est pas vide
            int vertex = stack.pop();                    // Récupérez un sommet de la pile
            if (!visited.contains(vertex)) {             // Si ce sommet n'a pas encore été visité
                visited.add(vertex);                     // Marquez-le comme visité
                for (int i = nbSommets - 1; i >= 0; i--) {  // Parcourez les voisins de ce sommet dans l'ordre inverse
                    if (adj[vertex][i] == 1 && !visited.contains(i)) {  // Si le voisin est connecté et non visité
                        stack.push(i);                   // Empilez le voisin
                    }
                }
            }
        }
        return visited;                                  // Retournez la liste des sommets visités
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
            for(int j=0;j<this.nbSommets;j++){
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


    // Version un pour permettre l'intégration de cette partie avec les autres parties du projet sans causer d'erreurs, triez et produisez les sorties dans l'ordre croissant des numéros
    public ArrayList<Integer> tsp(int debut) {
        ArrayList<Integer> tour = new ArrayList<>(nomSommets);  // Créez une copie de nomSommets pour éviter de modifier la liste originale
        tour.remove(Integer.valueOf(debut));  // Supprimez l'élément debut
        Collections.sort(tour);  // Triez les éléments restants
        tour.add(0, debut);  // Ajoutez l'élément debut au début de la liste
        return tour;
    }

}

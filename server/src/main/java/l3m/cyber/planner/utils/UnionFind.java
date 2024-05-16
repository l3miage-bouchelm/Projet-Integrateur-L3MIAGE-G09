package l3m.cyber.planner.utils;


// Gestion des opérations de recherche (Find) et de fusion (Union)
public class UnionFind {
    private int nb;
    private int[] parent; // Stocke le parent de chaque élément
    private int[] rang; // Stocke la profondeur de l'arbre pour chaque nœud racine

    public UnionFind(int n) {
        parent = new int[n];
        rang = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rang[i] = 0;
        }
    } // Dans le constructeur, le parent de chaque élément pointe initialement vers lui-même, ce qui signifie que chaque élément est d'abord son propre ensemble.
    // Le rang (hauteur de l'arbre) de chaque élément est initialisé à 0.

    public int find(int v) {
        if (v != parent[v]) {
            parent[v] = find(parent[v]);  // Compression de chemin
        }
        return parent[v];
    } // Le paramètre v est l'élément à rechercher, le retour est le parent de l'élément v.

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
    } // Trouver la racine de chaque élément, si les racines sont différentes, les fusionner, choisir celui avec le rang le plus grand comme racine et mettre à jour le parent de l'autre élément.
    // Si les rangs sont égaux, on peut choisir n'importe quel élément comme racine.

    @Override
    public String toString() {
        // Implémentation de la méthode selon les besoins
        return "UnionFind{...}";
    }
}



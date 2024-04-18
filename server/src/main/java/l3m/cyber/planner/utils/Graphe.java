package l3m.cyber.planner.utils;

import java.util.ArrayList;

public class Graphe {
    private int nbSommets;
    private int[][] adj;
    private double[][] poidsA;
    private ArrayList<Integer> nomSommets;

    //crée un graphe avec n sommets, nommés 0 à n-1 et aucune arête
    public Graphe(int n){
        this.nbSommets = n;
        this.poidsA = null;
        this.nomSommets = new ArrayList<>();
        for(int i = 0; i<n; i++){
            this.nomSommets.add(i);
        }
        //ici, on  creé pas la matrice d'adjacence, parce que il n'y aucune arête
    }
    
    //Il génère des noms de sommets par défaut en fonction du nombre de sommets. Cela convient pour une matrice entièrement pondérée
    public Graphe(double[][] poidsA,int n){
        this.nbSommets = n;
        this.poidsA = new double[n][n];
    }

    //
    public Graphe(ArrayList<Integer> nomSommets){
        
    }
}

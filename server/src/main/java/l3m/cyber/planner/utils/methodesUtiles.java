package l3m.cyber.planner.utils;


import java.util.ArrayList;

public class methodesUtiles {

    public static int[] CasernePlusProche( int[] ListeCasernes,  int k, int nbElm, double[][] distances){

        double d ;
        int c;
        int[] LC = new int[nbElm];

        for(int i=0; i < nbElm; i++ ){
            c=0;
            d = Double.MAX_VALUE;
            for (int j=0; j<k; j++){
                if( d > distances[i][j]) {
                    d = distances[i][j];
                    c = j;
                }
            }
            LC[i] = c;
        }

        return LC;
    }


    // Trouve le point dont la distance entre lui et sa caserne est la plus grande
    public static int PlusEloigneDeCaserneRef(int[] ListeCasernes , int nbElm, double[][] distances){
        int c=0;
        double d = Double.MIN_VALUE;
        for(int i=0; i < nbElm; i++ ){
            if( d < distances[i][ ListeCasernes[i]] ){
                d = distances[i][ ListeCasernes[i]];
                c = ListeCasernes[i];
            }
        }
       return c;
    }


    // retrouve tous les sommets dont la caserne de reférence est k pour en faire une partion et les met dans une arraylist
    private static  ArrayList<Integer> SommetsPartieK(int k, int[] AffectationCasernes, int nbElm){

        ArrayList<Integer> p= new ArrayList<>();
        for(int j=0; j < nbElm; j++ ){
            if( AffectationCasernes[j] == k){ p.add(j); }
        }
        return p;
    }

    //RemplireParties pour remplire une array avec les différentes partions
    public static ArrayList<ArrayList<Integer>> RemplireParties(int[] AffectationCasernes, int nbElm, int k){

        ArrayList<ArrayList<Integer>> prts = new ArrayList<>();
        for(int i=0; i < k; i++ ){

            ArrayList<Integer> p= SommetsPartieK(i, AffectationCasernes, nbElm);
            prts.add(p);        // un ajoute à l'array la partion dans les point ont pour caserne référente le point i
        }
        return prts;
    }



}


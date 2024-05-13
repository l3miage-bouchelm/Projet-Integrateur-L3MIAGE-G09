package l3m.cyber.planner.utils;


import java.util.ArrayList;

public class methodesUtiles {

    public static int[] CasernePlusProche( int[] ListeCasernes,  int nbrCasernesEnCours, int nbElm, Double[][] distances){

        double dmin ;
        int c;
        int[] LC = new int[nbElm];

        for(int i=0; i < nbElm; i++ ){
            c=0;
            dmin = Double.MAX_VALUE;

            for( int ki: ListeCasernes){
                if( dmin > distances[i][ki]) {
                    dmin = distances[i][ki];
                    c = ki;
                }
            }
            LC[i] = c;
        }

        return LC;
    }


    // Trouve le point dont la distance entre lui et sa caserne est la plus grande
    public static int PlusEloigneDeCaserneRef(int[] ListeCasernes , int nbElm, Double[][] distances){
        int c=0;
        double dmax = 0;
        for(int i=0; i < nbElm; i++ ){
            if( dmax < distances[i][ ListeCasernes[i]] ){
                dmax = distances[i][ ListeCasernes[i]];
                c = i;
            }
        }
       return c;
    }


    // retrouve tous les sommets dont la caserne de reférence est k pour en faire une partion et les met dans une arraylist
    private static  ArrayList<Integer> SommetsPartieK(int start, int numCaserne, int[] AffectationCasernes, int[] ListeCasernes, int nbElm){

        ArrayList<Integer> p= new ArrayList<>();
        if( numCaserne != 0  ){p.add(start);} // au début de chaque tournée, on place le sommet start
        else if ( numCaserne == 0 && nbElm==1 ){ p.add(start); } // si on a un seul sommet dans le graph


        for(int j=0; j < nbElm; j++ ){

            if( nbElm!=1 && AffectationCasernes[j] == ListeCasernes[numCaserne]){
                if(j==start){
                    p.add(0,j);
                }else {
                    p.add(j);
                }

            } // si on a plus d'un sommet

        }
        return p;
    }

    //RemplireParties pour remplire une array avec les différentes partions
    public static ArrayList<ArrayList<Integer>> RemplireParties(int start, int[] AffectationCasernes, int[] ListeCasernes, int nbElm, int k){

        ArrayList<ArrayList<Integer>> prts = new ArrayList<>();
        for(int numCaserne=0; numCaserne < k; numCaserne++ ){

            ArrayList<Integer> p= SommetsPartieK(start, numCaserne, AffectationCasernes, ListeCasernes, nbElm);
            prts.add(p);        // on ajoute à l'array la partion dont les points ont pour caserne référente le point i
        }
        return prts;
    }



}


package l3m.cyber.planner.utils;

import com.sun.source.tree.WhileLoopTree;

import java.util.ArrayList;




public class PartitionKCentre extends Partition {
    
    public PartitionKCentre(int n, int k){
        super(n, k);
    }
    /***********/public PartitionKCentre(int n, int k, int elemspecial){
        super(n, k, elemspecial);
    }

    @Override
    /*********/ public void partitionne(Double[][] distances){


        int k = this.k;
        int[] ListeCasernes = new int[k]; // liste des Casernes déjà sélectionnées
        int CaserneCourante = elemSpecial; //  nouvelle Caserne
        int count = 0; // nbre de caserneen cours déjà choisies

        int[] AffectationCasernes = new int[nbElem] ; // sert pour affecter à chaque point sa caserne



        while ( count < this.k){
            ListeCasernes[count]=CaserneCourante;

            AffectationCasernes = methodesUtiles.CasernePlusProche( ListeCasernes, count+1, nbElem, distances);
            // int[elemSpecial] CasernePlusProche(int[] ListeCasernes, int nbrCasernesEnCours, int nbElm, double[][] distances );
            // cette fonction renvoie une liste qui affecte à chaque sommet, sa caserne la plus proche

            int SommetDefavorise = methodesUtiles.PlusEloigneDeCaserneRef(AffectationCasernes, nbElem, distances);
            // int PlusEloigneDeCaserneRef( int[] ListeCasernes,int nbElm, double[][] distances);
            // renvoie le point le plus défavorisé
            CaserneCourante = SommetDefavorise;
            count++;
        }


        this.parties = methodesUtiles.RemplireParties(elemSpecial, AffectationCasernes, ListeCasernes, nbElem, k);
        // ArrayList< ArrayList<Integer>> RemplireParties(int start, int AffectationCasernes[elemSpecial], int[] ListeCasernes, int nbElem, int nbrDePartions)








        return;
    }








    @Override
    /*********/ public void AfficherPartition(Double[][] distances){


        int k = this.k;
        int[] ListeCasernes = new int[k]; // liste des Casernes déjà sélectionnées
        int CaserneCourante = elemSpecial; //  nouvelle Caserne
        int count = 0; // nbre de caserneen cours déjà choisies

        int[] AffectationCasernes = new int[nbElem] ; // sert pour affecter à chaque point sa caserne



        while ( count < this.k){
            ListeCasernes[count]=CaserneCourante;

            AffectationCasernes = methodesUtiles.CasernePlusProche( ListeCasernes, count+1, nbElem, distances);
            // int[elemSpecial] CasernePlusProche(int[] ListeCasernes, int nbrCasernesEnCours, int nbElm, double[][] distances );
            // cette fonction renvoie une liste qui affecte à chaque sommet, sa caserne la plus proche

            int SommetDefavorise = methodesUtiles.PlusEloigneDeCaserneRef(AffectationCasernes, nbElem, distances);
            // int PlusEloigneDeCaserneRef( int[] ListeCasernes,int nbElm, double[][] distances);
            // renvoie le point le plus défavorisé
            CaserneCourante = SommetDefavorise;
            count++;
        }

        for(int i =0; i< distances.length; i++){
            System.out.println("sommet "+i+" , sa caserne: "+AffectationCasernes[i]);
            System.out.println("********************************* \n");
        }

        System.out.println(" \n \n****** Liste des Casernes***************** \n");

        for(int i =0; i< ListeCasernes.length; i++){
            System.out.print(" "+i+", "+ListeCasernes[i]);
            System.out.println("********************************* \n");
        }

        return;
    }

}
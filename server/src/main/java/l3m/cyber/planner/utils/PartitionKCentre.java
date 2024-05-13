package l3m.cyber.planner.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class PartitionKCentre extends Partition {
    //所以是先找到k个分区中心点，然后进行分区
    public PartitionKCentre(int n, int k){
        super(n, k);
    }
    /***********/public PartitionKCentre(int n, int k, int elemspecial){
        super(n, k, elemspecial);
    }

    @Override
    /*********/ public void partitionne(Double[][] distances){


        int k = this.k;
        ArrayList<Integer> listeCasernes = new ArrayList<>();
        int CaserneCourante = elemSpecial; //  nouvelle Caserne
        int count = 0; // nbre de caserneen cours déjà choisies

        int[] AffectationCasernes = new int[nbElem] ; // sert pour affecter à chaque point sa caserne



        while (listeCasernes.size() < k) {
            if (!listeCasernes.contains(CaserneCourante)) {
                listeCasernes.add(CaserneCourante);
            }
            System.out.println("Iteration " + listeCasernes.size() + ": Current center is " + CaserneCourante);

            AffectationCasernes = methodesUtiles.CasernePlusProche(listeCasernes.stream().mapToInt(i->i).toArray(), listeCasernes.size(), nbElem, distances);
            System.out.println("Centers after update: " + listeCasernes);
            System.out.println("Assignments: " + Arrays.toString(AffectationCasernes));

            int sommetDefavorise = methodesUtiles.PlusEloigneDeCaserneRef(AffectationCasernes, nbElem, distances);
            System.out.println("Most disadvantaged vertex is " + sommetDefavorise);

            CaserneCourante = sommetDefavorise;
        }


        this.parties = methodesUtiles.RemplireParties(elemSpecial, AffectationCasernes, listeCasernes.stream().mapToInt(i->i).toArray(), nbElem, k);
        // ArrayList< ArrayList<Integer>> RemplireParties(int start, int AffectationCasernes[elemSpecial], int[] ListeCasernes, int nbElem, int nbrDePartions)


        return;
    }


}
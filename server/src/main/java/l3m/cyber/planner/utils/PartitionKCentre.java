package l3m.cyber.planner.utils;

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


}
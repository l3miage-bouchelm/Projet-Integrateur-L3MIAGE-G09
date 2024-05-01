package l3m.cyber.planner.utils;

import java.util.ArrayList;

public abstract class Partition {
    protected int nbElem;
    protected int k;//nombre de liveurs
    protected int elemSpecial;//l'inindice d'entrepot
    protected ArrayList<Integer> elems;
    protected  ArrayList<ArrayList<Integer>> parties;//Correspond à un itinéraire de livraison d'un livreur
    //l'inindice d'entrepot apparait qu'une fois, au debut de parties[i]

    public abstract void partitionne(Double[][] distances);


    Partition(ArrayList<Integer> elems, int k, int elemSpecial){
        this.nbElem = elems.size();
        this.k = k;
        this.elemSpecial = elemSpecial;
        this.elems = elems;
        partitionVide();
    }

    Partition(int n, int k, int elemSpecial){
        this.nbElem = n;
        this.k = k;
        this.elemSpecial = elemSpecial;
        this.elems = new ArrayList<Integer>();
        for (int i = 0; i < nbElem; i++) {
            if (i != elemSpecial) {
                elems.add(i);
            }
        }
        partitionVide();
    }

    Partition(int n, int k){
        this.nbElem = n;
        this.elemSpecial = 0;
        this.k = k;
        this.elems = new ArrayList<Integer>();
        for (int i = 0; i < nbElem; i++) {
            if (i != elemSpecial) {
                elems.add(i);
            }
        }
        partitionVide();

    }


    public void partitionVide(){
        this.parties = new ArrayList<>();
        for(int i = 0;i<k;i++){
            ArrayList<Integer> chemin_liveur = new ArrayList<>();
            chemin_liveur.add(this.elemSpecial);
            parties.add(chemin_liveur);
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Partition details:\n");
        for (int i = 0; i < k; i++) {
            builder.append("Partie ").append(i + 1).append(": ");
            ArrayList<Integer> partie = parties.get(i);
            for (int j = 0; j < partie.size(); j++) {
                builder.append(partie.get(j));
                if (j < partie.size() - 1) {
                    builder.append(", ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }


    public ArrayList<Integer> getPartie(int i){
        if (i < 0 || i >= k) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + parties.size());
        }
        return parties.get(i);
    }
}

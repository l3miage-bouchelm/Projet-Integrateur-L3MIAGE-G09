package l3m.cyber.planner.utils;
import java.util.ArrayList;
import java.util.Random;

public class PartitionAlea extends Partition {
    public PartitionAlea(int n,int k) {
        super(n, k);
    }

    public PartitionAlea(int n, int k, int elemSpecial){
        super(n, k, elemSpecial);
    }

    public PartitionAlea(ArrayList<Integer>elems,int k,int elemSpecial){
        super(elems,k,elemSpecial);
    }


    @Override
    public void partitionne(Double[][] distances) {
        Random random = new Random();
        if (this.nbElem == 0 || this.elems == null || this.parties == null) {
            // Ajouter un journal ou lever une exception indiquant que le partitionnement n'a pas pu être effectué parce que la liste d'entrée est vide ou n'a pas été initialisée.
            System.out.println("PartitionAlea: No elements to partition or not initialized");
            throw new IllegalStateException("Elements list is empty or not initialized.");
        }
        
    
        for (int i = 0; i < this.elems.size(); i++) {
            if (elems.get(i) == this.elemSpecial) {//Le jugement porte ici sur le troisième cas, où elems contient un élément de speciale.
                continue; // Sauter l'élément de special
            }
            int randomPartie = random.nextInt(this.k);
            parties.get(randomPartie).add(elems.get(i));
            System.out.println("Element " + elems.get(i) + " assigned to partie " + randomPartie);

        }
        System.out.println("PartitionAlea: Partitioning complete.");

    }








}

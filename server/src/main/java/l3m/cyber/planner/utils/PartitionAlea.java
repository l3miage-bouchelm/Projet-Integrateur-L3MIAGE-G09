package l3m.cyber.planner.utils;
import java.util.Random;

public class PartitionAlea extends Partition {
    public PartitionAlea(int n,int k) {
        super(n, k);
    }


    @Override
    public void partitionne(double[][] distances) {
        Random random = new Random();
        for(int i = 0; i<this.nbElem;i++){
            if (elems.get(i) == 0) {
                continue; // si l'element est 0, on saute dans la prochain iteration
            }
            int randomPartie = random.nextInt(this.k);
            parties.get(randomPartie).add(elems.get(i));
        }
    }
}

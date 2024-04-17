package l3m.cyber.planner.utils;
import java.util.ArrayList;
import java.util.Random;

public class PartitionAlea extends Partition {
    public PartitionAlea(ArrayList<Integer> elems, int k, int elemSpecial) {
        super(elems, k, elemSpecial);
    }


    @Override
    public void partitionne(double[][] distances) {
        elems.remove(elemSpecial);
        Random random = new Random();
        for(int i = 0; i<k;i++){
            int randomPartie = random.nextInt(this.k);
            parties.get(randomPartie).add(elems.get(i));
        }
    }
}

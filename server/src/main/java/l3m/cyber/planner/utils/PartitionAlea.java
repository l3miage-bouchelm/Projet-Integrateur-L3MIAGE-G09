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
            // 添加日志或抛出异常，表明无法进行分区因为输入列表为空或未初始化
            throw new IllegalStateException("Elements list is empty or not initialized.");
        }
        
    
        for (int i = 0; i < this.elems.size(); i++) {
            if (elems.get(i) == this.elemSpecial) {//这里的判断针对于第三种情况，第三种情况里elems包含仓库元素
                continue; // 跳过仓库元素
            }
            int randomPartie = random.nextInt(this.k);
            parties.get(randomPartie).add(elems.get(i));
        }
    }








}

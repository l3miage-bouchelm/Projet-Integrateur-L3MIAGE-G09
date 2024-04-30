package l3m.cyber.planner.utils;

import java.util.ArrayList;

import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.responses.PlannerResult;


public class Planner{
    Double[][] distances;
    int k;
    int debut;
    Partition p;
    ArrayList<ArrayList<Integer>> tournees;
    ArrayList<Double> longTournees;

    //这个构造器用于高层次的封装，与外部系统联系时比较有用
    public Planner(PlannerParameter param){
        this.distances = param.matrix();
        this.k = param.k();
        this.debut = param.start();
        this.tournees = new ArrayList<>();
        this.longTournees = new ArrayList<>();
         //需要从距离矩阵里提取有用信息作为构造器的参数
        ArrayList<Integer> elems = new ArrayList<>();
        for(int i=0; i<distances.length;i++){
            elems.add(i);//对于partitionalea构造器来说，需要一个包含所有没被分配顶点的list
        }
         //这里暂时使用了随机分类
        this.p = new PartitionAlea(elems,k,debut);//根据需求，选择对应的构造器进行初始化
        this.p = new PartitionKCentre( distances.length, k);
         //如果要使用kcentre方法进行分配则需要在这里初始化partitionkcentre的构造器
    }

    //这个构造器用于直接测试
    /*public Planner(Double[][] distances, int k, int debut){
        this.debut = debut;
        this.distances = distances;
        this.k = k;
        this.tournees = new ArrayList<>();
        this.longTournees = new ArrayList<>();
        //需要从距离矩阵里提取有用信息作为构造器的参数
        ArrayList<Integer> elems = new ArrayList<>();
        for(int i=0; i<distances.length;i++){
            elems.add(i);//对于partitionalea构造器来说，需要一个包含所有没被分配顶点的list
        }
        //这里暂时使用了随机分类
        this.p = new PartitionAlea(elems,k,debut);//根据需求，选择对应的构造器进行初始化
        //如果要使用kcentre方法进行分配则需要在这里初始化partitionkcentre的构造器
    }*/

    

    public ArrayList<ArrayList<Integer>> getTournees(){
        return this.tournees;
    }

    public void divise(){
        p.partitionne(distances);//ici,on utilise la method de partitionAlea
    }

    public ArrayList<Integer> calculeUneTournee(ArrayList<Integer> selec) {
        // 计算单个路线
        //实际上是对p中的每一个partie（路线）进行一个优化，使用graphe类中的内容
        Graphe graphe = new Graphe(selec);
        ArrayList<Integer> uneTournee = graphe.tsp(debut);
        return uneTournee;
    }

    public void calculeTournees() {
        divise();
        //应该使用for循环逐一调用calculeUneTournee
        System.out.println("Planner: Calculating tournees...");
        for(int i = 0; i < p.parties.size(); i++) {
            ArrayList<Integer> tournee = calculeUneTournee(p.getPartie(i));
            tournees.add(tournee);
            System.out.println("Planner: Processing partie " + i + " - Elements: " + p.getPartie(i));
        }
    }

    public void calculeLongTournees() {
        longTournees.clear();  // 清空以前的计算结果
    
        for (ArrayList<Integer> tournee : this.tournees) {
            double length = 0.0;
            if (tournee.size() > 1) {
                // 计算连续地点之间的距离
                for (int j = 0; j < tournee.size() - 1; j++) {
                    int start = tournee.get(j);
                    int end = tournee.get(j + 1);
                    double segmentLength = distances[start][end];
                    System.out.println("Calculating from " + start + " to " + end + ": " + segmentLength);
                    length += segmentLength;
                }
                // 添加从最后一个地点返回到起始点（仓库）的距离
                int lastPoint = tournee.get(tournee.size() - 1);
                int returnToPoint = tournee.get(0);
                double returnLength = distances[lastPoint][returnToPoint];
                System.out.println("Returning from " + lastPoint + " to " + returnToPoint + ": " + returnLength);
                length += returnLength;
            } else {
                // 如果路线中只有仓库点，长度为0
                System.out.println("Only the depot is in the tournee, no travel needed.");
            }
            longTournees.add(length);
            System.out.println("Length calculated for tournee: " + length);
        }
    }
    
    public String toString() {
        // 返回类的字符串表示
        return "Planner with " + k + " routes";
    }
    
    
    public PlannerResult result(){
        return new PlannerResult(tournees, longTournees);
    }
}
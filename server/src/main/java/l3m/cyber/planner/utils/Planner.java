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

    // Ce constructeur est utilisé pour l'encapsulation de haut niveau, lorsqu'il s'agit de contacter des systèmes externes.
    public Planner(PlannerParameter param){
        this.distances = param.matrix();
        this.k = param.k();
        this.debut = param.start();
        this.tournees = new ArrayList<>();
        this.longTournees = new ArrayList<>();
         //La méthode de classification aléatoire est utilisée ici pour l'instant
        //this.p = new PartitionAlea(distances.length,k,debut);///choisir le constructeur correspondant pour l'initialiser si nécessaire
        this.p = new PartitionKCentre( distances.length, k,debut);
        // le constructeur partitionkcentre .
    }

    // Ce constructeur est utilisé pour les tests directs
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
        //Calcul d'itinéraires individuels
        //en fait une optimisation pour chaque partie (route) de p, en utilisant ce qui se trouve dans la classe de graphe
        Graphe graphe = new Graphe(selec);
        ArrayList<Integer> uneTournee = graphe.tsp(debut);
        return uneTournee;
    }

    public void calculeTournees() {
        System.out.println(p.parties);
        divise();
        System.out.println(p.parties);
        System.out.println("Planner: Calcule tournees...");
        for(int i = 0; i < p.parties.size(); i++) {
            ArrayList<Integer> tournee = calculeUneTournee(p.getPartie(i));
            tournees.add(tournee);
            System.out.println("Planner: Traitement partie " + i + " - Elements: " + p.getPartie(i));
        }
        System.out.println(tournees);

    }

    public void calculeLongTournees() {
        longTournees.clear();  // Videz les résultats des calculs précédents

        for (ArrayList<Integer> tournee : this.tournees) {
            double length = 0.0;
            if (tournee.size() > 1) {
                // Calculez la distance entre les points consécutifs
                for (int j = 0; j < tournee.size() - 1; j++) {
                    int start = tournee.get(j);
                    int end = tournee.get(j + 1);
                    double segmentLength = distances[start][end];
                    System.out.println("Calcul de " + start + " à " + end + " : " + segmentLength);
                    length += segmentLength;
                }
                // Ajoutez la distance de retour du dernier point au point de départ (dépôt)
                int lastPoint = tournee.get(tournee.size() - 1);
                int returnToPoint = tournee.get(0);
                double returnLength = distances[lastPoint][returnToPoint];
                System.out.println("Retour de " + lastPoint + " à " + returnToPoint + " : " + returnLength);
                length += returnLength;
            } else {
                // Si l'itinéraire ne contient que le point de dépôt, la longueur est 0
                System.out.println("Seul le dépôt est dans la tournée, aucun voyage nécessaire.");
            }
            longTournees.add(length);
            System.out.println("Longueur calculée pour la tournée : " + length);
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
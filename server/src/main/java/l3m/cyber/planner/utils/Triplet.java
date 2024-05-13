package l3m.cyber.planner.utils;

public class Triplet implements Comparable<Triplet>{
    private int c1;
    private int c2;
    private double poids;

    public Triplet(int a, int b, double poids){
        this.c1 = a;
        this.c2 = b;
        this.poids = poids;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }

    public double getPoids() {

        return poids;
    }

    @Override
    public String toString() {
        return "(" + c1 + ", " + c2 + ", " + poids + ")";
    }

    @Override
    public int compareTo(Triplet other) {
        return Double.compare(this.poids, other.poids);
    }
}

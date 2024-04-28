package l3m.cyber.planner.utils;
import java.util.ArrayList;


public class Auxiliaire {
    public static ArrayList<Integer> integerList(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return list;
    }

    public static boolean estCarree(Object[][] matrice) {
        int rows = matrice.length;
        for (Object[] row : matrice) {
            if (row == null || row.length != rows) {
                return false;
            }
        }
        return true;
    }

    public static boolean estCarreeSym(Object[][] matrice) {
        if (!estCarree(matrice)) {
            return false;
        }
        int n = matrice.length;//返回的是行数
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (!matrice[i][j].equals(matrice[j][i])) {
                    return false;
                }
            }
        }
        return true;
    }
}

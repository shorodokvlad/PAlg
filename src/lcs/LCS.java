package lcs;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LCS {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lcs/lcs/in.txt";
        String caleFisierOut = "data/lcs/lcs/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            while (sc.hasNext()){
                int n = sc.nextInt();
                int[] a = new int[n];
                for (int i = 0; i < n; i++) {
                    a[i] = sc.nextInt();
                }


                int m = sc.nextInt();
                int[] b = new int[m];
                for (int j = 0; j < m; j++) {
                    b[j] = sc.nextInt();
                }



                int[][] lung = LCS(a, n, b, m);
                tiparesteLCS(a, b, lung);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    static int[][] LCS(int[] a, int n, int[] b, int m) {
        int[][] lung = new int[n][m];

        if (a[0] == b[0]) {
            lung[0][0] = 1;
        } else {
            lung[0][0] = 0;
        }

        for (int i = 1; i < n; i++) {
            if (a[i] == b[0]) {
                lung[i][0] = 1;
            } else {
                lung[i][0] = lung[i - 1][0];
            }
        }

        for (int j = 1; j < m; j++) {
            if (a[0] == b[j]) {
                lung[0][j] = 1;
            } else {
                lung[0][j] = lung[0][j - 1];
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int diagonala;
                if (a[i] == b[j]) {
                    diagonala = lung[i - 1][j - 1] + 1;
                } else {
                    diagonala = 0;
                }
                lung[i][j] = Math.max(diagonala, Math.max(lung[i - 1][j], lung[i][j - 1]));
            }
        }

        return lung;
    }

    public static void tiparesteLCS(int[] a, int[] b, int[][] lung) {
        List<Integer> subsecventa = new ArrayList<>();
        int i = a.length - 1;
        int j = b.length - 1;

        while (i >= 0 && j >= 0) {
            if (a[i] == b[j]) {
                subsecventa.add(a[i]);
                i--;
                j--;
            } else if (i >= 1 && lung[i - 1][j] == lung[i][j]) {
                i--;
            } else if (j >= 1 && lung[i][j - 1] == lung[i][j]) {
                j--;
            } else {
                break;
            }
        }

        Collections.reverse(subsecventa);

        System.out.println(subsecventa.size());

        for (int x : subsecventa) {
            System.out.print(x + " ");
        }
    }

}
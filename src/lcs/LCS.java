package lcs;

import java.io.File;
import java.io.PrintWriter;
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
                TiparesteLCS(a, n, b, m, lung);

                //CelMaiLungPalindrom(a, a.length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CelMaiLungPalindrom(int[] a, int n) {
        int[] a2 = new int[n];

        for (int i = 0; i < n - 1; i++) {
            a2[i] = a[n - 1 - i];
        }

        int[][] lung = LCS(a, n, a2, n);
        TiparesteLCS(a, n, a2, n, lung);
    }

    static int[][] LCS_Ternar(int[] a, int n, int[] b, int m) {
        int[][] lung = new int[n][m];

        // rezolvăm subproblemele simple - prima celulă
        lung[0][0] = (a[0] == b[0]) ? 1 : 0;

        // rezolvăm subproblemele simple - prima coloană
        for (int i = 1; i < n; i++) {
            if (a[i] == b[0]) {
                lung[i][0] = 1;
            } else {
                lung[i][0] = lung[i - 1][0];
            }
        }

        // rezolvăm subproblemele simple - primul rând
        for (int j = 1; j < m; j++) {
            if (a[0] == b[j]) {
                lung[0][j] = 1;
            } else {
                lung[0][j] = lung[0][j - 1];
            }
        }

        // completăm matricea lung
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int diagonala = (a[i] == b[j]) ? lung[i - 1][j - 1] + 1 : 0;
                lung[i][j] = Math.max(diagonala, Math.max(lung[i - 1][j], lung[i][j - 1]));
            }
        }

        return lung;
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

    static void TiparesteLCS(int[] a, int n, int[] b, int m, int[][] lung) {
        printWriter.println("Lungimea maxima: " + lung[n - 1][m - 1]);
        printWriter.print("Subsecventa este: ");

        int[] rezultat = new int[lung[n - 1][m - 1]];
        int idx = rezultat.length - 1;

        int i = n - 1;
        int j = m - 1;

        while (i >= 0 && j >= 0) {
            if (a[i] == b[j]) {
                rezultat[idx--] = a[i];
                i--;
                j--;
            } else {
                if (i - 1 >= 0 && lung[i][j] == lung[i - 1][j]) {
                    i--;
                } else {
                    j--;
                }
            }
        }

        for (int k = 0; k < rezultat.length; k++) {
            printWriter.print(rezultat[k]);
            if (k < rezultat.length - 1) {
                printWriter.print(" ");
            }
        }
        printWriter.println();
    }

}
package lcs;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class TurnCuburi {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn  = "data/lcs/cuburi/in.txt";
        String caleFisierOut = "data/lcs/cuburi/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            while (sc.hasNextInt()) {
                int n = sc.nextInt();
                int m = sc.nextInt();

                int[] a = new int[n];
                for (int i = 0; i < n; i++) a[i] = sc.nextInt();


                int[] b = new int[m];
                for (int j = 0; j < m; j++) b[j] = sc.nextInt();

                int[][] lung = LCS(a, n, b, m);
                TiparesteTurnuri(a, n, b, m, lung);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int[][] LCS(int[] a, int n, int[] b, int m) {
        int[][] lung = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a[i - 1] == b[j - 1]) {
                    lung[i][j] = lung[i - 1][j - 1] + 1;
                } else {
                    lung[i][j] = Math.max(lung[i - 1][j], lung[i][j - 1]);
                }
            }
        }
        return lung;
    }

    static void TiparesteTurnuri(int[] a, int n, int[] b, int m, int[][] lung) {
        int lcsLen = lung[n][m];
        int totalEliminate = (n - lcsLen) + (m - lcsLen);
        printWriter.println(totalEliminate);

        List<Integer> pozA = new ArrayList<>();
        List<Integer> pozB = new ArrayList<>();

        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (a[i - 1] == b[j - 1]) {
                pozA.add(i);
                pozB.add(j);
                i--;
                j--;
            } else if (lung[i - 1][j] >= lung[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        boolean[] ramaneA = new boolean[n + 1]; // 1-based
        boolean[] ramaneB = new boolean[m + 1];

        for (int idx : pozA) ramaneA[idx] = true;
        for (int idx : pozB) ramaneB[idx] = true;

        StringBuilder sbA = new StringBuilder();
        for (int k = 1; k <= n; k++) {
            if (!ramaneA[k]) {
                if (sbA.length() > 0) sbA.append(" ");
                sbA.append(k - 1);
            }
        }
        printWriter.println(sbA);

        StringBuilder sbB = new StringBuilder();
        for (int k = 1; k <= m; k++) {
            if (!ramaneB[k]) {
                if (sbB.length() > 0) sbB.append(" ");
                sbB.append(k - 1);
            }
        }
        printWriter.println(sbB);
    }
}
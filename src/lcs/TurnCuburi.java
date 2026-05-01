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

    // -------------------------------------------------------
    // Construiește matricea LCS (indexare 1-based în matrice,
    // 0-based în array-uri → lung[i][j] = LCS(a[0..i-1], b[0..j-1]))
    // -------------------------------------------------------
    static int[][] LCS(int[] a, int n, int[] b, int m) {
        int[][] lung = new int[n + 1][m + 1];
        // lung[0][*] = 0, lung[*][0] = 0 — deja inițializate

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a[i - 1] == b[j - 1]) {
                    // elementele se potrivesc → extindem LCS-ul diagonal
                    lung[i][j] = lung[i - 1][j - 1] + 1;
                } else {
                    // luăm maximul dintre "sărim din a" sau "sărim din b"
                    lung[i][j] = Math.max(lung[i - 1][j], lung[i][j - 1]);
                }
            }
        }
        return lung;
    }

    // -------------------------------------------------------
    // Reconstruiește LCS și calculează indicii cuburilor eliminate
    // -------------------------------------------------------
    static void TiparesteTurnuri(int[] a, int n, int[] b, int m, int[][] lung) {
        int lcsLen = lung[n][m];
        int totalEliminate = (n - lcsLen) + (m - lcsLen);
        printWriter.println(totalEliminate);

        // Reconstruim pozițiile (1-based) din LCS mergând înapoi
        List<Integer> pozA = new ArrayList<>(); // indici din a care RĂMÂN
        List<Integer> pozB = new ArrayList<>(); // indici din b care RĂMÂN

        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (a[i - 1] == b[j - 1]) {
                // element comun → face parte din LCS
                pozA.add(i); // index 1-based în a
                pozB.add(j); // index 1-based în b
                i--;
                j--;
            } else if (lung[i - 1][j] >= lung[i][j - 1]) {
                // venim de sus → a[i-1] se elimină
                i--;
            } else {
                // venim din stânga → b[j-1] se elimină
                j--;
            }
        }

        // pozA și pozB sunt în ordine inversă → construim setul celor care rămân
        boolean[] ramaneA = new boolean[n + 1]; // 1-based
        boolean[] ramaneB = new boolean[m + 1];

        for (int idx : pozA) ramaneA[idx] = true;
        for (int idx : pozB) ramaneB[idx] = true;

        // Tipărim indicii (1-based) ai cuburilor ELIMINATE din a
        StringBuilder sbA = new StringBuilder();
        for (int k = 1; k <= n; k++) {
            if (!ramaneA[k]) {
                if (sbA.length() > 0) sbA.append(" ");
                sbA.append(k - 1);  // 0-based
            }
        }
        printWriter.println(sbA);

        // Tipărim indicii (0-based) ai cuburilor ELIMINATE din b
        StringBuilder sbB = new StringBuilder();
        for (int k = 1; k <= m; k++) {
            if (!ramaneB[k]) {
                if (sbB.length() > 0) sbB.append(" ");
                sbB.append(k - 1);  // 0-based
            }
        }
        printWriter.println(sbB);
    }
}
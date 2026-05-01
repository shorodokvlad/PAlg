package lcs;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Vocabular {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lcs/vocabular/in.txt";
        String caleFisierOut = "data/lcs/vocabular/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;


            int n = sc.nextInt();
            String[] dictionar = new String[n];
            for (int i = 0; i < n; i++) {
                dictionar[i] = sc.next();
            }

            int m = sc.nextInt();
            for (int k = 0; k < m; k++) {
                String cuvant = sc.next();

                // găsim cuvântul din dicționar cu LCS maxim față de cuvântul utilizatorului
                String celMaiBun = dictionar[0];
                int lcsMax = lungimeLCS(cuvant, dictionar[0]);

                for (int i = 1; i < n; i++) {
                    int lcsActual = lungimeLCS(cuvant, dictionar[i]);
                    if (lcsActual > lcsMax) {
                        lcsMax = lcsActual;
                        celMaiBun = dictionar[i];
                    }
                }

                printWriter.println(celMaiBun);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // returnează doar lungimea LCS dintre două cuvinte (ca char[])
    static int lungimeLCS(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] lung = new int[n][m];

        // prima celulă
        lung[0][0] = (s1.charAt(0) == s2.charAt(0)) ? 1 : 0;

        // prima coloană
        for (int i = 1; i < n; i++) {
            if (s1.charAt(i) == s2.charAt(0)) {
                lung[i][0] = 1;
            } else {
                lung[i][0] = lung[i - 1][0];
            }
        }

        // primul rând
        for (int j = 1; j < m; j++) {
            if (s1.charAt(0) == s2.charAt(j)) {
                lung[0][j] = 1;
            } else {
                lung[0][j] = lung[0][j - 1];
            }
        }

        // completăm matricea
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    lung[i][j] = lung[i - 1][j - 1] + 1;
                } else {
                    lung[i][j] = Math.max(lung[i - 1][j], lung[i][j - 1]);
                }
            }
        }

        return lung[n - 1][m - 1];
    }
}
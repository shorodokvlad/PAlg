package lcs;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Excursii {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lcs/excursii/in.txt";
        String caleFisierOut = "data/lcs/excursii/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            int T = Integer.parseInt(sc.nextLine().trim());

            for (int t = 0; t < T; t++) {
                int n = Integer.parseInt(sc.nextLine().trim());

                List<List<String>> siruri = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    String linie = sc.nextLine().trim();
                    List<String> orase = Arrays.asList(linie.split("\\s+"));
                    siruri.add(orase);
                }

                List<String> lcsCorent = siruri.get(0);

                for (int i = 1; i < n; i++) {
                    lcsCorent = calculeazaLCS(lcsCorent, siruri.get(i));
                }

                printWriter.println(lcsCorent.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String> calculeazaLCS(List<String> a, List<String> b) {
        int n = a.size();
        int m = b.size();

        int[][] lung = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.get(i - 1).equals(b.get(j - 1))) {
                    lung[i][j] = lung[i - 1][j - 1] + 1;
                } else {
                    lung[i][j] = Math.max(lung[i - 1][j], lung[i][j - 1]);
                }
            }
        }

        List<String> rezultat = new ArrayList<>();
        int i = n;
        int j = m;

        while (i > 0 && j > 0) {
            if (a.get(i - 1).equals(b.get(j - 1))) {
                rezultat.add(a.get(i - 1));
                i--;
                j--;
            } else if (lung[i - 1][j] == lung[i][j]) {
                i--;
            } else {
                j--;
            }
        }

        Collections.reverse(rezultat);

        return rezultat;
    }
}
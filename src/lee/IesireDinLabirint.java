package lee;

import java.util.*;
import java.io.*;

public class IesireDinLabirint {
    public static class Locatie {
        public int linie, coloana;
        public Locatie(int l, int c) {
            this.linie = l; this.coloana = c;
        }
    }

    static int[] dL = {0, 0, 1, -1};
    static int[] dC = {1, -1, 0, 0};

    public static void main(String[] args) {
        String caleFisierIn  = "data/lee/iesire/in.txt";
        String caleFisierOut = "data/lee/iesire/out.txt";

        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            Scanner scanner = new Scanner(new File(caleFisierIn));

            int T = scanner.nextInt();

            for (int t = 0; t < T; t++) {
                int m = scanner.nextInt();
                int n = scanner.nextInt();
                scanner.nextLine();

                int[][] labirint = new int[m][n];

                for (int i = 0; i < m; i++) {
                    String linieText = scanner.nextLine().trim();
                    String procesat = linieText.replace("-", " -").replace("0", " 0 ");
                    Scanner linieScanner = new Scanner(procesat);
                    for (int j = 0; j < n; j++) {
                        if (linieScanner.hasNextInt()) {
                            labirint[i][j] = linieScanner.nextInt();
                        }
                    }
                }

                int startL = scanner.nextInt();
                int startC = scanner.nextInt();

                Locatie start = new Locatie(startL, startC);

                lee(labirint, start);

                Locatie iesire = gasesteIesire(labirint, m, n);

                if (iesire == null) {
                    writer.println("Suntem blocati in labirint.");
                } else {
                    int lungime = labirint[iesire.linie][iesire.coloana];
                    writer.println(lungime);
                    writer.println(iesire.linie + " " + iesire.coloana);
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void lee(int[][] labirint, Locatie start) {
        int m = labirint.length;
        int n = labirint[0].length;

        labirint[start.linie][start.coloana] = 1;

        Queue<Locatie> q = new LinkedList<>();
        q.add(start);

        while (!q.isEmpty()) {
            Locatie cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nL = cur.linie + dL[i];
                int nC = cur.coloana + dC[i];
                if (nL >= 0 && nL < m && nC >= 0 && nC < n && labirint[nL][nC] == 0) {
                    labirint[nL][nC] = labirint[cur.linie][cur.coloana] + 1;
                    q.add(new Locatie(nL, nC));
                }
            }
        }
    }

    public static Locatie gasesteIesire(int[][] labirint, int m, int n) {
        Locatie cea_mai_buna = null;
        int distMin = Integer.MAX_VALUE;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean peMargine = (i == 0 || i == m - 1 || j == 0 || j == n - 1);
                if (peMargine && labirint[i][j] > 1) {
                    if (labirint[i][j] < distMin) {
                        distMin = labirint[i][j];
                        cea_mai_buna = new Locatie(i, j);
                    }
                }
            }
        }
        return cea_mai_buna;
    }
}
package lee;

import java.util.*;
import java.io.*;

public class IesireDinLabirint {
    public static class Locatie {
        int linie, coloana;
        public Locatie(int l, int c) {
            this.linie = l;
            this.coloana = c;
        }
    }

    static int[] dL = {0, 0, -1, 1};
    static int[] dC = {1, -1, 0, 0};

    public static void main(String[] args) {
        String caleFisierIn  = "data/lee/iesire/in.txt";
        String caleFisierOut = "data/lee/iesire/out.txt";

        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            Scanner scanner = new Scanner(new File(caleFisierIn));

            int T = scanner.nextInt();

            for (int t = 0; t < T; t++) {
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                scanner.nextLine();

                int[][] labirint = new int[n][m];

                for (int i = 0; i < n; i++) {
                    String linie = scanner.nextLine();
                    String procesat = linie.replace("-", " -").replace("0", " 0 ");
                    Scanner linieScanner = new Scanner(procesat);
                    for (int j = 0; j < m; j++) {
                        if (linieScanner.hasNextInt())
                            labirint[i][j] = linieScanner.nextInt();
                    }
                }

                int startL = scanner.nextInt();
                int startC = scanner.nextInt();

                Locatie start = new Locatie(startL, startC);
                Lee(labirint, start);

                Locatie iesire = gasesteIeasire(labirint, n, m);

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

    public static void Lee(int[][] labirint, Locatie start) {
        int n = labirint.length;
        int m = labirint[0].length;

        labirint[start.linie][start.coloana] = 1;

        Queue<Locatie> q = new LinkedList<>();
        q.add(start);

        while (!q.isEmpty()) {
            Locatie curent = q.poll();
            for (int i = 0; i < 4; i++) {
                int nL = curent.linie + dL[i];
                int nC = curent.coloana + dC[i];
                if (nL >= 0 && nL < n && nC >= 0 && nC < m && labirint[nL][nC] == 0) {
                    labirint[nL][nC] = labirint[curent.linie][curent.coloana] + 1;
                    q.add(new Locatie(nL, nC));
                }
            }
        }
    }

    public static Locatie gasesteIeasire(int[][] labirint, int n, int m) {
        Locatie cea_mai_buna = null;
        int distMin = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean peMargine = (i == 0 || i == n - 1 || j == 0 || j == m - 1);
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
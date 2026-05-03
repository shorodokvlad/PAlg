package lee;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class IncaperiSecrete {
    public static class Locatie {
        int linie, coloana;
        public Locatie(int l, int c) {
            this.linie = l;
            this.coloana = c;
        }
    }
    public static int[] dL = {0, 0, 1, -1};
    public static int[] dC = {-1, 1, 0, 0};

    public static void main(String[] args) {
        String caleFisierIn = "data/lee/incaperi_secrete/in.txt";
        String caleFisierOut = "data/lee/incaperi_secrete/out.txt";

        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))){
            Scanner scanner = new Scanner(new File(caleFisierIn));

            int T = Integer.parseInt(scanner.nextLine().trim());

            for (int t = 0; t < T; t++) {
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                scanner.nextLine();

                int[][] labirint = new int[n][m];

                for (int i = 0; i < n; i++) {
                    String linieText = scanner.nextLine().trim();
                    String procesat = linieText.replace("-", " -").replace("0", " 0 ");
                    Scanner linieScanner = new Scanner(procesat);
                    for (int j = 0; j < m; j++) {
                        if (linieScanner.hasNextInt())
                            labirint[i][j] = linieScanner.nextInt();
                    }
                }

                int rez = DeterminaIncaperiSecrete(labirint, n, m);
                writer.println(rez);

            }
        } catch (Exception e) {
            System.out.println("Eroare la procesarea fisierului: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int DeterminaIncaperiSecrete(int[][] a, int n, int m) {
        int nr = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0) {
                    nr = nr + 1;
                    Lee(a, new Locatie(i, j));
                }
            }
        }
        return nr;
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
}
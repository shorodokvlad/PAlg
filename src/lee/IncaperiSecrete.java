package lee;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class IncaperiSecrete {
    static int[] dL = {0, 0, 1, -1};
    static int[] dC = {1, -1, 0, 0};

    public static class Locatie {
        int linie, coloana;
        public Locatie(int linie, int coloana) {
            this.linie = linie;
            this.coloana = coloana;
        }
    }

    public static void main(String[] args) {
        String caleFisierIn = "data/lee/incaperi_secrete/in.txt";
        String caleFisierOut = "data/lee/incaperi_secrete/out.txt";


        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))){
            File fisier = new File(caleFisierIn);
            Scanner scanner = new Scanner(fisier);

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
                linieScanner.close();
            }

            for (int i = 0; i < m; i++) {
                System.out.println();
                for (int j = 0; j < n; j++) {
                    System.out.print(labirint[i][j] + " ");
                }
            }

            int rezultat = determinaIncaperiSecrete(labirint, m, n);
            writer.println(rezultat);

        } catch (Exception e) {
            System.out.println("Eroare la procesarea fisierului: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int determinaIncaperiSecrete(int[][] a, int m, int n) {
        int nr = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 0) {
                    nr = nr + 1;
                    lee(a, new Locatie(i, j));
                }
            }
        }
        return nr;
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
}
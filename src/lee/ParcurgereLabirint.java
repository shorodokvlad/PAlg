package lee;

import java.util.*;
import java.io.*;

public class ParcurgereLabirint {
    public static class Locatie {
        public int linie, coloana;
        public Locatie(int l, int c) {
            this.linie = l; this.coloana = c;
        }
    }

    static int[] dL = {0, 0, 1, -1};
    static int[] dC = {1, -1, 0, 0};

    public static void main(String[] args) {
        String caleFisierIn = "data/lee/labirint/in.txt";
        String caleFisierOut = "data/lee/labirint/out.txt";

        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))){
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
                    //linieScanner.close();
                }

                int startL = scanner.nextInt();
                int startC = scanner.nextInt();
                int stopL  = scanner.nextInt();
                int stopC  = scanner.nextInt();
                //scanner.close();

                Locatie start = new Locatie(startL, startC);
                Locatie stop  = new Locatie(stopL, stopC);

                lee(labirint, start);

                List<Locatie> drum = reconstituieDrum(labirint, start, stop);

                if (!drum.isEmpty()) {
                    writer.println(drum.size());
                    for (Locatie p : drum) {
                        writer.println(p.linie + " " + p.coloana);
                    }
                } else {
                    writer.println("Nu exista drum.");
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

    public static List<Locatie> reconstituieDrum(int[][] labirint, Locatie start, Locatie stop) {
        int m = labirint.length;
        int n = labirint[0].length;

        List<Locatie> drum = new ArrayList<>();
        if (labirint[stop.linie][stop.coloana] <= 1) return drum;

        int cL = stop.linie;
        int cC = stop.coloana;
        int dist = labirint[cL][cC];

        while (!(cL == start.linie && cC == start.coloana)) {
            drum.add(new Locatie(cL, cC));
            for (int i = 0; i < 4; i++) {
                int nL = cL + dL[i];
                int nC = cC + dC[i];
                if (nL >= 0 && nL < m && nC >= 0 && nC < n && labirint[nL][nC] == dist - 1) {
                    cL = nL;
                    cC = nC;
                    dist--;
                    break;
                }
            }
        }
        drum.add(start);
        Collections.reverse(drum);
        return drum;
    }
}
package lee;

import java.util.*;
import java.io.*;

public class OptVecini {
    public static class Locatie {
        public int linie, coloana;
        public Locatie(int l, int c) {
            this.linie = l; this.coloana = c;
        }
    }

    // Toti cei 8 vecini: N, S, E, V, NE, NV, SE, SV
    static int[] dL = {0,  0,  1, -1,  1,  1, -1, -1};
    static int[] dC = {1, -1,  0,  0,  1, -1,  1, -1};

    public static void main(String[] args) {
        String caleFisierIn  = "data/lee/opt_vecini/in.txt";
        String caleFisierOut = "data/lee/opt_vecini/out.txt";

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
                    String procesat  = linieText.replace("-", " -").replace("0", " 0 ");
                    Scanner linieScanner = new Scanner(procesat);
                    for (int j = 0; j < n; j++) {
                        if (linieScanner.hasNextInt())
                            labirint[i][j] = linieScanner.nextInt();
                    }
                }

                int startL = scanner.nextInt(), startC = scanner.nextInt();
                int stopL  = scanner.nextInt(), stopC  = scanner.nextInt();

                Locatie start = new Locatie(startL, startC);
                Locatie stop  = new Locatie(stopL,  stopC);

                lee(labirint, start);

                List<List<Locatie>> toateDrumurile = toateDrumurileMinime(labirint, start, stop);

                if (toateDrumurile.isEmpty()) {
                    writer.println("Nu exista drum.");
                } else {
                    writer.println("Lungime drum: " + toateDrumurile.get(0).size());
                    writer.println("Numar drumuri minime: " + toateDrumurile.size());
                    for (int d = 0; d < toateDrumurile.size(); d++) {
                        writer.println("Drum " + (d + 1) + ":");
                        for (Locatie p : toateDrumurile.get(d))
                            writer.println("  " + p.linie + " " + p.coloana);
                    }
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
            for (int i = 0; i < 8; i++) {
                int nL = cur.linie   + dL[i];
                int nC = cur.coloana + dC[i];
                if (nL >= 0 && nL < m && nC >= 0 && nC < n && labirint[nL][nC] == 0) {
                    labirint[nL][nC] = labirint[cur.linie][cur.coloana] + 1;
                    q.add(new Locatie(nL, nC));
                }
            }
        }
    }

    public static List<List<Locatie>> toateDrumurileMinime(
            int[][] labirint, Locatie start, Locatie stop) {

        int m = labirint.length;
        int n = labirint[0].length;
        List<List<Locatie>> rezultat = new ArrayList<>();

        if (labirint[stop.linie][stop.coloana] <= 1) return rezultat;

        Deque<List<Locatie>> stiva = new ArrayDeque<>();
        List<Locatie> drumInitial = new ArrayList<>();
        drumInitial.add(stop);
        stiva.push(drumInitial);

        while (!stiva.isEmpty()) {
            List<Locatie> drumCurent = stiva.pop();
            Locatie capDrum = drumCurent.get(drumCurent.size() - 1);

            if (capDrum.linie == start.linie && capDrum.coloana == start.coloana) {
                List<Locatie> copie = new ArrayList<>(drumCurent);
                Collections.reverse(copie);
                rezultat.add(copie);
                continue;
            }

            int dist = labirint[capDrum.linie][capDrum.coloana];

            for (int i = 0; i < 8; i++) {
                int nL = capDrum.linie   + dL[i];
                int nC = capDrum.coloana + dC[i];
                if (nL >= 0 && nL < m && nC >= 0 && nC < n
                        && labirint[nL][nC] == dist - 1) {
                    List<Locatie> drumNou = new ArrayList<>(drumCurent);
                    drumNou.add(new Locatie(nL, nC));
                    stiva.push(drumNou);
                }
            }
        }

        return rezultat;
    }
}
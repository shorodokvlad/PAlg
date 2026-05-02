package lee;

import java.util.*;
import java.io.*;

public class VrajitoriComori {
    static int[] dL = {0,  0,  1, -1,  1,  1, -1, -1};
    static int[] dC = {1, -1,  0,  0,  1, -1,  1, -1};

    public static void main(String[] args) {
        String caleFisierIn  = "data/lee/vrajitori_comori/in.txt";
        String caleFisierOut = "data/lee/vrajitori_comori/out.txt";

        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            Scanner scanner = new Scanner(new File(caleFisierIn));

            int m = scanner.nextInt();
            int n = scanner.nextInt();
            scanner.nextLine();

            char[][] labirint = new char[m][n];
            for (int i = 0; i < m; i++) {
                String linie = scanner.nextLine().trim();
                for (int j = 0; j < n; j++) {
                    labirint[i][j] = linie.charAt(j);
                }
            }

            int startL = scanner.nextInt();
            int startC = scanner.nextInt();

            // Pas 1: marcam toate celulele periculoase
            // periculos[i][j] = true daca e V sau vecin cu V
            boolean[][] periculos = new boolean[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (labirint[i][j] == 'V') {
                        periculos[i][j] = true;           // celula vrajitorului
                        for (int d = 0; d < 8; d++) {     // toti cei 8 vecini
                            int ni = i + dL[d];
                            int nj = j + dC[d];
                            if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                                periculos[ni][nj] = true;
                            }
                        }
                    }
                }
            }

            // Pas 2: BFS din start, doar prin celule sigure (0 sau C, nepericulos)
            // Startul: daca e periculos, nu putem pleca de acolo -> 0 comori
            int comori = 0;

            if (!periculos[startL][startC]) {
                boolean[][] vizitat = new boolean[m][n];
                Queue<int[]> coada = new LinkedList<>();
                coada.add(new int[]{startL, startC});
                vizitat[startL][startC] = true;

                // verificam daca startul e comoara
                if (labirint[startL][startC] == 'C') comori++;

                while (!coada.isEmpty()) {
                    int[] cur = coada.poll();
                    int cl = cur[0], cc = cur[1];

                    for (int d = 0; d < 8; d++) {
                        int nl = cl + dL[d];
                        int nc = cc + dC[d];

                        if (nl >= 0 && nl < m && nc >= 0 && nc < n
                                && !vizitat[nl][nc]
                                && !periculos[nl][nc]
                                && labirint[nl][nc] != 'V') {
                            vizitat[nl][nc] = true;
                            if (labirint[nl][nc] == 'C') comori++;
                            coada.add(new int[]{nl, nc});
                        }
                    }
                }
            }

            writer.println(comori);

        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package lee;

import java.util.*;
import java.io.*;

public class VrajitoriComori {
    static int[] dL = {0, 0, 1, -1, 1, 1, -1, 1};
    static int[] dC = {1, -1, 0, 0, 1, -1, 1, -1};

    public static void main(String[] args) {
        String caleFisierIn  = "data/lee/vrajitori_comori/in.txt";
        String caleFisierOut = "data/lee/vrajitori_comori/out.txt";

        try (PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            Scanner scanner = new Scanner(new File(caleFisierIn));

            int n = scanner.nextInt();
            int m = scanner.nextInt();
            scanner.nextLine();

            char[][] labirint = new char[n][m];

            for (int i = 0; i < n; i++) {
                String linie = scanner.nextLine();
                for (int j = 0; j < m; j++)
                    labirint[i][j] = linie.charAt(j);
            }

            int startL = scanner.nextInt();
            int startC = scanner.nextInt();

            boolean[][] periculos = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (labirint[i][j] == 'V') {
                        periculos[i][j] = true;
                        for (int d = 0; d < 8; d++) {
                            int ni = i + dL[d];
                            int nj = j + dC[d];
                            if (ni >= 0 && ni < n && nj >= 0 && nj < m)
                                periculos[ni][nj] = true;
                        }
                    }
                }
            }

            int comori = 0;
            if (!periculos[startL][startC]) {
                boolean[][] vizitat = new boolean[n][m];
                Queue<int[]> q = new LinkedList<>();
                q.add(new int[]{startL, startC});
                vizitat[startL][startC] = true;

                if (labirint[startL][startC] == 'C') comori++;

                while (!q.isEmpty()) {
                    int[] curent = q.poll();
                    int cL = curent[0], cC = curent[1];

                    for (int d = 0; d < 8; d++) {
                        int nL = cL + dL[d];
                        int nC = cC + dC[d];

                        if (nL >= 0 && nL < n && nC >= 0 && nC < m && !vizitat[nL][nC] && !periculos[nL][nC] && labirint[nL][nC] != 'V') {
                            vizitat[nL][nC] = true;
                            if (labirint[nL][nC] == 'C')
                                comori++;
                            q.add(new int[] {nL, nC});
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
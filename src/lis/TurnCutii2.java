import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class TurnCutii2 {
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lis/cutii2/in.txt";
        String caleFisierOut = "data/lis/cutii2/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            if (!sc.hasNextInt()) return;
            int n = sc.nextInt();
            int[] g = new int[n];
            int[] w = new int[n];

            for (int i = 0; i < n; i++) {
                g[i] = sc.nextInt();
                w[i] = sc.nextInt();
            }

            // 1. SORTARE SIMPLĂ (Bubble Sort pentru a păstra vectorii paraleli)
            // Sortează descrescător după greutate (g)
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (g[i] < g[j]) {
                        int tempG = g[i];
                        g[i] = g[j];
                        g[j] = tempG;

                        int tempW = w[i];
                        w[i] = w[j];
                        w[j] = tempW;
                    }
                }
            }

            int[] lung = LIS(g, w, n);
            TiparesteLIS(g, w, n, lung);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] LIS(int[] g, int[] w, int n) {
        int[] lung = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (w[i] >= g[j] && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }

    public static void TiparesteLIS(int[] g, int[] w, int n, int[] lung) {
        int max = 0;
        int poz = 0;

        for (int i = 0; i < n; i++) {
            if (lung[i] > max) {
                max = lung[i];
                poz = i;
            }
        }

        printWriter.println(max);
        printWriter.println(g[poz] + " " + w[poz]);

        int lungRamas = max - 1;

        for (int i = poz + 1; i < n && lungRamas > 0; i++) {
            if (lung[i] == lungRamas && g[i] <= w[poz]) {
                printWriter.println(g[i] + " " + w[i]);
                poz = i;
                lungRamas--;

                if (lungRamas == 0) break;
            }
        }
    }
}
package lis;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TurnCutii {
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lis/cutii/in.txt";
        String caleFisierOut = "data/lis/cutii/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            int T = sc.nextInt();
            for (int t = 0; t < T; t++) {
                int n = sc.nextInt();

                int[] lun = new int[n];
                int[] lat = new int[n];
                int[] h = new int[n];

                for (int i = 0; i < n; i++) {
                    lun[i] = sc.nextInt();
                    lat[i] = sc.nextInt();
                    h[i] = sc.nextInt();
                }

                int[] aria = new int[n];
                for (int i = 0; i < n; i++) {
                    aria[i] = lat[i] * lun[i];
                }

                int[] lung = LIS(lun, aria);
                TiparesteLIS(lun, lat, h, aria, lung);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] LIS(int[] lun, int[] aria) {
        int n = lun.length;
        int[] lung = new int[n];
        lung[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (aria[i] >= aria[j]) {
                    if (max < lung[j])
                        max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }


    public static void TiparesteLIS(int[] lun, int[] lat, int[] h, int[] aria, int[] lung) {
        int n = lun.length;
        int max = 0;
        int poz = 0;

        for (int i = 0; i < n; i++) {
            if (max < lung[i]) {
                max = lung[i];
                poz = i;
            }
        }

        printWriter.println(max);
        printWriter.println(lun[poz] + " " + lat[poz] + " " + h[poz]);
        int lungRamas = max - 1;

        for (int i = poz + 1; i < n; i++) {
            if (lung[i] == lungRamas && aria[i] <= aria[poz]) {
                printWriter.println(lun[i] + " " + lat[i] + " " + h[i]);

                aria[poz] = aria[i];
                lungRamas--;

                if (lungRamas == 0) break;
            }
        }
    }

}
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

            while (sc.hasNext()) {
                int n = sc.nextInt();
                int[] lungime = new int[n];
                int[] latime = new int[n];
                int[] inaltime = new int[n];

                for (int i = 0; i < n; i++) {
                    lungime[i] = sc.nextInt();
                    latime[i] = sc.nextInt();
                    inaltime[i] = sc.nextInt();
                }

                int[] aria = new int[n];
                for (int i = 0; i < n; i++)
                    aria[i] = lungime[i] * latime[i];

                int[] lung = LIS(lungime, aria);
                TiparesteLIS(lung, lungime, latime, inaltime, aria);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] LIS(int[] lungime, int[] aria) {
        int n = lungime.length;
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

    public static void TiparesteLIS(int[] lung, int[] lungime, int[] latime, int[] h, int[] aria) {
        int n = lung.length;
        int max = 0;
        int poz = 0;

        for (int i = 0; i < n; i++) {
            if (max < lung[i]) {
                max = lung[i];
                poz = i;
            }
        }
        System.out.println(max);
        printWriter.println(max);

        System.out.println(lungime[poz] + " " + latime[poz] + " " + h[poz]);
        printWriter.println(lungime[poz] + " " + latime[poz] + " " + h[poz]);

        int rest = max - 1;
        int ariaAnterioara = aria[poz];

        for (int i = poz + 1; i < n; i++) {
            if (lung[i] == rest && aria[i] <= ariaAnterioara) {
                System.out.println(lungime[i] + " " + latime[i] + " " + h[i]);
                printWriter.println(lungime[i] + " " + latime[i] + " " + h[i]);

                ariaAnterioara = aria[i];
                rest--;

                if (rest == 0) break;
            }
        }
    }

}
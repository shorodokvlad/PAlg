package lis;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Excursii2 {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lis/excursii2/in.txt";
        String caleFisierOut = "data/lis/excursii2/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            int T = Integer.parseInt(sc.nextLine().trim());

            for (int t = 0; t < T; t++) {
                int n = Integer.parseInt(sc.nextLine().trim());

                String[] orase = new String[n];
                int citite = 0;
                while (citite < n) {
                    String[] peLiniaAsta = sc.nextLine().trim().split("\\s+");
                    for (String oras : peLiniaAsta) {
                        orase[citite++] = oras;
                    }
                }

                int[] lung = LIS(orase);
                printWriter.println(lungimeMaxima(lung));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] LIS(String[] a) {
        int n = a.length;
        int[] lung = new int[n];

        lung[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                // în loc de a[i] <= a[j], comparăm alfabetic
                if (a[i].compareTo(a[j]) < 0 && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }

        return lung;
    }

    public static int lungimeMaxima(int[] lung) {
        int max = lung[0];
        for (int i = 1; i < lung.length; i++) {
            if (lung[i] > max) {
                max = lung[i];
            }
        }
        return max;
    }
}
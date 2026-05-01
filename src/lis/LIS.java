package lis;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class LIS {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lis/lis/in.txt";
        String caleFisierOut = "data/lis/lis/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            int T = sc.nextInt();

            for (int j = 0; j < T; j++) {
                int n = sc.nextInt();
                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }

                int[] lung = LIS(arr);
                TiparesteLIS(arr, lung.length, lung);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] LIS(int[] a) {
        int n = a.length;
        int[] lung = new int[n];

        lung[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (a[i] <= a[j] && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }

    public static void TiparesteLIS(int[] a, int n, int[] lung) {
        int max = lung[0];
        int poz = 0;
        for (int i = 1; i < n; i++) {
            if (max < lung[i]) {
                max = lung[i];
                poz = i;
            }
        }

        System.out.println("Lungimea maxima este " + max);
        System.out.print("Iar subsecventa este: ");

        System.out.print(a[poz] + " ");

        for (int i = poz + 1; i < n; i++) {
            if (lung[i] == max - 1 && a[i] >= a[poz]) {
                System.out.print(a[i] + " ");

                poz = i;
                max = max - 1;

                if (max == 0) {
                    break;
                }
            }
        }
        System.out.println();
    }

}
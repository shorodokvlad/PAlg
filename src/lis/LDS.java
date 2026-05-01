package lis;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class LDS {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lis/lds/in.txt";
        String caleFisierOut = "data/lis/lds/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            while (sc.hasNextInt()) {
                int n = sc.nextInt();
                int[] arr = new int[n];
                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }

                int[] lung = LDS(arr);
                tiparesteLDS(arr, n, lung);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] LDS(int[] a) {
        int n = a.length;
        int[] lung = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (a[i] > a[j] && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }

    public static void tiparesteLDS(int[] a, int n, int[] lung) {
        int max = lung[0];
        int poz = 0;

        for (int i = 0; i < n; i++) {
            if (max < lung[i]) {
                max = lung[i];
                poz = i;
            }
        }
        printWriter.println(max);
        printWriter.print(a[poz] + " ");
        int lungRamasa = max - 1;

        for (int i = poz + 1; i < n; i++) {
            if (lung[i] == lungRamasa && a[i] < a[poz]) {
                printWriter.print(a[i] + " ");
                poz = i;
                lungRamasa--;

                if (lungRamasa == 0) break;
            }
        }
        printWriter.println();
    }

    public static int[] LDSImpar(int[] a) {
        int n = a.length;
        int[] lung = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            if (a[i] % 2 == 0) {
                lung[i] = 0;
                continue;
            }


            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (a[j] % 2 != 0 && a[i] > a[j] && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }

    public static void tiparesteLDSImpar(int[] a, int n, int[] lung) {
        int max = 0;
        int poz = -1;
        for (int i = 0; i < n; i++) {
            if (lung[i] > max) {
                max = lung[i];
                poz = i;
            }
        }

        printWriter.println(max);
        printWriter.print(a[poz] + " ");
        int lungRamas = max - 1;

        for (int i = poz + 1; i < n; i++) {
            if (a[i] % 2 != 0 && a[i] < a[poz] && lung[i] == lungRamas) {
                printWriter.print(a[i] + " ");
                poz = i;
                lungRamas--;
                if (lungRamas == 0) {
                    break;
                }
            }
        }
        printWriter.println();
    }

    public static int[] LDSPar(int[] a) {
        int n = a.length;
        int[] lung = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            if (a[i] % 2 != 0) {
                lung[i] = 0;
                continue;
            }
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (a[j] % 2 == 0 && a[i] > a[j] && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }

    public static void tiparesteLDSPar(int[] a, int n, int[] lung) {
        int max = 0;
        int poz = 0;
        for (int i = 0; i < n; i++) {
            if (max < lung[i]) {
                max = lung[i];
                poz = i;
            }
        }
        printWriter.println(max);

        StringBuilder sb = new StringBuilder();
        sb.append(a[poz]);
        int lungRamas = max - 1;

        for (int i = poz + 1; i < n && lungRamas > 0; i++) {
            if (a[i] % 2 == 0 && a[i] < a[poz] && lung[i] == lungRamas) {
                sb.append(" ").append(a[i]);
                poz = i;
                lungRamas--;
            }
        }

        printWriter.println(sb);
    }

}
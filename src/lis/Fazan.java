package lis;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Fazan {

    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn  = "data/lis/fazan/in.txt";
        String caleFisierOut = "data/lis/fazan/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            int n = sc.nextInt();
            String[] cuvinte = new String[n];
            for (int i = 0; i < n; i++) {
                cuvinte[i] = sc.next();
            }

            int[] lung = calcLung(cuvinte, n);
            tipareste(cuvinte, n, lung);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean esteSuccesor(String a, String b) {
        if (a.length() < 2 || b.length() < 2) return false;
        String sfarsitA = a.substring(a.length() - 2);
        String inceputB = b.substring(0, 2);
        return sfarsitA.equals(inceputB);
    }

    static int[] calcLung(String[] cuvinte, int n) {
        int[] lung = new int[n];

        lung[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            int max = 0;
            for (int j = i + 1; j < n; j++) {
                if (esteSuccesor(cuvinte[i], cuvinte[j]) && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }

    static void tipareste(String[] cuvinte, int n, int[] lung) {
        int max = lung[0];
        int poz = 0;
        for (int i = 1; i < n; i++) {
            if (lung[i] > max) {
                max = lung[i];
                poz = i;
            }
        }

        printWriter.println(max);
        printWriter.println(cuvinte[poz]);

        for (int i = poz + 1; i < n; i++) {
            if (lung[i] == max - 1 && esteSuccesor(cuvinte[poz], cuvinte[i])) {
                printWriter.println(cuvinte[i]);
                poz = i;
                max = max - 1;
                if (max == 0) break;
            }
        }
    }
}
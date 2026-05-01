package lis;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Elefanti {
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/lis/elefanti/in.txt";
        String caleFisierOut = "data/lis/elefanti/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            printWriter = writer;

            int T = sc.nextInt();
            for (int j = 0; j < T; j++) {
                int n = sc.nextInt();
                int[][] elefanti = new int[n][2];

                for (int i = 0; i < n; i++) {
                    elefanti[i][0] = sc.nextInt();
                    elefanti[i][1] = sc.nextInt();
                }

                Arrays.sort(elefanti, (a, b) -> {
                    if (a[0] != b[0]) return a[0] - b[0];
                    return b[1] - a[1];
                });

                int[] iq = new int[n];
                for (int i = 0; i < n; i++) {
                    iq[i] = elefanti[i][1];
                }

                int[] lung = LIS(iq);

                int maxLung = 0;
                for (int val : lung) {
                    if (val > maxLung) maxLung = val;
                }

                writer.println(maxLung);
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
                if (a[i] > a[j] && lung[j] > max) {
                    max = lung[j];
                }
            }
            lung[i] = max + 1;
        }
        return lung;
    }
}

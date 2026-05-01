package lis;

import java.io.File;
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

            int n = sc.nextInt();
            int[] lungime  = new int[n];
            int[] latime   = new int[n];
            int[] inaltime = new int[n];
            int[] arie     = new int[n];

            for (int i = 0; i < n; i++) {
                lungime[i]  = sc.nextInt();
                latime[i]   = sc.nextInt();
                inaltime[i] = sc.nextInt();
                arie[i]     = lungime[i] * latime[i];
            }

            int[] dp = calculeazaDP(arie, n);

            tiparesteTurn(dp, arie, lungime, latime, inaltime, n, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // dp[i] = numărul maxim de cutii în turn care se termină cu cutia i
    public static int[] calculeazaDP(int[] arie, int n) {
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = 1; // turnul format doar din cutia i

            for (int j = 0; j < i; j++) {
                // cutia i poate sta deasupra cutiei j
                if (arie[i] <= arie[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        return dp;
    }

    public static void tiparesteTurn(int[] dp, int[] arie, int[] lungime,
                                     int[] latime, int[] inaltime, int n,
                                     PrintWriter writer) {
        int maxCutii = dp[0];
        int pozFinal = 0;
        for (int i = 1; i < n; i++) {
            if (dp[i] > maxCutii) {
                maxCutii = dp[i];
                pozFinal = i;
            }
        }

        int[] turn = new int[n];
        int lungimeTurn = 0;
        int poz = pozFinal;

        while (poz != -1) {
            turn[lungimeTurn++] = poz;

            int urmator = -1;
            for (int j = poz - 1; j >= 0; j--) {
                if (arie[poz] <= arie[j] && dp[j] == dp[poz] - 1) {
                    urmator = j;
                    break;
                }
            }
            poz = urmator;
        }

        writer.println(maxCutii);
        for (int i = 0; i < lungimeTurn; i++) {
            int idx = turn[i];
            writer.println(lungime[idx] + " " + latime[idx] + " " + inaltime[idx]);
        }
    }
}
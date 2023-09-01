import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 30.
 * @see BoJ 2098 (G1)
 * @category DP, Bitmasking
 * @note 외판원 순회
 *
 * 비트마스킹을 이용한 DP
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[][] memory;
    static int[][] costs;
    static int INF = 20000000;

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();

        memory = new int[N][1 << N];
        costs = new int[N][N];

        for (int i = 0; i < N; i++) Arrays.fill(memory[i], -1);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                costs[i][j] = sc.nextInt();
            }
        }

        System.out.println(dp(0, 1));
    }

    static int dp(int before, int visited) {

        if (visited == (1 << N) - 1) {
            if (costs[before][0] == 0) {
                return INF;
            }
            else return costs[before][0];
        }

        if (memory[before][visited] != -1) return memory[before][visited];

        for (int i = 0; i < N; i++) {
            if ((visited & (1 << i)) != 0) continue;
            if (costs[before][i] == 0) continue;

            int x = dp(i, visited | (1 << i));

            if (x < INF && x != -1) {
                if (memory[before][visited] == -1) {
                    memory[before][visited] = x + costs[before][i];
                } else {
                    memory[before][visited] = Math.min(memory[before][visited], x + costs[before][i]);
                }
            }
        }

        if (memory[before][visited] == -1) memory[before][visited] = INF;
        return memory[before][visited];
    }
}

class BufferedScanner {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;

    public String next() throws IOException {
        if (st == null || !hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }

        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    public char nextChar() throws IOException {
        return next().charAt(0);
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public String readLine() throws IOException {
        String str = br.readLine();
        st = new StringTokenizer(str);

        return str;
    }

    public boolean hasMoreTokens() {
        return st.hasMoreTokens();
    }
}
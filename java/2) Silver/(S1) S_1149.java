import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 25.
 * @see BoJ 1149 (S1)
 * @category DP
 * @note RGB거리
 *
 * Row = 집 / Column = 색깔
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();

        int[][] costs = new int[N + 1][3];
        for (int i = 1; i < N + 1; i++) {
            costs[i] = new int[] { sc.nextInt(), sc.nextInt(), sc.nextInt() };
        }

        int[][] dp = new int[N + 1][3];
        for (int i = 1; i < N + 1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < 3; column++) {
                for (int targetColumn = 0; targetColumn < 3; targetColumn++) {
                    if (column == targetColumn) continue;

                    dp[row + 1][targetColumn] = Math.min(dp[row + 1][targetColumn], dp[row][column] + costs[row + 1][targetColumn]);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            min = Math.min(min, dp[N][i]);
        }

        System.out.println(min);
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
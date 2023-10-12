import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 12.
 * @see BoJ 17069 (G4)
 * @category
 * @note 파이프 옮기기 2
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int[][] map;
    static long[][][] dp;

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();

        map = new int[N][N];
        dp = new long[3][N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        dp[0][0][1] = 1;

        for (int i = 0; i < N; i++) {
            for (int j = 2; j < N; j++) {
                if (map[i][j] == 1) continue;

                // 0 = 가로, 1 = 대각선, 2 = 세로

                dp[0][i][j] = dp[0][i][j - 1] + dp[1][i][j - 1];

                if (i != 0) { // 맨 윗줄이면 끌어올거 없음
                    if (map[i - 1][j] != 1 && map[i][j - 1] != 1)
                        dp[1][i][j] = dp[0][i - 1][j - 1] + dp[1][i - 1][j - 1] + dp[2][i - 1][j - 1];

                    dp[2][i][j] = dp[1][i - 1][j] + dp[2][i - 1][j];
                }
            }
        }

        System.out.println(dp[0][N - 1][N - 1] + dp[1][N - 1][N - 1] + dp[2][N - 1][N - 1]);
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
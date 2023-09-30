import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 30.
 * @see BoJ 14517 (P5)
 * @category DP
 * @note 팰린드롬 개수 구하기 (Large)
 * 
 * DP + 포함 배제의 원리를 이용해 팰린드롬 구하기
 * 
 */


public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static final int MOD = 10007;
    public static void main(String[] args) throws Exception {
        String str = sc.next();
        int len = str.length();

        int[][] dp = new int[len][len];

        for (int i = 0; i < len; i++) dp[i][i] = 1;

        for (int i = 1; i < len; i++) {
            dp[i - 1][i] = 2;

            if (str.charAt(i) == str.charAt(i - 1)) dp[i - 1][i]++;
        }

        for (int i = 2; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (str.charAt(j) == str.charAt(j - i)) {
                    dp[j - i][j] = (dp[j - i][j - 1] + dp[j - i + 1][j] + 1) % MOD;
                }
                else {
                    dp[j - i][j] = (dp[j - i][j - 1] + dp[j - i + 1][j] - dp[j - i + 1][j - 1] + MOD) % MOD;
                }
            }
        }

        System.out.println(dp[0][len - 1]);
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

    public char nextChar() throws IOException {
        return next().charAt(0);
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
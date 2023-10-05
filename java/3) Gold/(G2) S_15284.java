import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 05.
 * @see BoJ 15284 (G2)
 * @category Combination, Math
 * @note 너 봄에는 캡사이신이 맛있단다
 *
 * 
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();

        long[] arr = new long[N + 1];
        long[] multiplier = new long[N + 1];

        multiplier[0] = 1L;

        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextLong();
            multiplier[i] = (multiplier[i - 1] * 2) % MOD;
        }

        Arrays.sort(arr);

        long result = 0;
        for (int i = 1; i <= N; i++) {
            result = (result + (multiplier[i - 1] - multiplier[N - i]) * arr[i]) % MOD;
        }

        System.out.println(result);
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
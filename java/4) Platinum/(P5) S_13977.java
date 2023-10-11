import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 11.
 * @see BoJ 13977 (P5)
 * @category Math
 * @note 이항 계수와 쿼리
 *
 * 페르마의 소정리 응용
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int MOD;
    static long[] factorials;

    public static void main(String[] args) throws IOException {
        int M = sc.nextInt();

        MOD = 1_000_000_007;
        factorials = new long[4000001];
        factorials[0] = 1;
        factorials[1] = 1;

        long current = 1;
        for (int i = 2; i <= 4000000; i++) {
            current = (current * i) % MOD;
            factorials[i] = current;
        }

        for (int j = 0; j < M; j++) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            long result = (f(N) * pow((f(N - K) * f(K)) % MOD, MOD - 2)) % MOD;
            sb.append(result).append("\n");
        }

        System.out.print(sb);
    }

    static long f(int n) {
       // if (n >= MOD) return 0;
        return factorials[n];
    }

    static long pow(long n, long x) {
        if (x == 0) return 1;
        if (x == 1) return n;

        long result;
        long child = pow(n, x / 2);

        if (x % 2 == 0) result = (child * child) % MOD;
        else result = (((child * child) % MOD) * n) % MOD;

        return result;
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
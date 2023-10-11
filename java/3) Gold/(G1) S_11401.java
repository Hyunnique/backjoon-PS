import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 11.
 * @see BoJ 11401
 * @category Math
 * @note 조합
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static final long MOD = 1_000_000_007;
    static long[] factorials;
    static Map<Long, Long> pows;

    public static void main(String[] args) throws IOException {
            pows = new HashMap<>();
            int N = sc.nextInt();
            int R = sc.nextInt();

            factorials = new long[N + 1];
            factorials[0] = 1;
            factorials[1] = 1;

            long current = 1;
            for (int i = 2; i <= N; i++) {
                current = (current * i) % MOD;
                factorials[i] = current;
            }
            // 조합 = n! / ((n-r)! * r!)
            // = n! * ((n-r)! * r!)^-1

            long result = (f(N) * pow((f(N - R) * f(R)) % MOD, MOD - 2)) % MOD;

        System.out.print(result);
    }

    static long f(int n) {
        return factorials[n];
    }

    static long pow(long n, long x) {
        if (x == 1) return n;
        if (pows.containsKey(x)) return pows.get(x);

        long result = 0;

        if (x % 2 == 0) result = (pow(n, x / 2) * pow(n, x / 2)) % MOD;
        else result = (pow(n, x / 2) * pow(n, x / 2 + 1)) % MOD;

        if (!pows.containsKey(x)) pows.put(x, result);
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
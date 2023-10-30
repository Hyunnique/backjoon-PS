import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 30.
 * @see BoJ 30408 (G5)
 * @category Memoization
 * @note 춘배가 선물하는 특별한 하트
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static long N, M;
    static HashSet<Long> memory = new HashSet<>();

    public static void main(String[] args) throws IOException {
        N = sc.nextLong();
        M = sc.nextLong();

        if (divide(N)) System.out.println("YES");
        else System.out.println("NO");
    }

    static boolean divide(long x) {
        if (memory.contains(x)) return false;
        if (x < M) return false;
        if (x == M) return true;

        memory.add(x);

        if (x % 2 == 0) {
            return divide(x / 2);
        } else {
            return divide( x / 2) || divide(x / 2 + 1);
        }
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
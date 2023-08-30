import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 25.
 * @see BoJ 11060 (S2)
 * @category DP
 * @note 점프 점프
 *
 * DFS + DP
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[] arr;
    static int[] memory;

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();
        arr = new int[N];
        memory = new int[N];

        Arrays.fill(memory, Integer.MAX_VALUE);

        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();

        dp(0, 0);
        memory[0] = 0;
        System.out.println((memory[N - 1] == Integer.MAX_VALUE ? -1 : memory[N - 1]));
    }

    static void dp(int idx, int jump) {
        if (idx >= N - 1) return;

        for (int i = arr[idx]; i >= 1; i--) {
            if (idx + i > N - 1 || memory[idx + i] <= jump + 1) continue;

            memory[idx + i] = jump + 1;
            dp(idx + i, jump + 1);
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
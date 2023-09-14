import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 13.
 * @see BoJ 2533 (G3)
 * @category Tree, DP
 * @note 사회망 서비스 (SNS)
 *
 * DP
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static ArrayList<ArrayList<Integer>> adjList;
    static boolean[] visited;
    static int[][] dp;

    public static void main(String[] args) throws IOException {

        int N = sc.nextInt();

        adjList = new ArrayList<>();
        visited = new boolean[N + 1];
        dp = new int[N + 1][2];

        for (int i = 0; i < N + 1; i++) {
            adjList.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < N - 1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            adjList.get(a).add(b);
            adjList.get(b).add(a);
        }

        dfs(1);
        System.out.println(Math.min(dp[1][0], dp[1][1]));
    }

    static void dfs(int x) {
        visited[x] = true;
        dp[x][0] = 0;
        dp[x][1] = 1;

        for (int adj : adjList.get(x)) {
            if (visited[adj]) continue;

            dfs(adj);
            dp[x][0] += dp[adj][1];
            dp[x][1] += Math.min(dp[adj][0], dp[adj][1]);
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
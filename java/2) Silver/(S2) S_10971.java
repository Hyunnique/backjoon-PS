import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 30.
 * @see BoJ 10971 (S2)
 * @category DP
 * @note 외판원 순회 2
 *
 * 10!이므로 모든 순열을 탐색해볼수도 있겠지만
 * A->B로 가는 길이 없을 수도 있기 때문에
 * 갈 수 있는지를 체크하는 방식으로 DFS + 백트래킹 활용
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static int N, minCost;
    static int[][] adjCosts;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();
        minCost = Integer.MAX_VALUE;

        adjCosts = new int[N][N];
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                adjCosts[i][j] = sc.nextInt();
            }
        }

        visited[0] = true;
        dfs(0, 0, 0, 0);
        
        /*
        for (int i = 0; i < N; i++) {
            visited[i] = true;
            dfs(i, i, 0, 0);
            visited[i] = false;
        }
        */

        System.out.println(minCost);
    }

    static void dfs(final int startIndex, int index, int nth, int costSum) {

        if (nth == N - 1) {
            if (adjCosts[index][startIndex] == 0) return;

            minCost = Math.min(minCost, costSum + adjCosts[index][startIndex]);
        }

        for (int i = 0; i < N; i++) {
            if (visited[i] || adjCosts[index][i] == 0) continue;
            if (costSum + adjCosts[index][i] >= minCost) continue;

            visited[i] = true;
            dfs(startIndex, i, nth + 1, costSum + adjCosts[index][i]);
            visited[i] = false;
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
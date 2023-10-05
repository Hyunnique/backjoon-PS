import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 04.
 * @see BoJ 2458 (G4)
 * @category 플로이드 워셜
 * @note
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();
        int M = sc.nextInt();

        boolean[][] adjList = new boolean[N][N];

        for (int i = 0; i < N; i++) adjList[i][i] = true;

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            adjList[a][b] = true;
            //adjList[b][a] = true;
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == j) continue;
                    adjList[i][j] = adjList[i][j] || (adjList[i][k] && adjList[k][j]);
                }
            }
        }

        int cnt = 0;
        outer:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!adjList[i][j] && !adjList[j][i]) continue outer;
            }

            ++cnt;
        }

        System.out.println(cnt);
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
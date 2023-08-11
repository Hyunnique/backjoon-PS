import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 09.
 * @see BoJ 14938
 * @note
 * 
 * 플로이드 워셜
 * N to N 최단거리 구해서 비교해야함
 */

class CustomReader {
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

class Node {
    int value;

    Node(int value) {
        this.value = value;
    }
}

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static final int INF = 200000000;
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        int M = cr.nextInt();
        int R = cr.nextInt();

        int[] items = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            items[i] = cr.nextInt();
        }

        int[][] adjList = new int[N + 1][N + 1];

        for (int i = 0; i < N + 1; i++) {
            Arrays.fill(adjList[i], INF);
        }

        for (int i = 1; i < N + 1; i++) {
            adjList[i][i] = 0;
        }

        for (int i = 0; i < R; i++) {
            int a = cr.nextInt();
            int b = cr.nextInt();
            int l = cr.nextInt();

            adjList[a][b] = Math.min(adjList[a][b], l);
            adjList[b][a] = Math.min(adjList[b][a], l);
        }

        for (int k = 0; k < N + 1; k++) {
            for (int i = 0; i < N + 1; i++) {
                for (int j = 0; j < N + 1; j++) {
                    adjList[i][j] = Math.min(adjList[i][j], adjList[i][k] + adjList[k][j]);
                }
            }
        }

        int max = 0;

        for (int i = 1; i < N + 1; i++) {
            //System.out.println(Arrays.toString(adjList[i]));
            int rowResult = 0;
            for (int j = 1; j < N + 1; j++) {
                if (adjList[i][j] <= M) {
                    rowResult += items[j];
                }
            }

            max = Math.max(max, rowResult);
        }

        System.out.println(max);
    }
}

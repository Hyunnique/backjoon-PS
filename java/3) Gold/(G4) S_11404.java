import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 11.
 * @see BoJ 11404 (G4)
 * @note 플로이드
 * 
 * 제목에서도 알 수 있듯이 기초적인 플로이드 워셜 문제인듯
 * 
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

public class Main {   
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static final int INF = 100000000;
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();
        
        N = cr.nextInt();
        M = cr.nextInt();

        int[][] adjList = new int[N + 1][N + 1];

        for (int i = 0; i < N + 1; i++) {
            Arrays.fill(adjList[i], INF);
        }

        for (int i = 0; i < N + 1; i++) {
            adjList[i][i] = 0;
        }

        for (int i = 0; i < M; i++) {
            int a = cr.nextInt();
            int b = cr.nextInt();
            int c = cr.nextInt();

            adjList[a][b] = Math.min(adjList[a][b], c);
        }

        for (int k = 1; k < N + 1; k++) {
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    adjList[i][j] = Math.min(adjList[i][j], adjList[i][k] + adjList[k][j]);
                }
            }
        }

        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (adjList[i][j] == INF) sb.append("0 ");
                else sb.append(adjList[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}

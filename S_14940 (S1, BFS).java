// Default
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    
    public static int N, M;
    public static int[][] arr;
    public static boolean[][] visited;
    public static int[][] dv = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N][M];
        visited = new boolean[N][M];

        int curR = -1, curC = -1;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                int s = Integer.parseInt(st.nextToken());
                if (s == 2) { curR = i; curC = j; }
                if (s == 1) s = -1;
                arr[i][j] = s;
            }
        }

        arr[curR][curC] = 0;
        bfs(curR, curC);
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result.append(arr[i][j] + " ");
            }
            result.append("\n");
        }

        System.out.println(result);
    }

    public static void bfs(int R, int C) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] { R, C, 1 });

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int[] v : dv) {
                int curR = cur[0] + v[0];
                int curC = cur[1] + v[1];

                if (curR < 0 || curR >= N || curC < 0 || curC >= M || visited[curR][curC] || arr[curR][curC] == 0) continue;

                arr[curR][curC] = cur[2];
                visited[curR][curC] = true;
                q.add(new int[] { curR, curC, cur[2] + 1 });
            }
        }
    }
}

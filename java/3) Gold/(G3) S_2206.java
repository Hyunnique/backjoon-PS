import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 08.
 * @see BoJ 2206
 * @note
 * 
 *       BFS를 이용해서 최단거리를 출력하면 되는데,
 *       벽을 한 개 부수는 것을 생각해야 함
 * 
 *       벽을 부순 차원, 부수지 않은 차원으로 나누어
 *       3차원 배열로 처리하기
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
    static int N;
    static StringBuilder sb = new StringBuilder();
    static int[][] vec = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        int M = cr.nextInt();

        int[][] map = new int[N][M];
        boolean[][][] visited = new boolean[2][N][M];

        for (int i = 0; i < N; i++) {
            char[] str = cr.readLine().toCharArray();

            for (int j = 0; j < M; j++) {
                map[i][j] = str[j] - 48;
            }
        }

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { 0, 0, 0, 1 });

        int curX = 0, curY = 0;
        int dist0 = Integer.MAX_VALUE;
        int dist1 = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (cur[1] == N - 1 && cur[2] == M - 1) {
                if (cur[0] == 0)
                    dist0 = cur[3];
                else
                    dist1 = cur[3];
            }
            for (int[] v : vec) {
                curX = cur[1] + v[0];
                curY = cur[2] + v[1];

                if (curX < 0 || curX >= N || curY < 0 || curY >= M)
                    continue;

                // System.out.println(curX + "," + curY + "? " + map[curX][curY]);
                if (map[curX][curY] == 0) {
                    if (visited[cur[0]][curX][curY])
                        continue;

                    visited[cur[0]][curX][curY] = true;
                    queue.offer(new int[] { cur[0], curX, curY, cur[3] + 1 });
                } else {
                    if (cur[0] == 1 || visited[1][curX][curY])
                        continue;

                    visited[1][curX][curY] = true;
                    queue.offer(new int[] { 1, curX, curY, cur[3] + 1 });
                }
            }
        }

        if (Math.min(dist0, dist1) == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(Math.min(dist0, dist1));
    }
}

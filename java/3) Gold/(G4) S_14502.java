import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 25.
 * @see BoJ 14502 (G4)
 * @category BFS
 * @note 연구소
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static int[][] map;
    static int initCount = 0, minCount = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();
        M = sc.nextInt();

        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();
                if (map[i][j] == 0) initCount++;
            }
        }

        for (int a = 0; a < N * M - 2; a++) {
            if (map[a / M][a % M] == 0) map[a / M][a % M] = 1;
            else continue;

            for (int b = a + 1; b < N * M - 1; b++) {
                if (map[b / M][b % M] == 0) map[b / M][b % M] = 1;
                else continue;

                for (int c = b + 1; c < N * M; c++) {
                    if (map[c / M][c % M] == 0) map[c / M][c % M] = 1;
                    else continue;

                    find();
                    map[c / M][c % M] = 0;
                }

                map[b / M][b % M] = 0;
            }

            map[a / M][a % M] = 0;
        }

        System.out.println(initCount - minCount - 3);
    }

    static void find() {
        boolean[][] visited = new boolean[N][M];
        int result = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 2) result += dfs(visited, i, j);
            }
        }

        if (result < minCount) minCount = result;
    }

    static int[][] movementDelta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int dfs(boolean[][] visited, int i, int j) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { i, j });
        visited[i][j] = true;

        int painted = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for (int[] delta : movementDelta) {
                int posY = cur[0] + delta[0];
                int posX = cur[1] + delta[1];

                if (posY < 0 || posY >= N || posX < 0 || posX >= M || visited[posY][posX]
                || map[posY][posX] != 0) continue;

                visited[posY][posX] = true;
                painted++;
                queue.offer(new int[] { posY, posX });
            }
        }

        return painted;
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
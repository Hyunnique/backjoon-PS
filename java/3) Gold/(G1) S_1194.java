import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 26.
 * @see BoJ 1194 (G1)
 * @category BFS
 * @note 달이 차오른다, 가자.
 *
 * BFS
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static char[][] map;
    static int[][] movementDelta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int minMoveCount = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = sc.nextInt();
        M = sc.nextInt();

        map = new char[N][M];

        int curRow = -1, curColumn = -1;

        for (int i = 0; i < N; i++) {
            String str = sc.next();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == '0') {
                    map[i][j] = '.';
                    curRow = i;
                    curColumn = j;
                }
            }
        }

        boolean[][][] visited = new boolean[(1 << 6)][N][M];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { curRow, curColumn, 0, 0 });
        visited[0][curRow][curColumn] = true;

        int curKeys = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            if (current[3] >= minMoveCount) break;

            for (int[] delta : movementDelta) {
                curRow = current[0] + delta[0];
                curColumn = current[1] + delta[1];
                curKeys = current[2];

                if (curRow < 0 || curRow >= N || curColumn < 0 || curColumn >= M ||
                visited[curKeys][curRow][curColumn] || map[curRow][curColumn] == '#') continue;

                if (map[curRow][curColumn] >= 'a' && map[curRow][curColumn] <= 'f') {
                    curKeys |= (1 << (map[curRow][curColumn] - 'a'));
                }

                if (map[curRow][curColumn] >= 'A' && map[curRow][curColumn] <= 'F' &&
                        (curKeys & (1 << (map[curRow][curColumn] - 'A'))) == 0) continue;

                if (map[curRow][curColumn] == '1') {
                    minMoveCount = current[3];
                }

                visited[curKeys][curRow][curColumn] = true;
                queue.offer(new int[] { curRow, curColumn, curKeys, current[3] + 1 });
            }
        }

        if (minMoveCount == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(minMoveCount + 1);
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
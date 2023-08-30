import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 22.
 * @see BoJ 10026 (G5)
 * @category DFS / BFS
 * @note 적록색약
 *
 * 적록색약인 그리드와 아닌 그리드에 대해
 * 각각 그룹을 탐색하기
 * 구현이 익숙한 BFS로 하자
 * 
 */

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int[][] movementDelta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    public static void main(String[] args) throws Exception {
        N = sc.nextInt();
        
        // 1만개 배열 하나쯤은 그냥 더 만들어도 괜찮을듯?
        char[][] map = new char[N][N];
        char[][] colorBlindMap = new char[N][N];
        boolean[][] visited = new boolean[N][N];

        for (int row = 0; row < N; row++) {
            String str = sc.next();
            for (int column = 0; column < N; column++) {
                map[row][column] = str.charAt(column);
                colorBlindMap[row][column] = (str.charAt(column) == 'G' ? 'R' : str.charAt(column));
            }
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    ++result;
                    bfs(map, visited, i, j, map[i][j]);
                }
            }
        }

        sb.append(result).append(" ");
        result = 0;
        for (int i = 0; i < N; i++) Arrays.fill(visited[i], false);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    ++result;
                    bfs(colorBlindMap, visited, i, j, colorBlindMap[i][j]);
                }
            }
        }
        sb.append(result).append(" ");
        System.out.println(sb.toString());
    }

    static void bfs(char[][] map, boolean[][] visited, int posRow, int posColumn, char target) {
        Queue<int[]> q = new ArrayDeque<>();
        visited[posRow][posColumn] = true;
        q.offer(new int[] { posRow, posColumn });

        while (!q.isEmpty()) {
            int[] n = q.poll();

            for (int[] delta : movementDelta) {
                int curRow = n[0] + delta[0];
                int curColumn = n[1] + delta[1];

                if (curRow < 0 || curRow >= N ||
                curColumn < 0 || curColumn >= N ||
                visited[curRow][curColumn] ||
                map[curRow][curColumn] != target) continue;

                visited[curRow][curColumn] = true;
                q.offer(new int[] { curRow, curColumn });
            }
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

    public String readLine() throws IOException {
        String str = br.readLine();
        st = new StringTokenizer(str);

        return str;
    }

    public boolean hasMoreTokens() {
        return st.hasMoreTokens();
    }
}
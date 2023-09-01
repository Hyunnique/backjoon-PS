import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 30.
 * @see BoJ 1600 (G3)
 * @category DP
 * @note 말이 되고픈 원숭이
 *
 * 벽 부수고 이동하기와 동일한 방식으로
 * 말의 방식으로 몇번 이동했는지를 포함한 visited 배열을 통해
 * 모든 경우의 수 탐색하기
 *
 */
class Position {
    int depth, row, column;

    Position(int depth, int row, int column) {
        this.depth = depth;
        this.row = row;
        this.column = column;
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static int K, W, H;
    static int[][] map;
    static int[][][] arr;
    static int[][] movementDelta = { { 1, -2, -1 }, { 1, -2, 1 }, { 1, -1, -2 }, { 1, -1, 2 }, { 1, 1, -2 }, { 1, 1, 2 }, { 1, 2, -1 }, { 1, 2, 1 },
                                     { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 } };
    static final int INF = 1000000;

    public static void main(String[] args) throws IOException {

        K = sc.nextInt();
        W = sc.nextInt();
        H = sc.nextInt();

        map = new int[H][W];
        arr = new int[31][H][W];

        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < H; j++) {
                Arrays.fill(arr[i][j], INF);
            }
        }

        for (int row = 0; row < H; row++) {
            for (int column = 0; column < W; column++) {
                map[row][column] = sc.nextInt();
            }
        }

        bfs();

        int min = INF;

        for (int i = 0; i <= K; i++) {
            min = Math.min(min, arr[i][H - 1][W - 1]);
        }

        if (min == INF) System.out.println(-1);
        else System.out.println(min);
    }

    static void bfs() {
        Queue<Position> q = new ArrayDeque<>();
        q.offer(new Position(0, 0, 0));
        arr[0][0][0] = 0;

        int curDepth, curRow, curColumn;

        while (!q.isEmpty()) {
            Position cur = q.poll();

            for (int[] delta : movementDelta) {
                curDepth = cur.depth + delta[0];
                curRow = cur.row + delta[1];
                curColumn = cur.column + delta[2];

                if (curDepth > K || curRow < 0 || curRow >= H || curColumn < 0 || curColumn >= W) continue;
                if (map[curRow][curColumn] == 1) continue;
                if (arr[cur.depth][cur.row][cur.column] + 1 >= arr[curDepth][curRow][curColumn]) continue;

                arr[curDepth][curRow][curColumn] = arr[cur.depth][cur.row][cur.column] + 1;
                q.offer(new Position(curDepth, curRow, curColumn));
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
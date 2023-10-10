import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 10.
 * @see BoJ 17144 (G4)
 * @category BFS
 * @note 미세먼지 안녕!
 *
 * BFS 구현
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int R, C, T;
    static int[][] movementDelta = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    static int[][] movementDelta2 = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    public static void main(String[] args) throws IOException {
        R = sc.nextInt();
        C = sc.nextInt();
        T = sc.nextInt();

        int[][] map = new int[R][C];
        int cleanerPosition = -1;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map[i][j] = sc.nextInt();
                if (map[i][j] == -1 && cleanerPosition == -1) {
                    cleanerPosition = i;
                }
            }
        }

        for (int i = 0; i < T; i++) {
            map = spread(map);
            map = clean(map, cleanerPosition);
        }

        int result = 0;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] != -1) result += map[i][j];
            }
        }

        System.out.println(result);
    }

    static int[][] spread(int[][] map) {
        int[][] newMap = new int[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] < 5) {
                    newMap[i][j] += map[i][j];
                    continue;
                }

                int spreadValue = map[i][j] / 5;
                int spreadLeft = map[i][j];

                for (int[] delta : movementDelta) {
                    int curR = i + delta[0];
                    int curC = j + delta[1];

                    if (curR < 0 || curR >= R || curC < 0 || curC >= C || map[curR][curC] == -1) continue;

                    newMap[curR][curC] += spreadValue;
                    spreadLeft -= spreadValue;
                }

                newMap[i][j] += spreadLeft;
            }
        }

        return newMap;
    }

    static int[][] clean(int[][] map, int cleanerPosition) {

        // 위쪽 공기청정기
        int curR = cleanerPosition;
        int curC = 0;
        int curDelta = 0;

        while (true) {
            if (curR + movementDelta[curDelta][0] < 0 || curR + movementDelta[curDelta][0] > cleanerPosition ||
                    curC + movementDelta[curDelta][1] < 0 || curC + movementDelta[curDelta][1] >= C) curDelta = (curDelta + 1) % 4;

            swap(map, curR, curC, curR + movementDelta[curDelta][0], curC + movementDelta[curDelta][1]);

            if (curR + movementDelta[curDelta][0] == cleanerPosition && curC + movementDelta[curDelta][1] == 0) {
                map[curR][curC] = 0;
                break;
            }

            curR += movementDelta[curDelta][0];
            curC += movementDelta[curDelta][1];
        }

        curR = cleanerPosition + 1;
        curC = 0;
        curDelta = 0;

        while (true) {
            if (curR + movementDelta2[curDelta][0] < cleanerPosition + 1 || curR + movementDelta2[curDelta][0] >= R ||
                    curC + movementDelta2[curDelta][1] < 0 || curC + movementDelta2[curDelta][1] >= C) curDelta = (curDelta + 1) % 4;

            swap(map, curR, curC, curR + movementDelta2[curDelta][0], curC + movementDelta2[curDelta][1]);

            if (curR + movementDelta2[curDelta][0] == cleanerPosition + 1 && curC + movementDelta2[curDelta][1] == 0) {
                map[curR][curC] = 0;
                break;
            }

            curR += movementDelta2[curDelta][0];
            curC += movementDelta2[curDelta][1];
        }

        return map;
    }

    static void swap(int[][] map, int r1, int c1, int r2, int c2) {
        int t = map[r1][c1];
        map[r1][c1] = map[r2][c2];
        map[r2][c2] = t;
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
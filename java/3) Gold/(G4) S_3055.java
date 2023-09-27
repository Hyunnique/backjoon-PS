import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 27.
 * @see BoJ 3055 (G4)
 * @category BFS
 * @note 탈출
 *
 * 맵 최소시간 탐색 = BFS
 * time마다 물 먼저 BFS 돌리고 나서 고슴도치 BFS 돌리면 된다
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int R, C;
    static char[][] map;
    static int[][] movementDelta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    static class Position {
        int row, column;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        R = sc.nextInt();
        C = sc.nextInt();

        Queue<Position> waterQueue = new ArrayDeque<>();
        Queue<Position> playerQueue = new ArrayDeque<>();

        map = new char[R][C];
        boolean[][] waterVisited = new boolean[R][C];
        boolean[][] playerVisited = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            String str = sc.next();
            for (int j = 0; j < C; j++) {
                map[i][j] = str.charAt(j);

                if (map[i][j] == 'S') {
                    map[i][j] = '.';
                    playerQueue.offer(new Position(i, j));
                    playerVisited[i][j] = true;
                }
                else if (map[i][j] == '*') {
                    waterQueue.offer(new Position(i, j));
                    waterVisited[i][j] = true;
                }
            }
        }

        int time = 0, playerSize = -1, waterSize = -1;
        int curR = -1, curC = -1;
        int result = -1;

        outer:
        while (!playerQueue.isEmpty()) {
            ++time;
            playerSize = playerQueue.size();
            waterSize = waterQueue.size();

            while (waterSize-- > 0) {
                Position current = waterQueue.poll();

                for (int[] delta : movementDelta) {
                    curR = current.row + delta[0];
                    curC = current.column + delta[1];

                    if (curR < 0 || curR >= R || curC < 0 || curC >= C ||
                            waterVisited[curR][curC] ||
                            map[curR][curC] == 'X' || map[curR][curC] == 'D') continue;

                    waterVisited[curR][curC] = true;
                    waterQueue.offer(new Position(curR, curC));
                }
            }

            while (playerSize-- > 0) {
                Position current = playerQueue.poll();

                for (int[] delta : movementDelta) {
                    curR = current.row + delta[0];
                    curC = current.column + delta[1];

                    if (curR < 0 || curR >= R || curC < 0 || curC >= C ||
                            playerVisited[curR][curC] || waterVisited[curR][curC] ||
                            map[curR][curC] == 'X') continue;

                    if (map[curR][curC] == 'D') {
                        result = time;
                        break outer;
                    }

                    playerVisited[curR][curC] = true;
                    playerQueue.offer(new Position(curR, curC));
                }
            }
        }

        if (result == -1) System.out.println("KAKTUS");
        else System.out.println(result);
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
import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 18.
 * @see BoJ 1987 (G4)
 * @category DFS
 * @note 알파벳
 *
 * 그냥 visited 배열과 어떤 알파벳을 썼는지를 관리하는
 * 두 개의 배열을 써서 DFS 돌면 됨
 * 
 */

class Point {
    int row, column;

    Point(int row, int column) {
        this.row = row;
        this.column = column;
    }
}

public class Main {
    static int R, C;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int[][] movementDelta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static boolean[][] visited;
    static boolean[] alphabet = new boolean[26];
    static char[][] map;
    static int max = 0;
    public static void main(String[] args) throws Exception {

        R = sc.nextInt();
        C = sc.nextInt();

        map = new char[R][C];
        visited = new boolean[R][C];

        for (int row = 0; row < R; row++) {
            String str = sc.next();
            for (int column = 0; column < C; column++) {
                map[row][column] = str.charAt(column);
            }
        }

        alphabet[map[0][0] - 'A'] = true;
        visited[0][0] = true;
        dfs(0, 0, 1);

        System.out.println(max);
    }

    static void dfs(int curRow, int curColumn, int size) {

        boolean isMoved = false;
        for (int[] delta : movementDelta) {
            int moveRow = curRow + delta[0];
            int moveColumn = curColumn + delta[1];

            if (moveRow < 0 || moveRow >= R || moveColumn < 0 || moveColumn >= C
            || visited[moveRow][moveColumn] || alphabet[map[moveRow][moveColumn] - 'A']) continue;

            visited[moveRow][moveColumn] = true;
            alphabet[map[moveRow][moveColumn] - 'A'] = true;
            
            dfs(moveRow, moveColumn, size + 1);

            visited[moveRow][moveColumn] = false;
            alphabet[map[moveRow][moveColumn] - 'A'] = false;
            isMoved = true;
        }

        if (!isMoved) {
            max = Math.max(max, size);
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
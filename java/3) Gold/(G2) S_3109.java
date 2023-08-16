import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 16.
 * @see BoJ 3109 (G2)
 * @category Greedy
 * @note 빵집
 * 
 * 1st try)
 * 일단 그리디로 접근해서 
 * 
 */

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
    static int N, R, C;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static String[] map;
    static int[] chosen;
    static boolean[][] visited;
    static boolean[] end;

    static int result = 0;
    public static void main(String[] args) throws Exception {

        R = sc.nextInt();
        C = sc.nextInt();

        map = new String[R];
        visited = new boolean[R][C];
        end = new boolean[R];

        for (int i = 0; i < R; i++) {
            map[i] = sc.readLine();
        }

        chosen = new int[C];

        for (int far = 0; far < R / 2; far++) {
            visited[far][0] = true;
            visited[R - far - 1][0] = true;
            dfs(true, far, far, 0);
            dfs(false, R - far - 1, R - far - 1, 0);
        }

        System.out.println(result);
    }

    static void dfs(boolean side, final int startRow, int row, int column) {
        if (end[startRow]) return;
        
        if (column == C - 1) {
            // 도착!
            
            end[startRow] = true;
            chosen[column] = row;
            result++;

            return;
        }

        chosen[column] = row;

        if (side) {
            for (int i = -1; i <= 1; i++) {
                if (end[startRow]) return;

                if (row + i >= 0 && row + i < R && map[row + i].charAt(column + 1) == '.' && !visited[row + i][column + 1]) {
                    visited[row + i][column + 1] = true;
                    dfs(side, startRow, row + i, column + 1);
                }
            }
        } else {
            for (int i = 1; i >= -1; i--) {
                if (end[startRow]) return;

                if (row + i >= 0 && row + i < R && map[row + i].charAt(column + 1) == '.' && !visited[row + i][column + 1]) {
                    visited[row + i][column + 1] = true;
                    dfs(side, startRow, row + i, column + 1);
                }
            }
        }
    }
}

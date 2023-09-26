import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 26.
 * @see BoJ 2239 (G4)
 * @category Backtracking
 * @note 스도쿠
 *
 * 각 열, 행, 사각형에 이미 사용한 수를 Bitmasking으로 기억해두고 백트래킹 탐색
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int[] row, column, square;
    static int[][] map;
    static boolean solved = false;

    static int bitmask(int n) {
        if (n == 0) return 0;
        else return (1 << (n - 1));
    }

    public static void main(String[] args) throws IOException {
        row = new int[9];
        column = new int[9];
        square = new int[9];
        map = new int[9][9];

        for (int i = 0; i < 9; i++) {
            String str = sc.next();
            for (int j = 0; j < 9; j++) {
                int bit = bitmask(str.charAt(j) - '0');
                row[i] |= bit;
                column[j] |= bit;
                square[(i / 3) * 3 + (j / 3)] |= bit;

                map[i][j] = str.charAt(j) - '0';
            }
        }

        solve(0);
        System.out.println(sb);
    }

    static void solve(int n) {
        if (solved) return;

        if (n == 9 * 9) {
            solved = true;

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }

            return;
        }

        int i = n / 9;
        int j = n % 9;

        if (map[i][j] == 0) {
            for (int x = 1; x <= 9; x++) {
                //solved = true;

                if ((row[i] & bitmask(x)) == 0 &&
                        (column[j] & bitmask(x)) == 0 &&
                        (square[(i / 3) * 3 + (j / 3)] & bitmask(x)) == 0) {
                    map[i][j] = x;
                    row[i] |= bitmask(x);
                    column[j] |= bitmask(x);
                    square[(i / 3) * 3 + (j / 3)] |= bitmask(x);

                    solve(n + 1);

                    map[i][j] = 0;
                    row[i] ^= bitmask(x);
                    column[j] ^= bitmask(x);
                    square[(i / 3) * 3 + (j / 3)] ^= bitmask(x);
                }
            }
        }
        else solve(n + 1);
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
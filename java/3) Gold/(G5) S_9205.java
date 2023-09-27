import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 27.
 * @see BoJ 9205 (G5)
 * @category .
 * @note 맥주 마시면서 걸어가기
 *
 * 맥주는 앉아서 마시자
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static class Position {
        int row, column;
        boolean visited = false;
        boolean dest = false;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        int T = sc.nextInt();

        for (int tc = 0; tc < T; tc++) {
            int N = sc.nextInt();
            Position[] conv = new Position[N + 1];

            Position home = new Position(sc.nextInt(), sc.nextInt());

            for (int i = 0; i < N; i++) {
                conv[i] = new Position(sc.nextInt(), sc.nextInt());
            }

            conv[N] = new Position(sc.nextInt(), sc.nextInt());
            conv[N].dest = true;

            Queue<Position> queue = new ArrayDeque<>();
            queue.offer(home);
            home.visited = true;

            String result = "sad";

            outer:
            while (!queue.isEmpty()) {
                Position cur = queue.poll();

                for (Position c : conv) {
                    if (!c.visited && Math.abs(c.row - cur.row) + Math.abs(c.column - cur.column) <= 1000) {
                        if (c.dest) {
                            result = "happy";
                            break outer;
                        }

                        c.visited = true;
                        queue.offer(c);
                    }
                }
            }

            sb.append(result).append("\n");
        }

        System.out.print(sb);
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
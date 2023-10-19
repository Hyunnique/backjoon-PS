import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 19.
 * @see BoJ 9626 (S5)
 * @category Implement
 * @note 크로스워드 퍼즐
 *
 * 구현
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int M = sc.nextInt();
        int N = sc.nextInt();

        int U = sc.nextInt();
        int L = sc.nextInt();
        int R = sc.nextInt();
        int D = sc.nextInt();

        char[][] map = new char[M + U + D][N + L + R];
        char[] arr = { '#', '.' };

        for (int i = 0; i < M + U + D; i++) {
            String str = null;
            if (i >= U && i < M + U) str = sc.next();

            for (int j = 0; j < N + L + R; j++) {
                if (i >= U && i < M + U && j >= L && j < N + L) {
                    map[i][j] = str.charAt(j - L);
                } else {
                    map[i][j] = arr[(i + j) % 2];
                }
            }
        }

        for (int i = 0; i < M + U + D; i++) {
            for (int j = 0; j < N + L + R; j++) {
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }

        System.out.println(sb);
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
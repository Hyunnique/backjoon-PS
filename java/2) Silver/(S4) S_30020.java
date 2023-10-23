import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 23.
 * @see BoJ 30020 (S4)
 * @category Math
 * @note 치즈버거 만들기 2
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int A = sc.nextInt();
        int B = sc.nextInt();

        if (A - B < 1) {
            System.out.println("NO");
            return;
        }
        else {
            sb.append("YES").append("\n");

            int cnt = A - B - 1;
            sb.append(cnt + 1).append("\n");
            for (int i = 0; i < cnt; i++) {
                sb.append("aba").append("\n");
                A -= 2;
                B--;

                if (A < 2 || B < 1) {
                    System.out.println("NO");
                    return;
                }
            }

            for (int i = 0; i < B; i++) {
                sb.append("ab");
            }

            sb.append("a");
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
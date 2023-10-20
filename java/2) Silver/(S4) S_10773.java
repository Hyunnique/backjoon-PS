import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 20.
 * @see BoJ 10773 (S4)
 * @category Implement
 * @note 제로
 *
 * 구현, 스택
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int K = sc.nextInt();

        Stack<Integer> stack = new Stack<>();
        long result = 0;
        for (int i = 0; i < K; i++) {
            int cur = sc.nextInt();

            if (cur == 0) {
                result -= stack.pop();
            } else {
                stack.push(cur);
                result += cur;
            }
        }

        System.out.println(result);
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
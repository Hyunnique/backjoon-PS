import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 26.
 * @see BoJ 9436 (S3)
 * @category Implement, Queue
 * @note Round Robin
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        while (true) {
            int N = sc.nextInt();

            if (N == 0) break;

            int T = sc.nextInt();

            Queue<Integer> queue = new ArrayDeque<>();

            for (int i = 0; i < N; i++) queue.offer(0);

            int last = -1;
            while (true) {
                for (int i = 0; i < T; i++) {
                    int next = queue.poll() + 1;
                    if (i != T - 1) queue.offer(next);
                }

                last = queue.peek();
                boolean check = true;
                for (int i = 0; i < queue.size(); i++) {
                    int x = queue.poll();
                    if (x != last) check = false;
                    queue.offer(x);
                }

                if (check) break;
            }

            sb.append(queue.size()).append(" ").append(last).append("\n");
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
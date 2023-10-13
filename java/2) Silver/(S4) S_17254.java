import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 13.
 * @see BoJ 17254 (S4)
 * @category Sorting
 * @note 키보드 이벤트
 *
 * PQ 이용해서 정렬하기
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static class KeyboardEvent implements Comparable<KeyboardEvent> {
        int index, time;
        char value;

        public KeyboardEvent(int index, int time, char value) {
            this.index = index;
            this.time = time;
            this.value = value;
        }

        @Override
        public int compareTo(KeyboardEvent o) {
            if (time == o.time) {
                return Integer.compare(index, o.index);
            } else {
                return Integer.compare(time, o.time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();
        int M = sc.nextInt();

        PriorityQueue<KeyboardEvent> pq = new PriorityQueue<>();

        for (int i = 0; i < M; i++) {
            pq.offer(new KeyboardEvent(sc.nextInt(), sc.nextInt(), sc.nextChar()));
        }

        while (!pq.isEmpty()) {
            sb.append(pq.poll().value);
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
import java.io.*;
import java.util.*;

/*
 BoJ 1019
 책 페이지 (G1)
 */

public class Main {
        static BufferedScanner sc = new BufferedScanner();
        static StringBuilder sb = new StringBuilder();
        static int N;
        static int[] arr;

        public static void main(String[] args) throws Exception {
                N = sc.nextInt();

                int digit = 1;
                int current = 1;

                arr = new int[10];

                while (current <= N) {
                        while (current % 10 != 0 && current <= N) {
                                doCount(current, digit);
                                current++;
                        }

                        while (N % 10 != 9 && current <= N) {
                                doCount(N, digit);
                                N--;
                        }

                        if (current > N) break;

                        current /= 10;
                        N /= 10;

                        for (int i = 0; i < 10; i++) {
                                arr[i] += (N - current + 1) * digit;
                        }

                        digit *= 10;
                }

                for (int x : arr) {
                        sb.append(x).append(" ");
                }

                System.out.println(sb);
        }

        static void doCount(int current, int digit) {
                while (current > 0) {
                        arr[current % 10] += digit;
                        current /= 10;
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
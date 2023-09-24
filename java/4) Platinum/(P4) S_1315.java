import java.io.*;
import java.util.*;

/*
 BoJ 1315 (P4)
 RPG
 */

public class Main {
        static BufferedScanner sc = new BufferedScanner();
        static StringBuilder sb = new StringBuilder();

        static int T, N, M;

        static class SegmentTree {
                int[] tree;

                SegmentTree(int size) {
                        tree = new int[size];
                }

                int init(int node, int start, int end) {
                        if (start == end) {
                                if (start < M) return tree[node] = 0;
                                else return tree[node] = 1;
                        }

                        int mid = (start + end) / 2;
                        tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);

                        return tree[node];
                }

                int sum(int node, int start, int end, int left, int right) {
                        if (right < start || end < left) return 0;
                        if (left <= start && right >= end) return tree[node];

                        int mid = (start + end) / 2;
                        return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);
                }

                void update(int node, int start, int end, int idx, int diff) {
                        if (idx < start || end < idx) return;

                        tree[node] += diff;

                        if (start == end) return;

                        int mid = (start + end) / 2;

                        update(node * 2, start, mid, idx, diff);
                        update(node * 2 + 1, mid + 1, end, idx, diff);
                }
        }

        public static void main(String[] args) throws Exception {
                T = sc.nextInt();

                for (int test_case = 0; test_case < T; test_case++) {
                        N = sc.nextInt();
                        M = sc.nextInt();

                        int[] arr = new int[N + 1];
                        for (int i = 1; i <= N; i++) arr[i] = i + M - 1;

                        SegmentTree segTree = new SegmentTree(4 * (N + M));
                        segTree.init(1, 0, N + M - 1);

                        for (int i = 0; i < M; i++) {
                                int A = sc.nextInt();
                                int index = arr[A];

                                sb.append(segTree.sum(1, 0, N + M - 1, 0, index - 1)).append(" ");

                                segTree.update(1, 0, N + M - 1, index, -1);
                                segTree.update(1, 0, N + M - 1, M - 1 - i, 1);
                                arr[A] = M - 1 - i;
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

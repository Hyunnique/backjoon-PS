import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 14.
 * @see BoJ 10999 (P4)
 * @category Segment Tree
 * @note 구간 합 구하기 2
 *
 * 수의 변경이 빈번히 일어나는 구간합 구하기
 * = 세그먼트 트리 Standard 문제
 *
 * but 변경이 일어나는 노드가 단일 노드가 아닌 구간
 * -> 모든 구간 노드에 대한 갱신을 실행하면 비효율적이므로 Lazy Propagation
 */

class SegmentTree {
    long[] tree;
    long[] lazy;
    int treeSize;

    public SegmentTree(int size) {
        int h = (int) Math.ceil(Math.log(size) / Math.log(2));

        this.treeSize = (int) Math.pow(2, h+1);
        tree = new long[treeSize];
        lazy = new long[treeSize];
    }

    public long init(long[] arr, int node, int start, int end) {
        if (start == end) return tree[node] = arr[start];

        return tree[node] = init(arr, node * 2, start, (start + end) / 2)
                + init(arr, node * 2 + 1, (start + end) / 2 + 1, end);
    }

    public void updateRange(int left, int right, int start, int end, int node, long diff) {
        updateLazy(start, end, node);
        if (right < start || end < left) return;
        else if (left <= start && end <= right) {
            tree[node] += (end - start + 1) * diff;

            if (start != end) {
                lazy[node * 2] += diff;
                lazy[node * 2 + 1] += diff;
            }
        } else {
            updateRange(left, right, start, (start + end) / 2, node * 2, diff);
            updateRange(left, right, (start + end) / 2 + 1, end, node * 2 + 1, diff);
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }
    }

    public void updateLazy(int start, int end, int node) {
        if (lazy[node] != 0) {
            tree[node] += (end - start + 1) * lazy[node];

            if (start != end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }

            lazy[node] = 0;
        }
    }
    public long sum(int node, int start, int end, int left, int right) {

        updateLazy(start, end, node);

        if (left > end || right < start) return 0;
        if (left <= start && end <= right) return tree[node];

        return sum(node * 2, start, (start + end) / 2, left, right) +
                sum(node * 2 + 1, (start + end) / 2 + 1, end, left, right);
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        SegmentTree segTree = new SegmentTree(N);
        long[] arr = new long[N + 1];

        for (int i = 1; i < N + 1; i++) {
            arr[i] = sc.nextLong();
        }

        segTree.init(arr, 1, 1, N);

        for (int i = 0; i < M + K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            if (a == 1) {
                long d = sc.nextLong();
                segTree.updateRange(b, c, 1, N, 1, d);
            } else {
                sb.append(segTree.sum(1, 1, N, b, c)).append("\n");
            }
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
import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 19.
 * @see BoJ 11505 (G1)
 * @category Segment Tree
 * @note 구간 곱 구하기
 *
 *       수의 변경이 빈번히 일어나는 구간곱 구하기
 *       구간합 구하기에서 구간 곱으로 바뀌는데, 리프노드가 아니면 곱하기 전의 값이 무엇인지, 얼마나 달라져야 하는지
 *       알 방법이 없어서 Bottom-up으로 구현해야 한다
 *
 */

class SegmentTree {
    long tree[];
    int treeSize;
    int startIndex;
    long DIVIDER = 1_000_000_007L;

    public SegmentTree(int size) {
        int h = (int) Math.ceil(Math.log(size) / Math.log(2));

        this.treeSize = (int) Math.pow(2, h + 1);
        this.startIndex = (int) Math.pow(2, h);

        tree = new long[treeSize];
        Arrays.fill(tree, -1L);
    }

    public long init(long[] arr, int node, int start, int end) {
        if (start == end)
            return tree[node] = arr[start];
        else {
            int mid = (start + end) / 2;
            return tree[node] = (init(arr, node * 2, start, mid) * init(arr, node * 2 + 1, mid + 1, end)) % DIVIDER;
        }
    }

    public long update(int node, int start, int end, int idx, int value) {
        if (end < idx || idx < start)
            return tree[node];
        else if (start == end)
            return tree[node] = value;

        int mid = (start + end) / 2;
        return tree[node] = (update(node * 2, start, mid, idx, value) * update(node * 2 + 1, mid + 1, end, idx, value))
                % DIVIDER;
    }

    public long sum(int node, int start, int end, int left, int right) {
        if (end < left || right < start)
            return 1;
        if (left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;
        return (sum(node * 2, start, mid, left, right) * sum(node * 2 + 1, mid + 1, end, left, right)) % DIVIDER;
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

        for (int i = 0; i < N; i++) {
            arr[i + 1] = sc.nextLong();
        }

        segTree.init(arr, 1, 1, N);

        // System.out.println(Arrays.toString(segTree.tree));

        for (int i = 0; i < M + K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            if (a == 1) {
                segTree.update(1, 1, N, b, c);
            } else {
                sb.append(segTree.sum(1, 1, N, b, c)).append("\n");
            }
        }

        System.out.print(sb);
        // System.out.println(Arrays.toString(segTree.tree));
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
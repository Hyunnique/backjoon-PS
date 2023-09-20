import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 20.
 * @see BoJ 2357 (G1)
 * @category Segment Tree
 * @note 최솟값과 최댓값
 *
 * N (1 <= N <= 100,000) 개의 정수 중에서,
 * [a, b] 에 해당하는 구간에서의 최솟값 혹은 최댓값을 구하기
 * 쿼리의 개수가 M (1 <= M <= 100,000) 개이므로 세그먼트 트리를 이용해서
 * 최소, 최댓값 저장
 *
 */

class Node {
    int min, max;

    Node() {

    }

    Node(int value) {
        min = value;
        max = value;
    }

    @Override
    public String toString() {
        return "min " + min + " max " + max;
    }
}

class SegmentTree {
    Node[] tree;
    int treeSize;
    int[] values;

    public SegmentTree(int size) {
        int h = (int) Math.ceil(Math.log(size) / Math.log(2));

        this.treeSize = (int) Math.pow(2, h+1);

        tree = new Node[treeSize];
        values = new int[size + 1];
    }

    public int min(Node left, Node right) {
        if (left == null) return right.min;
        else if (right == null) return left.min;
        else return Math.min(left.min, right.min);
    }

    public int max(Node left, Node right) {
        if (left == null) return right.max;
        else if (right == null) return left.max;
        else return Math.max(left.max, right.max);
    }

    public Node init(int node, int start, int end) {
        if (start == end) return tree[node] = new Node(values[start]);

        int mid = (start + end) / 2;

        Node left = init(node * 2, start, mid);
        Node right = init(node * 2 + 1, mid + 1, end);

        if (tree[node] == null) tree[node] = new Node();
        tree[node].min = min(left, right);
        tree[node].max = max(left, right);

        return tree[node];
    }

    public Node update(int node, int start, int end, int idx, int value) {
        if (end < idx || idx < start) return tree[node];
        else if (start == end) return tree[node];

        int mid = (start + end) / 2;

        Node left = update(node * 2, start, mid, idx, value);
        Node right = update(node * 2 + 1, mid + 1, end, idx, value);

        if (tree[node] == null) tree[node] = new Node();
        tree[node].min = min(left, right);
        tree[node].max = min(left, right);

        return tree[node];
    }

    public int sum(int node, int start, int end, int left, int right, boolean isMin) {
        if (end < left || right < start) return (isMin ? Integer.MAX_VALUE : 0);
        if (left <= start && end <= right) return (isMin ? tree[node].min : tree[node].max);

        int mid = (start + end) / 2;

        int sLeft = sum(node * 2, start, mid, left, right, isMin);
        int sRight = sum(node * 2 + 1, mid + 1, end, left, right, isMin);

        if (tree[node] == null) tree[node] = new Node();

        if (isMin) {
            return Math.min(sLeft, sRight);
        } else {
            return Math.max(sLeft, sRight);
        }
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        int N = sc.nextInt();
        int M = sc.nextInt();
        SegmentTree segTree = new SegmentTree(N);

        for (int i = 0; i < N; i++) {
            segTree.values[i + 1] = sc.nextInt();
        }

        segTree.init(1, 1, N);

        //System.out.println(Arrays.toString(segTree.tree));

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            // 두번씩 질의하는게 굉장히 비효율적이지만 맞으니까 넘어간다..
            sb.append(segTree.sum(1, 1, N, a, b, true)).append(" ");
            sb.append(segTree.sum(1, 1, N, a, b, false)).append("\n");
        }

        System.out.print(sb);
        //System.out.println(Arrays.toString(segTree.indexTree));
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
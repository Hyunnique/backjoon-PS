import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 19.
 * @see BoJ 14428 (G1)
 * @category Segment Tree
 * @note 수열과 쿼리 16
 *
 *       수의 변경이 빈번히 일어나는 수열에서, 특정 구간에서 크기가 가장 작은 값의 인덱스를 구하기
 *       해당 구간의 가장 작은 값의 인덱스를 저장하는 세그먼트 트리 구현
 *
 */

class SegmentTree {
    int[] indexTree;
    int[] values;
    int treeSize;

    public SegmentTree(int size) {
        int h = (int) Math.ceil(Math.log(size) / Math.log(2));

        this.treeSize = (int) Math.pow(2, h + 1);

        indexTree = new int[treeSize];
        values = new int[size + 1];
    }

    public int compare(int left, int right) {
        if (left == -1)
            return right;
        else if (right == -1)
            return left;
        else if (values[left] == values[right])
            return Math.min(left, right);
        else
            return (values[left] < values[right] ? left : right);
    }

    public int init(int node, int start, int end) {
        // System.out.println("node=" + node + ", start=" + start + ", end=" + end);
        if (start == end)
            return indexTree[node] = start;

        int mid = (start + end) / 2;

        int left = init(node * 2, start, mid);
        int right = init(node * 2 + 1, mid + 1, end);

        return indexTree[node] = compare(left, right);
    }

    public int update(int node, int start, int end, int idx, int value) {
        if (end < idx || idx < start)
            return indexTree[node];
        else if (start == end)
            return indexTree[node];

        int mid = (start + end) / 2;

        int left = update(node * 2, start, mid, idx, value);
        int right = update(node * 2 + 1, mid + 1, end, idx, value);

        return indexTree[node] = compare(left, right);
    }

    public int sum(int node, int start, int end, int left, int right) {
        if (end < left || right < start)
            return -1;
        if (left <= start && end <= right)
            return indexTree[node];

        int mid = (start + end) / 2;

        int sLeft = sum(node * 2, start, mid, left, right);
        int sRight = sum(node * 2 + 1, mid + 1, end, left, right);

        return compare(sLeft, sRight);
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        int N = sc.nextInt();
        SegmentTree segTree = new SegmentTree(N);

        for (int i = 0; i < N; i++) {
            segTree.values[i + 1] = sc.nextInt();
        }

        segTree.init(1, 1, N);

        // System.out.println(Arrays.toString(segTree.indexTree));

        int M = sc.nextInt();

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            if (a == 1) {
                segTree.values[b] = c;
                segTree.update(1, 1, N, b, c);
            } else {
                sb.append(segTree.sum(1, 1, N, b, c)).append("\n");
            }
        }

        System.out.print(sb);
        // System.out.println(Arrays.toString(segTree.indexTree));
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
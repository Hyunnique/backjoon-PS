import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 14.
 * @see BoJ 2243 (P5)
 * @category Segment Tree
 * @note 사탕상자
 *
 * 중간에 사탕에 변동이 있음
 * 1. 맛의 순위에 따라서 x번째 순위의 사탕을 꺼내기 -> 이분탐색을 통해 트리의 인덱스 찾기
 * 2. 특정한 맛의 사탕을 n개 넣거나 꺼내기 -> 세그먼트 트리를 이용한 구간합 계산
 */

class SegmentTree {
    int tree[];

    public SegmentTree(int size) {
        tree = new int[4 * size + 1];
    }

    public void update(int node, int start, int end, int target, int diff) {
        if (target < start || end < target) return;

        tree[node] += diff;

        if (start != end) {
            update(node * 2, start, (start + end) / 2, target, diff);
            update(node * 2 + 1, (start + end) / 2 + 1, end, target, diff);
        }
    }

    public int binarySearch(int start, int end, int current, int target) {
        if (start == end) {
            update(1, 1, 1000000, start, -1);
            return start;
        }

        int mid = (start + end) / 2;

        if (target <= tree[current * 2]) return binarySearch(start, mid, current * 2, target);
        else return binarySearch(mid + 1, end, current * 2 + 1, target - tree[current * 2]);
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static final int SIZE = 1_000_000;

    public static void main(String[] args) throws IOException {

        int N = sc.nextInt();

        SegmentTree segTree = new SegmentTree(SIZE);

        for (int i = 0; i < N; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            if (a == 1) {
                sb.append(segTree.binarySearch(1, SIZE, 1, b)).append("\n");
            } else {
                int c = sc.nextInt();
                segTree.update(1, 1, SIZE, b, c);
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
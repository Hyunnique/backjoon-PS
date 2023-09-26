import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 26.
 * @see BoJ 3066 (G2)
 * @category LIS
 * @note 브리징 시그널
 *
 * 가장 긴 증가하는 부분수열 2와 동일
 * (LIS + Binary Search Standard 문제)
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int N;
    static int[] arr;

    static int binarySearch(int left, int right, int n) {
        while (left < right) {
            int mid = (left + right) / 2;

            if (arr[mid] < n) left = mid + 1;
            else right = mid;
        }

        return right;
    }

    public static void main(String[] args) throws IOException {

        int T = sc.nextInt();

        for (int tc = 0; tc < T; tc++) {
            N = sc.nextInt();

            arr = new int[N];

            int len = 1;
            arr[0] = sc.nextInt();

            for (int i = 1; i < N; i++) {
                int x = sc.nextInt();

                if (arr[len - 1] < x) {
                    arr[len++] = x;
                } else {
                    int idx = binarySearch(0, len - 1, x);
                    arr[idx] = x;
                }
            }

            sb.append(len).append("\n");
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 16.
 * @see BoJ 1992 (S1)
 * @category Divide & Conquer
 * @note 쿼드 트리
 * 
 * 사분면으로 분할정복
 * 
 */

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

    public char nextChar() throws IOException {
        return next().charAt(0);
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

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static char[][] arr;
    public static void main(String[] args) throws Exception {

        N = sc.nextInt();

        arr = new char[N][N];

        for (int i = 0; i < N; i++) {
            int cursor = 0;
            for (char c : sc.readLine().toCharArray()) {
                arr[i][cursor++] = c;
            }
        }

        System.out.println(DC(N, 0, 0));
    }

    static String DC(int size, int curRow, int curColumn) {
        if (size == 1) {
            return String.valueOf(arr[curRow][curColumn]);
        }

        String s1 = DC(size / 2, curRow, curColumn);
        String s2 = DC(size / 2, curRow, curColumn + size / 2);
        String s3 = DC(size / 2, curRow + size / 2, curColumn);
        String s4 = DC(size / 2, curRow + size / 2, curColumn + size / 2);

        if ((s1.equals("0") || s1.equals("1")) && s1.equals(s2) && s3.equals(s4) && s1.equals(s3)) {
            return s1;
        } else {
            StringBuilder ss = new StringBuilder();
            ss.append("(").append(s1).append(s2).append(s3).append(s4).append(")");
            return ss.toString();
        }
    }
}

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
 * @since 2023. 08. 14.
 * @see BoJ 1074
 * @category Divide & Conquer
 * @note Z
 * 
 * 재귀를 이용해 4등분을 반복해서 탐색
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
    static int cnt = 0;
    static int r, c;
    static boolean matched = false;
    public static void main(String[] args) throws Exception {

        N = sc.nextInt();
        r = sc.nextInt();
        c = sc.nextInt();

        DC((int) Math.pow(2, N), 0, 0);
        
        //System.out.println(sb.toString());
    }

    static void DC(int size, int curRow, int curColumn) {
        if (matched) return;
        if (size == 1) {
            ++cnt;
            if (curRow == r && curColumn == c) {
                matched = true;
                System.out.println(cnt - 1);
            }

            return;
        }

        if (r < curRow + size / 2) {
            if (c < curColumn + size / 2) {
                DC(size / 2, curRow, curColumn);
            } else {
                cnt += (int) Math.pow(size / 2, 2);
            }

            DC(size / 2, curRow, curColumn + size / 2);
        } else {
            cnt += (int) Math.pow(size / 2, 2) * 2;
        }

        if (c < curColumn + size / 2) {
            DC(size / 2, curRow + size / 2, curColumn);
        } else {
            cnt += (int) Math.pow(size / 2, 2);
        }

        DC(size / 2, curRow + size / 2, curColumn + size / 2);
    }
}

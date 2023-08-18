import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 18.
 * @see BoJ 1891 (G4)
 * @category Divide and Conquer ?
 * @note 사분면
 *
 * 
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
    static long curRow = 0, curColumn = 0;
    public static void main(String[] args) throws Exception {

        int d = sc.nextInt();
        String str = sc.next();
        long size = (long) Math.pow(2, d);
        
        dc1(str, 0, size);

        curColumn += Long.parseLong(sc.next());
        curRow -= Long.parseLong(sc.next());

        if (curRow < 0 || curRow >= size || curColumn < 0 || curColumn >= size) {
            System.out.println(-1);
        } else {
            dc2(size, 0, 0);
            System.out.println(sb.toString());
        }
    }

    static void dc1(String str, int cursor, long size) {
        if (cursor >= str.length()) {
            return;
        }

        switch (str.charAt(cursor++)) {
            case '1':
                curColumn += size / 2;
                break;
            case '2':
                break;
            case '3':
                curRow += size / 2;
                break;
            case '4':
                curRow += size / 2;
                curColumn += size / 2;
                break;
        }

        dc1(str, cursor, size / 2);
    }

    static void dc2(long size, long checkRow, long checkColumn) {
        if (size == 1) {
            return;
        }

        if (curRow < checkRow + size / 2 && curColumn >= checkColumn + size / 2) {
            sb.append(1);
            checkColumn += size / 2;
        }
        else if (curRow < checkRow + size / 2 && curColumn < checkColumn + size / 2) {
            sb.append(2);
        }
        else if (curRow >= checkRow + size / 2 && curColumn < checkColumn + size / 2) {
            sb.append(3);
            checkRow += size / 2;
        }
        else if (curRow >= checkRow + size / 2 && curColumn >= checkColumn + size / 2) {
            sb.append(4);
            checkRow += size / 2;
            checkColumn += size / 2;
        }

        dc2(size / 2, checkRow, checkColumn);
    }
}

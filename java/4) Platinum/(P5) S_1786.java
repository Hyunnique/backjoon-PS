import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 20.
 * @see BoJ 1786 (P5)
 * @category KMP
 * @note 찾기
 *
 * KMP 알고리즘 Standard 문제
 *
 */


public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int count = 0;
    static List<Integer> result = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        String original = sc.readLine();
        String pattern = sc.readLine();

        KMP(original, pattern);
        
        sb.append(count).append("\n");
        for (int i = 0; i < count; i++) {
            sb.append(result.get(i)).append(" ");
        }
        
        System.out.println(sb.toString());
    }

    static void KMP(String original, String pattern) {
        int[] f = new int[pattern.length()];
        int idx = 0;

        for (int i = 1; i < pattern.length(); i++) {
            while (idx > 0 && pattern.charAt(i) != pattern.charAt(idx)) idx = f[idx - 1];
            if (pattern.charAt(i) == pattern.charAt(idx)) f[i] = ++idx;
        }

        idx = 0;

        for (int i = 0; i < original.length(); i++) {
            while (idx > 0 && original.charAt(i) != pattern.charAt(idx)) idx = f[idx - 1];
            if (original.charAt(i) == pattern.charAt(idx)) {
                if (idx == pattern.length() - 1) {
                    ++count;
                    result.add(i - idx + 1);
                    idx = f[idx];
                }
                else idx++;
            }
        }
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

    public String readLine() throws IOException {
        String str = br.readLine();
        st = new StringTokenizer(str);

        return str;
    }

    public boolean hasMoreTokens() {
        return st.hasMoreTokens();
    }
}
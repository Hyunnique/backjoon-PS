import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 11.
 * @see BoJ 1629
 * @note
 * 
 * N^M = N^(M/2) * N^(M/2)
 */

class CustomReader {
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
    static long N, C;
    static StringBuilder sb = new StringBuilder();
    static Map<Integer, Long> mem = new HashMap<>();
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        int A = cr.nextInt();
        int B = cr.nextInt();
        C = cr.nextInt();

        System.out.println(multiply(A, B) % C);
    }

    static long multiply(int a, int b) {
        if (b == 1) return a;
        if (mem.containsKey(b)) return mem.get(b);

        long result;
        if (b % 2 == 0) {
            result = ((multiply(a, b / 2) % C) * (multiply(a, b / 2) % C) % C);
        } else {
            result = ((multiply(a, b / 2) % C) * (multiply(a, b / 2 + 1) % C) % C);
        }

        if (!mem.containsKey(b)) mem.put(b, result);
        return result;
    }
}

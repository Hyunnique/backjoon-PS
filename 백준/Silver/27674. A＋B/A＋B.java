import java.util.*;
import java.io.*;

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {

        int N = sc.nextInt();

        for (int i = 0; i < N; i++) {
            sc.br.readLine();
            char[] arr = sc.next().toCharArray();
            Arrays.sort(arr);

            long result = 0;
            for (int j = arr.length - 1; j >= 0; j--) {
                if (j != 0) {
                    result += arr[j] - '0';
                    result *= 10;
                } else {
                    result /= 10;
                    result += arr[j] - '0';
                }
            }

            sb.append(result).append("\n");
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
import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 30.
 * @see BoJ 16991 (G1)
 * @category DP, Bitmasking
 * @note 외판원 순회 3
 *
 * 비트마스킹을 이용한 DP
 *
 */

class Point {
    int row, column;

    Point(int column, int row) {
        this.row = row;
        this.column = column;
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static double[][] memory;
    static Point[] nodes;
    static int INF = 20000000;

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();

        memory = new double[N][1 << N];
        nodes = new Point[N];

        for (int i = 0; i < N; i++) Arrays.fill(memory[i], -1);

        for (int i = 0; i < N; i++) {
            nodes[i] = new Point(sc.nextInt(), sc.nextInt());
        }

        System.out.println(dp(0, 1));
    }

    static double getCost(int a, int b) {
        Point pa = nodes[a];
        Point pb = nodes[b];

        return Math.sqrt(Math.pow(pa.row - pb.row, 2) + Math.pow(pa.column - pb.column, 2));
    }

    static double dp(int before, int visited) {

        if (visited == (1 << N) - 1) {
            if (getCost(before, 0) == 0) {
                return INF;
            }
            else return getCost(before,0);
        }

        if (memory[before][visited] != -1) return memory[before][visited];

        for (int i = 0; i < N; i++) {
            if ((visited & (1 << i)) != 0) continue;
            if (getCost(before, i) == 0) continue;

            double x = dp(i, visited | (1 << i));

            if (x < INF && x != -1) {
                if (memory[before][visited] == -1) {
                    memory[before][visited] = x + getCost(before, i);
                } else {
                    memory[before][visited] = Math.min(memory[before][visited], x + getCost(before, i));
                }
            }
        }

        if (memory[before][visited] == -1) memory[before][visited] = INF;
        return memory[before][visited];
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
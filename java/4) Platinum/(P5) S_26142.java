import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 16.
 * @see BoJ 26142 (P5)
 * @category Greedy
 * @note 꺾이지 않는 마음 1
 *
 * TL 1.5s, ML 1024mb
 * 용의 수 N (1 <= N <= 10,000)
 *
 * 화살을 쏘지 않을 수도 있지만, 안 쏠 이유는 없다고 생각함
 * 무조건 쏘기
 * 
 * 자라는 키 (Growth)로 내림차순 정렬해서 그리디 접근
 *
 */

class Dragon implements Comparable<Dragon> {
    long height, growth;

    public Dragon(long growth, long height) {
        this.height = height;
        this.growth = growth;
    }

    @Override
    public int compareTo(Dragon o) {
        return Long.compare(o.growth, growth);
    }

    @Override
    public String toString() {
        return Long.toString(height);
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static Dragon[] dragons;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();

        dragons = new Dragon[N];
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            dragons[i] = new Dragon(sc.nextLong(), sc.nextLong());
        }

        Arrays.sort(dragons);

        long result = 0;

        for (int i = 0; i < N; i++) {
            int cur = i;
            int resultIndex = 0;
            long heightMax = -1, heightSum = result;

            for (int j = 0; j < N; j++) {
                if (visited[j]) {
                    heightSum += dragons[j].growth;
                    cur -= 1;
                }
                else if (heightMax < heightSum + dragons[j].height + dragons[j].growth * cur) {
                    heightMax = heightSum + dragons[j].height + dragons[j].growth * cur;
                    resultIndex = j;
                }
            }

            visited[resultIndex] = true;
            result = heightMax;
            sb.append(heightMax).append("\n");
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
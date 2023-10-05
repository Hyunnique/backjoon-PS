import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 05.
 * @see BoJ 4485 (G4)
 * @category Dijkstra
 * @note 녹색 옷 입은 애가 젤다지?
 *
 * 시작 -> 도착으로 가는 최단거리 계산 (거리 = 해당 칸의 값)
 *
 */

class Position implements Comparable<Position> {
    int x, y, weight;

    Position(int y, int x, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    @Override
    public int compareTo(Position o) {
        return Integer.compare(this.weight, o.weight);
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static final int INF = 100_000_000;
    static final int[][] movementDelta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {

        int tc = 1;
        while (true) {
            int N = sc.nextInt();

            if (N == 0) break;

            int[][] map = new int[N][N];
            int[][] distances = new int[N][N];

            for (int i = 0; i < N; i++) Arrays.fill(distances[i], INF);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = sc.nextInt();
                }
            }

            PriorityQueue<Position> pq = new PriorityQueue<>();
            pq.offer(new Position(0, 0, map[0][0]));
            //distances[0][0] = 0;

            while (!pq.isEmpty()) {
                Position cur = pq.poll();

                if (distances[cur.y][cur.x] < cur.weight) continue;

                for (int[] delta : movementDelta) {
                    int nextY = cur.y + delta[0];
                    int nextX = cur.x + delta[1];

                    if (nextY < 0 || nextY >= N || nextX < 0 || nextX >= N) continue;

                    int nextDist = cur.weight + map[nextY][nextX];

                    if (nextDist < distances[nextY][nextX]) {
                        distances[nextY][nextX] = nextDist;
                        pq.offer(new Position(nextY, nextX, nextDist));
                    }
                }
            }

            sb.append("Problem ").append(tc++).append(": ").append(distances[N - 1][N - 1]).append("\n");
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
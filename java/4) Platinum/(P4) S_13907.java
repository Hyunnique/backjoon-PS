import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge> {
    int position, count;
    long cost;

    Edge(int position, long cost, int count) {
        this.position = position;
        this.cost = cost;
        this.count = count;
    }

    @Override
    public int compareTo(Edge o) {
        return Long.compare(cost, o.cost);
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int N, M, K, S, D;
    static List<List<Edge>> edgeList = new ArrayList<>();
    static long[][] distances;
    static int INF = 1000000000;
    public static void main(String[] args) throws IOException {

        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        S = sc.nextInt();
        D = sc.nextInt();
        
        distances = new long[N + 1][N + 1];

        for (int i = 0; i < N + 1; i++) {
            edgeList.add(new ArrayList<Edge>());
            Arrays.fill(distances[i], INF);
        }

        for (int i = 0; i < M; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            int cost = sc.nextInt();

            edgeList.get(start).add(new Edge(end, cost, 1));
            edgeList.get(end).add(new Edge(start, cost, 1));
        }

        int[] costIncrease = new int[K + 1];
        for (int i = 1; i < K + 1; i++) {
            costIncrease[i] = sc.nextInt();
        }

        dijkstra(S);

        int idx = N, tmp = N, sum = 0;
        for (int i = 0; i < K + 1; i++) {
            long min = Long.MAX_VALUE;
            sum += costIncrease[i];

            for (int j = 0; j <= idx; j++) {
                if (min > distances[D][j] + j * sum) {
                    tmp = j;
                    min = distances[D][j] + j * sum;
                }
            }

            sb.append(min).append("\n");
            idx = tmp;
        }

        System.out.println(sb.toString());
    }

    static void dijkstra(int start) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0, 0));
        distances[start][0] = 0;

        while (!pq.isEmpty()) {
            Edge current = pq.poll();

            if (distances[current.position][current.count] < current.cost) continue;

            Loop:
            for (Edge next : edgeList.get(current.position)) {
                for (int i = 0; i <= current.count; i++) {
                    if (current.cost + next.cost > distances[next.position][i]) continue Loop;
                }

                if (distances[next.position][current.count + 1] > current.cost + next.cost) {
                    distances[next.position][current.count + 1] = current.cost + next.cost;
                    pq.offer(new Edge(next.position, distances[next.position][current.count + 1], current.count + 1));
                }
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
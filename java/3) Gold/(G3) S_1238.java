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
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 14.
 * @see BoJ 1238 (G3)
 * @category Graph (1 to N)
 * @note 파티
 * 
 * N명의 사람 -> 마을, 마을 -> N명의 사람을 구해야 하는데
 * 다 해볼 필요 없이 N명의 사람 -> 마을 경로를 거꾸로 생각해서
 * 마을에서 가는 경로를 두번 다익스트라 하면 해결
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

class Edge implements Comparable<Edge> {
    int start, end, cost;

    Edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(cost, o.cost);
    }
}

public class Main {   
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int[] distancesToX, distancesFromX;
    static List<List<Edge>> edgesToX, edgesFromX;
    static final int INF = 200000000;

    public static void main(String[] args) throws Exception {

        N = sc.nextInt();
        M = sc.nextInt();
        int X = sc.nextInt();

        edgesToX = new ArrayList<>();
        edgesFromX = new ArrayList<>();

        distancesToX = new int[N + 1];
        distancesFromX = new int[N + 1];

        Arrays.fill(distancesToX, INF);
        Arrays.fill(distancesFromX, INF);

        for (int i = 0; i < N + 1; i++) {
            edgesToX.add(new ArrayList<>());
            edgesFromX.add(new ArrayList<>());
        }

        for (int i = 1; i < M + 1; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            int cost = sc.nextInt();

            // 거꾸로 뒤집어서 마을에서 모든 다른 사람들에게 간다고 생각
            // 사람 -> 마을 오는 데 걸리는 시간
            edgesToX.get(end).add(new Edge(end, start, cost));

            // 원래 마을에서 사람들에게 가는 길
            // 마을 -> 사람 가는 데 걸리는 시간
            edgesFromX.get(start).add(new Edge(start, end, cost));
        }
        
        dijkstra(X, distancesToX, edgesToX);
        dijkstra(X, distancesFromX, edgesFromX);

        int max = -1;

        for (int i = 1; i < N + 1; i++) {
            max = Math.max(max, distancesFromX[i] + distancesToX[i]);
        }
        System.out.println(max);
    }

    static void dijkstra(int start, int[] distances, List<List<Edge>> edges) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        distances[start] = 0;
        pq.offer(new Edge(start, start, 0));

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            
            for (Edge nextE : edges.get(e.end)) {
                if (distances[nextE.end] > distances[e.end] + nextE.cost) {
                    distances[nextE.end] = distances[e.end] + nextE.cost;
                    pq.offer(new Edge(e.end, nextE.end, nextE.cost));
                }
            }
        }
    }
}

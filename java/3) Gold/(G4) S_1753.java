import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 25.
 * @see BoJ 1753 (G4)
 * @category Dijkstra
 * @note 최단경로
 *
 * 다익스트라를 이용해서 최단경로 찾기
 *
 */

class Node implements Comparable<Node> {
    int destination;
    int cost;

    Node(int destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(cost, o.cost);
    }
}
public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int INF = 10000000;

    static List<List<Node>> adjList;
    static int[] distances;
    static boolean[] visited;
    static int V, E;

    public static void main(String[] args) throws Exception {

        V = sc.nextInt();
        E = sc.nextInt();

        int K = sc.nextInt();

        distances = new int[V + 1];
        Arrays.fill(distances, INF);

        adjList = new ArrayList<>();
        visited = new boolean[V + 1];

        for (int i = 0; i < V + 1; i++) adjList.add(new ArrayList<>());

        for (int i = 0; i < E; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            int cost = sc.nextInt();

            adjList.get(start).add(new Node(end, cost));
        }

        dijkstra(K);

        for (int i = 1; i < V + 1; i++) {
            if (distances[i] == INF) sb.append("INF\n");
            else sb.append(distances[i]).append("\n");
        }

        System.out.println(sb.toString());
    }

    static void dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        distances[start] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int dest = cur.destination;

            if (!visited[dest]) {
                visited[dest] = true;

                for (Node n : adjList.get(dest)) {
                    if (!visited[n.destination] && distances[n.destination] > distances[dest] + n.cost) {
                        distances[n.destination] = distances[dest] + n.cost;
                        pq.add(new Node(n.destination, distances[n.destination]));
                    }
                }
            }
        }

        return;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @author Hyunnique
 * @since 2023. 08. 09.
 * @see BoJ 1504 (G4)
 * @category Shortest Path
 * @note
 * 
 * 다익스트라를 이용해서 A-B간의 최단거리 찾기
 * 
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

class Node implements Comparable<Node> {
    int destination;
    int cost;

    Node(int destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node o) {
        return cost - o.cost;
    }
}

public class Main {   
    static int N, E, u, v;
    static StringBuilder sb = new StringBuilder();
    static List<List<Node>> adjList;
    static int[] distances;
    static boolean[] visited;
    static final int INF = 200000000;
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        E = cr.nextInt();

        adjList = new ArrayList<>();
        distances = new int[N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < N + 1; i++) adjList.add(new ArrayList<>());
        
        for (int i = 0; i < E; i++) {
            int start = cr.nextInt();
            int end = cr.nextInt();
            int cost = cr.nextInt();

            adjList.get(start).add(new Node(end, cost));
            adjList.get(end).add(new Node(start, cost));
        }

        u = cr.nextInt();
        v = cr.nextInt();

        int middle = dijkstra(u, v);

        int r1 = dijkstra(1, u) + middle + dijkstra(v, N);
        int r2 = dijkstra(1, v) + middle + dijkstra(u, N);

        int result = (r1 >= INF && r2 >= INF ? -1 : Math.min(r1, r2));
        System.out.println(result);
    }

    static int dijkstra(int start, int end) {
        Arrays.fill(distances, INF);
        Arrays.fill(visited, false);

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

        return distances[end];
    }
}

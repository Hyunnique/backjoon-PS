import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 23.
 * @see BoJ 6497 (G4)
 * @category MST
 * @note 전력난
 *
 * MST를 구해서 전체 간선의 가중치 - MST의 가중치합을 구하면 된다
 * 
 */

class Edge implements Comparable<Edge> {
    int from, to, weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(weight, o.weight);
    }
}

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static Edge[] edgeList;
    static int V, E;
    static int[] parents;
    public static void main(String[] args) throws Exception {
        
        while (true) {
            V = sc.nextInt();
            E = sc.nextInt();

            if (V == 0 && E == 0) break;

            edgeList = new Edge[E];
            parents = new int[V];

            for (int i = 0; i < V; i++) parents[i] = i;

            int totalCost = 0;
            for (int i = 0; i < E; i++) {
                edgeList[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
                totalCost += edgeList[i].weight;
            }

            Arrays.sort(edgeList);
            
            int mstCost = 0, count = 0;
            for (int i = 0; i < E; i++) {
                if (union(edgeList[i].from, edgeList[i].to)) {
                    ++count;
                    mstCost += edgeList[i].weight;
                    if (count == V - 1) break;
                }
            }

            sb.append(totalCost - mstCost).append("\n");
        }
        System.out.println(sb.toString());
    }

    static int find(int x) {
        if (parents[x] == x) return x;
        return parents[x] = find(parents[x]);
    }

    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) return false;
        parents[rootB] = rootA;
        return true;
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
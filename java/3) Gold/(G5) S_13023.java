import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 22.
 * @see BoJ 13023 (G5)
 * @category DFS
 * @note ABCDE
 *
 * 양방향 그래프에서 연속된 노드가 5개 이상 이어지는 구간이 있는지 확인하기
 * 
 */

class Node {
    int index;
    List<Node> adjList = new ArrayList<>();
    boolean visited = false;

    Node(int index) {
        this.index = index;
    }
}

public class Main {
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static List<Node> nodeList;
    static int result = 0;
    public static void main(String[] args) throws Exception {
        N = sc.nextInt();
        M = sc.nextInt();
        
        nodeList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            nodeList.add(new Node(i));
        }

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            nodeList.get(a).adjList.add(nodeList.get(b));
            nodeList.get(b).adjList.add(nodeList.get(a));
        }

        for (int i = 0; i < N; i++) {
            if (!nodeList.get(i).visited) {
                //System.out.println("-- start " + i);
                nodeList.get(i).visited = true;
                dfs(i, 1);
                nodeList.get(i).visited = false;
            }
        }

        System.out.println(result);
    }

    static void dfs(int idx, int depth) {
        if (result == 1) return;
        //System.out.println(idx + ", " + depth);
        if (depth == 5) {
            result = 1;
            return;
        }

        Node currentNode = nodeList.get(idx);

        for (Node n : currentNode.adjList) {
            if (!n.visited) {
                n.visited = true;
                dfs(n.index, depth + 1);
                n.visited = false;
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

    public String readLine() throws IOException {
        String str = br.readLine();
        st = new StringTokenizer(str);

        return str;
    }

    public boolean hasMoreTokens() {
        return st.hasMoreTokens();
    }
}
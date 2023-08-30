import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 21.
 * @see BoJ 2252 (G3)
 * @category Topological Sort
 * @note 줄 세우기
 *
 * 위상 정렬 Standard 문제
 * 
 */

class Node {
    int index;
    Node next;

    Node(int index, Node next) {
        this.index = index;
        this.next = next;
    }
}

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static Node[] nodeList;
    static int[] indegrees;
    public static void main(String[] args) throws Exception {
        N = sc.nextInt();
        int M = sc.nextInt();

        nodeList = new Node[N + 1];
        indegrees = new int[N + 1];
        
        for (int i = 0; i < M; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();

            nodeList[A] = new Node(B, nodeList[A]);
            indegrees[B]++;
        }

        topologicalSort(indegrees);
        
        System.out.println(sb.toString());
    }

    static void topologicalSort(int[] indegrees) {
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 1; i < indegrees.length; i++) {
            if (indegrees[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            Integer n = q.poll();
            sb.append(n).append(" ");

            for (Node next = nodeList[n]; next != null; next = next.next) {
                indegrees[next.index]--;
                if (indegrees[next.index] == 0) q.offer(next.index);
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
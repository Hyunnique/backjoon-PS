import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 10. 10.
 * @see BoJ 14725 (G3)
 * @category String, Trie
 * @note 개미굴
 *
 * TreeMap으로 Trie와 유사하게 구현
 *
 */

class Node {
    String value;
    TreeMap<String, Node> children;

    Node(String value) {
        this.value = value;
        children = new TreeMap<>();
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();

        TreeMap<String, Node> trie = new TreeMap<>();

        for (int i = 0; i < N; i++) {
            int K = sc.nextInt();
            TreeMap<String, Node> cur = null;

            for (int j = 0; j < K; j++) {
                String str = sc.next();

                if (j == 0) cur = trie;

                cur = insert(cur, str);
            }
        }

        dfs(trie, 0);
        System.out.println(sb);
    }

    static TreeMap<String, Node> insert(TreeMap<String, Node> children, String value) {
        if (children.containsKey(value)) {
            return children.get(value).children;
        } else {
            children.put(value, new Node(value));
            return children.get(value).children;
        }
    }

    static void dfs(TreeMap<String, Node> children, int depth) {
        for (Node n : children.values()) {
            for (int i = 0; i < depth * 2; i++) sb.append("-");
            sb.append(n.value).append("\n");

            dfs(n.children, depth + 1);
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
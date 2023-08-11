import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class Node {
    char l, r, value;

    Node(char c, char l, char r) {
        this.value = c;
        this.l = l;
        this.r = r;
    }
}

public class Main {
    static Map<Character, Node> nodeMap = new HashMap<>();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            char x = st.nextToken().charAt(0);
            nodeMap.put(x, new Node(x, st.nextToken().charAt(0), st.nextToken().charAt(0)));
        }
        preOrder('A');
        sb.append("\n");
        inOrder('A');
        sb.append("\n");
        postOrder('A');

        System.out.println(sb.toString());
    }

    static void preOrder(char cursor) {
        if (cursor == '.') return;
        
        Node n = nodeMap.get(cursor);

        sb.append(n.value);
        preOrder(n.l);
        preOrder(n.r);
    }

    static void inOrder(char cursor) {
        if (cursor == '.') return;
        
        Node n = nodeMap.get(cursor);

        inOrder(n.l);
        sb.append(n.value);
        inOrder(n.r);
    }

    static void postOrder(char cursor) {
        if (cursor == '.') return;
        
        Node n = nodeMap.get(cursor);

        postOrder(n.l);
        postOrder(n.r);
        sb.append(n.value);
    }
}
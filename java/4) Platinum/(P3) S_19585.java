import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 17.
 * @see BoJ 19585
 * @category String, Hashing
 * @note 전설
 *
 * Color는 Tree를 이용한 해싱, Nickname은 Hashset을 이용해서 판단
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

class Node {
    char value;
    Node[] children = new Node[26];
    boolean isEnd = false;

    Node(char value) {
        this.value = value;
    }

    public Node addChild(char c) {
        Node child = new Node(c);
        children[c - 'a'] = child;

        return child;
    }

    public Node getChild(char c) {
        return children[c - 'a'];
    }
}

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();

    public static void main(String[] args) throws Exception {

        int C = sc.nextInt();
        N = sc.nextInt();

        Node colorHead = new Node('0');
        Set<String> nicknameSet = new HashSet<>();

        for (int i = 0; i < C; i++) {
            String str = sc.next();
            Node cursor = colorHead;
            Node temp;

            for (char s : str.toCharArray()) {
                temp = cursor.getChild(s);

                if (temp == null) {
                    cursor = cursor.addChild(s);
                } else {
                    cursor = cursor.getChild(s);
                }
            }

            cursor.isEnd = true;
        }

        for (int i = 0; i < N; i++) {
            nicknameSet.add(sc.next());
        }

        int Q = sc.nextInt();

        for (int i = 0; i < Q; i++) {
            char[] str = sc.next().toCharArray();
            Node cursor = colorHead;

            boolean latestEndStatus = false;
            boolean available = false;

            for (int cursorIndex = 0; cursorIndex < str.length; cursorIndex++) {

                cursor = cursor.getChild(str[cursorIndex]);
                if (cursor != null) latestEndStatus = cursor.isEnd;
                //if (latestEndStatus) System.out.println("\"" + String.valueOf(str, cursorIndex + 1, str.length - cursorIndex - 1) + "\"");
                if (cursor == null) {
                    break;
                } else if (latestEndStatus && nicknameSet.contains(String.valueOf(str, cursorIndex + 1, str.length - cursorIndex - 1))) {

                    available = true;
                    break;
                }
            }

            if (available) sb.append("Yes\n");
            else sb.append("No\n");
        }

        System.out.println(sb.toString());
    }
}

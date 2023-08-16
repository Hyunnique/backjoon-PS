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
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 14.
 * @see BoJ 5639 (G5)
 * @category Tree, Implement
 * @note 이진 검색 트리
 * 
 * 일단 트리 그리고 후위순회 하자
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
    Node left = null;
    Node right = null;
    Node parent = null;

    int value;

    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
    }
}

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Node head;
    public static void main(String[] args) throws Exception {

        Node head = null;
        Node cursor = null;

        while (true) {
            String str = br.readLine();
            if (str == null || str.equals("")) break;

            int x = Integer.parseInt(str);
            if (x == 0) break;

            if (head == null) {
                head = new Node(x, null);
                cursor = head;
                continue;
            }

            if (x < cursor.value) {
                cursor.left = new Node(x, cursor);
                cursor = cursor.left;
                //System.out.println(x + " : left of " + cursor.parent.value);
            } else {
                Node latestAvailable = cursor;
                while (cursor.parent != null && cursor.parent.value < x) {
                    if (cursor.parent.right == null) latestAvailable = cursor.parent;
                    cursor = cursor.parent;
                }

                latestAvailable.right = new Node(x, latestAvailable);
                cursor = latestAvailable.right;
                //System.out.println(x + " : right of " + latestAvailable.value);
            }
        }

        traverse(head);

        System.out.println(sb.toString());
    }

    static void traverse(Node cursor) {
        if (cursor.left != null) traverse(cursor.left);
        if (cursor.right != null) traverse(cursor.right);
        
        sb.append(cursor.value).append("\n");
    }
}

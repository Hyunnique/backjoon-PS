import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 21.
 * @see BoJ 2263 (G1)
 * @category Tree, Divide & Conquer
 * @note 트리의 순회
 *
 * Inorder, Postorder Traverse 결과가 주어졌을 때
 * Preorder Traverse 출력하기
 * 
 * 
 * 
 */

class Node {
    int value;
    Node left = null;
    Node right = null;

    Node(int value) {
        this.value = value;
    }
}

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int drawCursor;
    static int[] inOrder, postOrder;
    public static void main(String[] args) throws Exception {
        N = sc.nextInt();
        
        inOrder = new int[N];
        postOrder = new int[N];

        drawCursor = N - 1;

        for (int i = 0; i < N; i++) {
            inOrder[i] = sc.nextInt();
        }

        for (int i = 0; i < N; i++) {
            postOrder[i] = sc.nextInt();
        }

        preOrder(drawSubtree(0, false, 0, N - 1));
        System.out.println(sb.toString());
    }
    
    static Node drawSubtree(int rootCursor, boolean isLeft, int start, int end) {
        if (drawCursor < 0 || drawCursor >= N) return null;

        Node root = new Node(postOrder[drawCursor--]);
        //System.out.println("Drawing subtree of " + root.value + "(" + start + "~" + end + "), " + (isLeft ? "at left" : "at right") + ", cursor=" + rootCursor);

        if (start == end) {
            return root;
        }

        while (inOrder[rootCursor] != root.value) {
            rootCursor += (isLeft ? -1 : 1);
        }
        //System.out.println(rootCursor);
        if (rootCursor + 1 <= end) root.right = drawSubtree(rootCursor + 1, false, rootCursor + 1, end);
        if (rootCursor - 1 >= start) root.left = drawSubtree(rootCursor - 1, true, start, rootCursor - 1);

        return root;
    }

    static void preOrder(Node root) {
        sb.append(root.value).append(" ");
        if (root.left != null) preOrder(root.left);
        if (root.right != null) preOrder(root.right);
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
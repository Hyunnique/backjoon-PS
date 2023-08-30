import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 23.
 * @see BoJ 17471 (G4)
 * @category Subset, DFS
 * @note 게리맨더링
 *
 * 두 선거구로 나누는 모든 Subset을 구한다.
 * 각 선거구마다 임의의 노드에서의 DFS를 통해
 * 모든 노드가 연결되어있는지 확인한다.
 */

class Node {
    int index, value;
    boolean visited = false, grouped = false;
    List<Node> adj = new ArrayList<>();

    Node(int index, int value) {
        this.index = index;
        this.value = value;
    }
}

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int N, minGap = Integer.MAX_VALUE;
    static List<Node> nodeList;
    public static void main(String[] args) throws Exception {
        
        N = sc.nextInt();

        nodeList = new ArrayList<>();
        nodeList.add(new Node(0, 0));
        
        for (int i = 0; i < N; i++) {
            nodeList.add(new Node(i + 1, sc.nextInt()));
        }

        for (int i = 0; i < N; i++) {
            int edgeCount = sc.nextInt();

            for (int j = 1; j <= edgeCount; j++) {
                int x = sc.nextInt();
                nodeList.get(i + 1).adj.add(nodeList.get(x));
            }
        }

        makeGroup(0, new boolean[N]);
        
        System.out.println((minGap == Integer.MAX_VALUE ? -1 : minGap));
    }

    static void makeGroup(int nth, boolean[] selectedStatus) {

        if (nth == selectedStatus.length) {
            int lCnt = 0, rCnt = 0;
            int lSum = 0, rSum = 0;
            int fgi = -1, fui = -1;

            for (int i = 0; i < selectedStatus.length; i++) {
                if (selectedStatus[i]) {
                    if (fgi == -1) fgi = i + 1;
                    lSum += nodeList.get(i + 1).value;
                    lCnt++;
                } else {
                    if (fui == -1) fui = i + 1;
                    rSum += nodeList.get(i + 1).value;
                    rCnt++;
                }
            }

            if (lCnt == 0 || rCnt == 0) return;
            if (Math.abs(lSum - rSum) >= minGap) return;
            boolean lBFS = sideBFS(selectedStatus, fgi, lCnt, false);
            boolean rBFS = sideBFS(selectedStatus, fui, rCnt, true);

            if (lBFS && rBFS) {
                minGap = Math.min(minGap, Math.abs(lSum - rSum));
            }

            return;
        }

        selectedStatus[nth] = true;
        makeGroup(nth + 1, selectedStatus);
        selectedStatus[nth] = false;
        makeGroup(nth + 1, selectedStatus);
    }

    static boolean sideBFS(boolean[] selectedStatus, int startIndex, int requiredCount, boolean groupedBit) {

        ArrayDeque<Node> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        visited[startIndex] = true;
        queue.offerLast(nodeList.get(startIndex));

        int linkedCount = 0;
        while (!queue.isEmpty()) {
            Node n = queue.pollFirst();
            linkedCount++;
            for (Node next : n.adj) {
                if (selectedStatus[next.index - 1] == groupedBit || visited[next.index]) continue;

                visited[next.index] = true;
                //linkedCount++;
                queue.offerLast(next);
            }
        }

        if (linkedCount == requiredCount) return true;
        else return false;
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
import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 18.
 * @see BoJ 30039 (P5)
 * @category Implement
 * @note Queueueue (Utilcup Chapter 2)
 *
 * TL 2.5s (Java 6s), ML 1024mb
 * 가로세로 두가지의 큐를 구성하고, 각각 단일 큐처럼 동작하되
 * 가운데 있는 공유원소는 어떠한 큐의 원소도 아니고 가변적으로 각 큐로 이동해야함
 *
 */



public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static PSStringBuilder sb = new PSStringBuilder(false);

    static class Node {
        int value;
        Node[] prev, next;

        Node(int value) {
            this.value = value;

            prev = new Node[2];
            next = new Node[2];
        }

        @Override
        public String toString() {
            return String.valueOf(value) +
                    "(hPrev=" + (prev[0] == null ? "null" : prev[0].value) + ", " +
                    "hNext=" + (next[0] == null ? "null" : next[0].value) + ", " +
                    "vPrev=" + (prev[1] == null ? "null" : prev[1].value) + ", " +
                    "vNext=" + (next[1] == null ? "null" : next[1].value) + ")";
        }
    }

    static class Queueueue {
        Node[] head, tail;
        int[] size;

        Node middle;

        Queueueue() {
            head = new Node[2];
            tail = new Node[2];

            size = new int[2];
            middle = null;
        }

        boolean isEmpty() {
            return sizeAll() == 0;
        }

        void empty() { // 원소 개수 0으로 만들 때
            Arrays.fill(head, null);
            Arrays.fill(tail, null);
            Arrays.fill(size, 0);

            middle = null;
        }

        void init(int value) { // 원소 개수 1로 만들 때
            Node n = new Node(value);

            Arrays.fill(head, n);
            Arrays.fill(tail, n);
            Arrays.fill(size, 1);

            middle = n;
        }

        void adjustMiddle(int direction, int delta) {
            int anotherDirection = (direction + 1) % 2;

            Node tPrev = middle.prev[anotherDirection];
            Node tNext = middle.next[anotherDirection];

            if (delta > 0) {
                if (size[direction] % 2 == 1) {
                    if (middle == head[anotherDirection]) head[anotherDirection] = middle.next[direction];
                    if (middle == tail[anotherDirection]) tail[anotherDirection] = middle.next[direction];
                    middle = middle.next[direction];
                }
            } else {
                if (size[direction] % 2 == 1) {
                    if (middle == head[anotherDirection]) head[anotherDirection] = middle.next[direction];
                    if (middle == tail[anotherDirection]) tail[anotherDirection] = middle.next[direction];
                    middle = middle.next[direction];
                } else {
                    if (middle == head[anotherDirection]) head[anotherDirection] = middle.prev[direction];
                    if (middle == tail[anotherDirection]) tail[anotherDirection] = middle.prev[direction];
                    middle = middle.prev[direction];
                }
            }

            if (tPrev != null) tPrev.next[anotherDirection] = middle;
            if (tNext != null) tNext.prev[anotherDirection] = middle;

            middle.prev[anotherDirection] = tPrev;
            middle.next[anotherDirection] = tNext;

        }

        void push(int direction, int value) {
            Node n = new Node(value);

            if (this.isEmpty()) {
                init(value);
                return;
            }

            n.prev[direction] = this.tail[direction];
            this.tail[direction].next[direction] = n;
            this.tail[direction] = n;

            ++size[direction];
            adjustMiddle(direction, 1);
        }

        int pop(int direction) {
            if (this.isEmpty()) return -1;

            int anotherDirection = (direction + 1) % 2;
            Node popped = head[direction];

            if (size[direction] == 1) {
                if (size[anotherDirection] == 1) empty();
                else {
                    if (popped == head[anotherDirection]) head[anotherDirection] = head[anotherDirection].next[anotherDirection];

                    if (popped.prev[anotherDirection] != null) popped.prev[anotherDirection].next[anotherDirection] = popped.next[anotherDirection];
                    if (popped.next[anotherDirection] != null) popped.next[anotherDirection].prev[anotherDirection] = popped.prev[anotherDirection];

                    --size[anotherDirection];
                    adjustMiddle(anotherDirection, -1);
                }
            } else {
                head[direction] = head[direction].next[direction];
                head[direction].prev[direction] = null;

                --size[direction];
                adjustMiddle(direction, 1);
            }

            return popped.value;
        }

        int front(int direction) {
            if (isEmpty()) return -1;
            return head[direction].value;
        }

        int back(int direction) {
            if (isEmpty()) return -1;
            return tail[direction].value;
        }

        int size(int direction) {
            return size[direction];
        }

        int sizeAll() {
            return size[0] + size[1] - (middle != null ? 1 : 0);
        }

        int getMiddle() {
            if (isEmpty()) return -1;
            else return middle.value;
        }
    }

    public static void main(String[] args) throws IOException {

        Queueueue queue = new Queueueue();
        int N = sc.nextInt();

        String[] cmdParams = { "h", "v" };

        for (int i = 0; i < N; i++) {
            String cmd = sc.next();
            //sb.appendLine(cmd);
            for (int c = 0; c < cmdParams.length; c++) {
                if (cmd.startsWith(cmdParams[c]) && cmd.endsWith("push")) {
                    queue.push(c, sc.nextInt());
                }
                else if (cmd.startsWith(cmdParams[c]) && cmd.endsWith("pop")) {
                    sb.appendLine(queue.pop(c));
                }
                else if (cmd.startsWith(cmdParams[c]) && cmd.endsWith("front")) {
                    sb.appendLine(queue.front(c));
                }
                else if (cmd.startsWith(cmdParams[c]) && cmd.endsWith("back")) {
                    sb.appendLine(queue.back(c));
                }
                else if (cmd.startsWith(cmdParams[c]) && cmd.endsWith("size")) {
                    sb.appendLine(queue.size(c));
                }
            }

            if (cmd.equals("size")) {
                sb.appendLine(queue.sizeAll());
            }
            else if (cmd.equals("empty")) {
                sb.appendLine(queue.isEmpty() ? 1 : 0);
            }
            else if (cmd.equals("middle")) {
                sb.appendLine(queue.getMiddle());
            }

            /*for (int v = 0; v < 2; v++) {
                Node cursor = queue.head[v];

                sb.append("Queue" + cmdParams[v] + " : size = " + queue.size[v] + " [");
                while (cursor != null) {
                    sb.append(cursor.toString()).append(", ");
                    cursor = cursor.next[v];
                }
                sb.append("]\n");
            }

            sb.append("middle : " + queue.getMiddle() + "\n");*/
        }

        System.out.print(sb);
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

class PSStringBuilder {
    StringBuilder sb = new StringBuilder();
    boolean debug;

    PSStringBuilder(boolean debug) {
        this.debug = debug;
    }

    void checkDebug() {
        if (debug) {
            System.out.print(sb.toString());
            sb = new StringBuilder();
        }
    }

    @Override
    public String toString() {
        if (debug) return "";
        else return sb.toString();
    }

    <T> PSStringBuilder append (T value) {
        sb.append(value);
        checkDebug();
        return this;
    }

    <T> PSStringBuilder appendLine (T value) {
        sb.append(value).append("\n");
        checkDebug();
        return this;
    }
}
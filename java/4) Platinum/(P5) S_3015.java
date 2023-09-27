import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 26.
 * @see BoJ 3015 (P5)
 * @category Stack
 * @note 오아시스 재결합
 *
 * 스택을 이용해서 하나씩 넣으면서
 * peek보다 작으면 스택에 삽입하고 쌍 갯수 +1
 * 크면 나보다 큰게 나올때 까지 스택에서 pop하면서 쌍 갯수 +1
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int N;

    static class Node {
        int value;
        int size;

        Node(int value) {
            this.value = value;
            this.size = 1;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static void main(String[] args) throws IOException {
        N = sc.nextInt();

        ArrayDeque<Node> stack = new ArrayDeque<>();

        long cnt = 0;

        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();

            if (stack.isEmpty()) stack.push(new Node(x));
            else {
                if (stack.peek().value < x) {
                    while (!stack.isEmpty() && stack.peek().value < x) {
                        Node n = stack.pop();
                        cnt += n.size;
                    }

                    if (!stack.isEmpty()) {
                        if (stack.peek().value == x) {
                            if (stack.size() == 1) {
                                cnt += stack.peek().size++;
                            } else {
                                cnt += stack.peek().size++ + 1;
                            }
                        } else {
                            cnt += 1;
                            stack.push(new Node(x));
                        }
                    } else {
                        stack.push(new Node(x));
                    }
                }
                else if (stack.peek().value == x) {
                    if (stack.size() == 1) {
                        cnt += stack.peek().size++;
                    } else {
                        cnt += stack.peek().size++ + 1;
                    }
                }
                else if (stack.peek().value > x) {
                    cnt++;
                    stack.push(new Node(x));
                }
            }
        }

        System.out.println(cnt);
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
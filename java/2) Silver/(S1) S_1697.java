import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 18.
 * @see BoJ 1697 (S1)
 * @category BFS
 * @note 숨바꼭질
 *
 * 몇 초 후에 찾는지를 구해야 하기 때문에
 * 1차원 배열에 대한 BFS 돌기
 * 
 */

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    public static void main(String[] args) throws Exception {
        N = sc.nextInt();
        int K = sc.nextInt();

        int[] arr = new int[200000];
        boolean[] visited = new boolean[200000];

        Queue<Integer> queue = new ArrayDeque<>();

        int time = 0;
        boolean found = false;

        queue.offer(N);
        visited[N] = true;

        if (N == K) System.out.println(0);
        else {
            while (!queue.isEmpty()) {
                time++;
                int size = queue.size(); 

                while (--size >= 0) {
                    int x = queue.poll();
                    //System.out.println(x);
                    if (x * 2 == K || x - 1 == K || x + 1 == K) {
                        found = true;
                        break;
                    }
                    if (x * 2 < 200000 && !visited[x * 2]) {
                        visited[x * 2] = true;
                        queue.offer(x * 2);
                    }
                    if (x - 1 >= 0 && !visited[x - 1]) {
                        visited[x - 1] = true;
                        queue.offer(x - 1);
                    }
                    if (x + 1 < 200000 && !visited[x + 1]) {
                        visited[x + 1] = true;
                        queue.offer(x + 1);
                    }
                    
                }

                if (found) break;
            }

            System.out.println(time);
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 08.
 * @see BoJ 16926
 * @note
 * 
 *       (BoJ 16927과 동일한 풀이)
 *       테두리에서부터 0번째, 1번째 ... n번째로 줄을 나누고
 *       각각 Queue에 넣어서 R % (Total Movecount) 회 Offer & Poll 진행
 * 
 */

class CustomReader {
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

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static int[][] vec = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        int M = cr.nextInt();
        int R = cr.nextInt();

        int f = (int) (Math.ceil((double) Math.min(N, M) / 2));

        int[][] arr = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                arr[i][j] = cr.nextInt();
            }
        }

        for (int i = 0; i < f; i++) {
            Queue<Integer> q = new ArrayDeque<>();
            int mv = 0;
            int vecIndex = 0;
            int curN = i, curM = i;
            int moveN = (N - i * 2 - 1);
            int moveM = (M - i * 2 - 1);

            // System.out.println("total: " + (moveN * 2 + moveM * 2));
            for (int j = 0; j < (moveN * 2 + moveM * 2); j++) {
                q.offer(arr[curN][curM]);
                // System.out.println("offered " + curN + "," + curM);
                if (vecIndex % 2 == 1 && mv == moveN) {
                    vecIndex++;
                    mv = 1;
                } else if (vecIndex % 2 == 0 && mv == moveM) {
                    vecIndex++;
                    mv = 1;
                } else {
                    mv++;
                }

                curN += vec[vecIndex][0];
                curM += vec[vecIndex][1];

                if (vecIndex == 4)
                    break;
            }

            for (int j = 0; j < R % (moveN * 2 + moveM * 2); j++) {
                q.offer(q.poll());
            }

            mv = 0;
            vecIndex = 0;
            curN = i;
            curM = i;

            for (int j = 0; j < (moveN * 2 + moveM * 2); j++) {
                arr[curN][curM] = q.poll();

                if (vecIndex % 2 == 1 && mv == moveN) {
                    vecIndex++;
                    mv = 1;
                } else if (vecIndex % 2 == 0 && mv == moveM) {
                    vecIndex++;
                    mv = 1;
                } else {
                    mv++;
                }

                curN += vec[vecIndex][0];
                curM += vec[vecIndex][1];

                if (vecIndex == 4)
                    break;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}

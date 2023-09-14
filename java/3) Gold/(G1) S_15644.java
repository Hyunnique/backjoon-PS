import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 08.
 * @see BoJ 15644 (G1)
 * @category BFS, Implement
 * @note 구슬 탈출 3
 *
 * 구현
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static char[][] map;
    static int result = 100;
    static String resultString = "";
    static int holeRow = -1, holeColumn = -1;
    static int[][] movementDelta = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();
        M = sc.nextInt();

        map = new char[N][M];

        int rowA = -1, columnA = -1;
        int rowB = -1, columnB = -1;

        for (int i = 0; i < N; i++) {
            String str = sc.next();

            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);

                if (map[i][j] == 'R') {
                    rowA = i;
                    columnA = j;
                }
                else if (map[i][j] == 'B') {
                    rowB = i;
                    columnB = j;
                }
                else if (map[i][j] == 'O') {
                    holeRow = i;
                    holeColumn = j;
                }
            }
        }

        simulate(0, -1, new ArrayDeque<Character>(), rowA, columnA, rowB, columnB);

        System.out.println((result == 100 ? -1 : result));
        if (result != 100) System.out.println(resultString);
    }

    static void simulate(int nth, int latestDelta, ArrayDeque<Character> route, int rowA, int columnA, int rowB, int columnB) {
        if (result <= nth + 1 || nth == 10) return;

        int curRowA, curColumnA, curRowB, curColumnB;

        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
            if ((deltaIndex + 2) % 4 == latestDelta) continue; // 직전에 움직였던 곳으로 돌아갈 필요는 없음

            curRowA = rowA;
            curColumnA = columnA;
            curRowB = rowB;
            curColumnB = columnB;

            boolean passedA = false;
            boolean passedB = false;

            while (true) {
                boolean availableA = available(curRowA + movementDelta[deltaIndex][0], curColumnA + movementDelta[deltaIndex][1]);
                boolean availableB = available(curRowB + movementDelta[deltaIndex][0], curColumnB + movementDelta[deltaIndex][1]);

                if (!availableA && !availableB) break;
                if (!availableA && curRowB + movementDelta[deltaIndex][0] == curRowA && curColumnB + movementDelta[deltaIndex][1] == curColumnA) break;
                if (!availableB && curRowA + movementDelta[deltaIndex][0] == curRowB && curColumnA + movementDelta[deltaIndex][1] == curColumnB) break;

                if (availableA && curRowA != -1) {
                    curRowA += movementDelta[deltaIndex][0];
                    curColumnA += movementDelta[deltaIndex][1];

                    if (map[curRowA][curColumnA] == 'O') {
                        passedA = true;
                        curRowA = -1;
                        curColumnA = -1;
                    }
                }

                if (availableB && curRowB != -1) {
                    curRowB += movementDelta[deltaIndex][0];
                    curColumnB += movementDelta[deltaIndex][1];

                    if (map[curRowB][curColumnB] == 'O') {
                        passedB = true;
                        curRowB = -1;
                        curColumnB = -1;
                    }
                }
            }

            if (passedA && !passedB) {
                result = Math.min(result, nth + 1);
                StringBuilder sbb = new StringBuilder();

                if (deltaIndex == 0) route.addLast('U');
                else if (deltaIndex == 1) route.addLast('L');
                else if (deltaIndex == 2) route.addLast('D');
                else route.addLast('R');

                for (int i = 0; i < route.size(); i++) {
                    char x = route.pollFirst();
                    sbb.append(x);
                    route.offerLast(x);
                }

                resultString = sbb.toString();

                route.removeLast();
                continue;
            }
            else if (passedB) {
                continue;
            }

            if (deltaIndex == 0) route.addLast('U');
            else if (deltaIndex == 1) route.addLast('L');
            else if (deltaIndex == 2) route.addLast('D');
            else route.addLast('R');

            simulate(nth + 1, deltaIndex, route, curRowA, curColumnA, curRowB, curColumnB);

            route.removeLast();
        }
    }

    static boolean available(int row, int column) {
        return row >= 0 && row < N && column >= 0 && column < M && map[row][column] != '#';
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
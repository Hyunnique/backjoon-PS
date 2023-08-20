import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 18.
 * @see BoJ 17135 (G3)
 * @category Combination, Bruteforce
 * @note 캐슬 디펜스
 *
 * 궁수 배치 Combination을 구해서 모두 해보기
 * BFS를 이용해서 미리 i번 라인에 궁수를 배치했을 때의 사격 순서를 저장해두고
 * 어떤 조합이 오던 그 사격 순서대로만 쏘면 된다.
 * 
 * 같은 적을 쏠 때나, 쏘려던 적이 이미 다른 궁수에 의해 죽었던 적인 경우에 대해 잘 예외처리해야 한다.
 *
 */

class Point {
    int row, column;

    Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        Point target = (Point) o;
        return row == target.row && column == target.column;
    }
}

class Enemy {
    Point position;
    int distance;

    Enemy(Point position, int distance) {
        this.position = position;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        Enemy target = (Enemy) o;
        return position.equals(target.position);
    }
}

public class Main {
    static int N, M, D;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int[][] map;
    static int[][] movementDelta = { { 0, -1 }, { 1, 0 }, { 0, 1 } };
    static int enemyCount = 0;
    static List<List<Enemy>> lineList = new ArrayList<>();
    static int max;
    public static void main(String[] args) throws Exception {

        N = sc.nextInt();
        M = sc.nextInt();
        D = sc.nextInt();

        map = new int[N][M];

        // 편의를 위해 위 아래를 뒤집어서 받는다
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();

                if (map[i][j] == 1) enemyCount++;
            }
        }

        // 모든 라인에 대해 BFS를 돌려서 미리 해당 라인에 궁수가 배치되었을 때의 사격 순서를 저장해둔다
        // 나중에 추가적인 거리 계산 없이 상수 시간 안에 사격 대상을 정할 수 있다.
        for (int i = 0; i < M; i++) {
            lineList.add(BFS(i));
        }

        // 조합을 만들고 각 조합에 대해서 시뮬레이션한다
        makeCombination(0, 0, new int[3]);

        System.out.println(max);
    }

    static List<Enemy> BFS(int line) {
        // 일반적인 BFS 메소드
        // 왼쪽 - 아래 - 오른쪽 순서로 탐색하면 알아서 문제에서 요구하는 사격 순서대로 정렬이 가능함
        // 탐색하면서 도달하는데 걸린 거리를 저장해둠

        List<Enemy> result = new ArrayList<>();

        Queue<Point> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];
        
        queue.offer(new Point(0, line));
        visited[0][line] = true;

        if (map[0][line] == 1) {
            result.add(new Enemy(new Point(0, line), 1));
        }

        int depth = 2;
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            while (size-- > 0) {
                Point p = queue.poll();

                for (int[] delta : movementDelta) {
                    int curRow = p.row + delta[0];
                    int curColumn = p.column + delta[1];

                    if (curRow < 0 || curRow >= N ||
                    curColumn < 0 || curColumn >= M ||
                    visited[curRow][curColumn]) continue;

                    visited[curRow][curColumn] = true;
                    queue.offer(new Point(curRow, curColumn));
                    
                    if (map[curRow][curColumn] == 1) {
                        result.add(new Enemy(new Point(curRow, curColumn), depth));
                    }
                }
            }

            depth++;
        }

        return result;
    }

    static void makeCombination(final int nth, final int startIndex, int[] chosen) {

        // 모든 적을 잡았으면 조기 종료
        if (max == enemyCount) {
            return;
        }

        // 궁수를 배치할 수 있는 모든 조합을 확인해서 게임을 진행한다
        if (nth == chosen.length) {
            game(chosen);
            return;
        }

        for (int i = startIndex; i < M; i++) {
            chosen[nth] = i;
            makeCombination(nth + 1, i + 1, chosen);
        }
    }

    static void game(int[] sniper) {

        int leftEnemy = enemyCount; // 남은 적의 수

        // 각 라인의 리스트간의 싱크를 맞추기 위해서
        // 해당 위치의 적이 이미 죽었는지를 확인하는 이차원 배열을 관리함
        boolean[][] dead = new boolean[N][M];

        // 리스트의 재사용을 위해 Queue로 구현하지 않고 ArrayList + Cursor로 인덱스만 이동하면서 순서 탐색
        int[] shootCursor = new int[3];

        // 각 턴마다
        for (int turn = 0; turn < N; turn++) {

            // 가지치기 (남은 턴동안 3명씩 꼬박꼬박 잡아도 최댓값에 도달하지 못한다면 즉시 게임 종료)
            if (max >= (enemyCount - leftEnemy) + ((N - turn) * 3)) break;

            // 동일한 턴에는 똑같은 적을 쏘긴 쏴야 하므로 턴이 끝난 뒤에 한번에 죽인다
            Queue<Enemy> willDie = new ArrayDeque<>();

            // 각 궁수별로
            for (int sniperIndex = 0; sniperIndex < 3; sniperIndex++) {

                // 궁수가 서있는 라인을 확인해서 미리 순서를 정해둔 리스트를 받아온다
                int sniperLine = sniper[sniperIndex];
                List<Enemy> shootOrder = lineList.get(sniperLine);

                while (true) {
                    // 만약 더이상 쏠 수 있는 적이 없으면 break
                    if (shootCursor[sniperIndex] + 1 > shootOrder.size()) break;

                    // 현재 커서의 적을 받아와서
                    Enemy target = shootOrder.get(shootCursor[sniperIndex]++);

                    // 만약 턴이 지나면서 맵 밖으로 사라져버린 적이면 continue
                    if (target.position.row < turn) continue;

                    // 만약 사거리 안에 아직 안 들어온 적이라면 break
                    if (target.distance > D + turn) {
                        shootCursor[sniperIndex]--;
                        break;
                    }

                    // 만약 다른 궁수가 쏜 적이면 break
                    if (willDie.contains(target)) break;

                    // 만약 이미 죽은 적이면 continue
                    if (dead[target.position.row][target.position.column]) continue;

                    willDie.offer(target);
                    leftEnemy--;
                    break;
                }
            }

            // 턴 끝나고 한번에 죽이기
            while (!willDie.isEmpty()) {
                Enemy target = willDie.poll();
                dead[target.position.row][target.position.column] = true;
            }
        }

        max = Math.max(max, enemyCount - leftEnemy);
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
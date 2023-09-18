import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 18.
 * @see BoJ 30038 (G1)
 * @category Implement
 * @note Overdose Runner
 *
 * 게임 맵 N * M
 *
 * 플레이어가 할 수 있는 행동
 * - 순간이동 : 행동력 -1, 상하좌우 중 한 방향으로 정확히 p (캐릭터 이동속도) 만큼 이동
 * - 대기 : 행동력 -1, 아무 행동도 하지 않음
 * - 공격 : 행동력 -3, 상하좌우 중 한 방향으로 투사체 발사
 * > 공격 사거리까지 날아가며 몬스터가 맞으면 공격력 - 방어력만큼 몬스터 체력 감소 (음수는 감소 X)
 * > 몬스터 체력이 0 이하가 되면 몬스터는 사라지고 경험치 흭득
 * > 몬스터에 닿아도 투사체가 사라지진 않고 계속 진행하는듯? but 장애물 만나면 사라짐
 * - 약 먹기 : 행동력 -2, 이동속도 1 증가 혹은 감소
 * > 약을 5번 먹을 때마다 Overdose 상태가 되어 행동력을 10 이상 소모할때까지 해제되지 않으며,
 * > 이 상태에서는 순간이동, 대기를 제외한 다른 행동을 할 수 없음
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    static int N, M;
    static Object[][] map;
    static int goalRow = -1, goalColumn = -1;

    static class Monster {
        int x, y;
        int HP, DEF, EXP;

        Monster(int posRow, int posColumn) {
            this.y = posRow;
            this.x = posColumn;
        }
    }

    static class Wall {
        int x, y;

        Wall(int posRow, int posColumn) {
            this.y = posRow;
            this.x = posColumn;
        }
    }

    static class Player {
        int ATK, RANGE, SPEED, MAX_EXP, EXP, LEVEL, TURN;
        int x, y;
        int DRUG_COUNT;
        boolean OVERDOSED;
        int OVERDOSED_AT;

        boolean CLEARED = false;

        Player(int posRow, int posColumn) {
            ATK = 5;
            RANGE = 1;
            SPEED = 1;
            MAX_EXP = 10;
            EXP = 0;
            LEVEL = 1;
            TURN = 0;

            DRUG_COUNT = 0;

            OVERDOSED = false;
            OVERDOSED_AT = -1;

            this.y = posRow;
            this.x = posColumn;
        }

        void addEXP(int value) {
            EXP += value;

            while (EXP >= MAX_EXP) {
                EXP -= MAX_EXP;

                ATK += LEVEL;
                RANGE += 1;
                MAX_EXP += 10;
                LEVEL += 1;
            }
        }

        void checkOverdose() {
            if (OVERDOSED_AT + 10 <= TURN) {
                OVERDOSED = false;
                OVERDOSED_AT = -1;
            }
        }

        void move(int row, int column) {
            if (CLEARED) return;

            int curRow = y + (row * this.SPEED);
            int curColumn = x + (column * this.SPEED);

            if (curRow < 0 || curRow >= N || curColumn < 0 || curColumn >= M ||
            map[curRow][curColumn] instanceof Wall || map[curRow][curColumn] instanceof Monster) return;

            map[y][x] = null;
            map[curRow][curColumn] = this;

            y = curRow;
            x = curColumn;

            ++TURN;
            checkOverdose();
        }

        void doWait() {
            if (CLEARED) return;

            ++TURN;
            checkOverdose();
        }

        void attack(int row, int column) {
            if (OVERDOSED || CLEARED) return;

            int remainRange = RANGE;
            int curRow = y + row, curColumn = x + column;
            int gainedExp = 0;

            while (remainRange > 0) {
                if (curRow < 0 || curRow >= N || curColumn < 0 || curColumn >= M ||
                        map[curRow][curColumn] instanceof Wall) break;

                if (map[curRow][curColumn] instanceof Monster) {
                    Monster m = (Monster) map[curRow][curColumn];

                    m.HP -= Math.max(0, this.ATK - m.DEF);
                    if (m.HP <= 0) {
                        map[curRow][curColumn] = null;
                        gainedExp += m.EXP;
                    }
                }

                curRow += row;
                curColumn += column;
                --remainRange;
            }

            addEXP(gainedExp);
            TURN += 3;
        }

        void drug(int deltaSpeed) {
            if (CLEARED || OVERDOSED) return;

            SPEED += deltaSpeed;
            if (SPEED < 0) SPEED = 0;

            if (++DRUG_COUNT == 5) {
                DRUG_COUNT = 0;
                OVERDOSED = true;
                OVERDOSED_AT = TURN + 2;
            }

            TURN += 2;
        }

        void clear() {
            if (CLEARED || OVERDOSED) return;

            if (y == goalRow && x == goalColumn) CLEARED = true;
        }
    }

    public static void main(String[] args) throws IOException {

        N = sc.nextInt();
        M = sc.nextInt();

        map = new Object[N][M];

        Player player = null;
        Queue<Monster> monsterQueue = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            String line = sc.next();

            for (int j = 0; j < M; j++) {
                char x = line.charAt(j);

                switch (x) {
                    case '*':
                        map[i][j] = new Wall(i, j);
                        break;
                    case '.':
                        map[i][j] = null;
                        break;
                    case 'p':
                        player = new Player(i, j);
                        map[i][j] = player;
                        break;
                    case 'm':
                        Monster m = new Monster(i, j);
                        map[i][j] = m;
                        monsterQueue.offer(m);
                        break;
                    case 'g':
                        goalRow = i;
                        goalColumn = j;
                        map[i][j] = null;
                        break;
                }
            }
        }

        int monsterCount = sc.nextInt();

        for (int i = 0; i < monsterCount; i++) {
            Monster m = monsterQueue.poll();
            m.HP = sc.nextInt();
            monsterQueue.offer(m);
        }

        for (int i = 0; i < monsterCount; i++) {
            Monster m = monsterQueue.poll();
            m.DEF = sc.nextInt();
            monsterQueue.offer(m);
        }

        for (int i = 0; i < monsterCount; i++) {
            Monster m = monsterQueue.poll();
            m.EXP = sc.nextInt();
            monsterQueue.offer(m);
        }

        int commandCount = sc.nextInt();

        for (int i = 0; i < commandCount; i++) {
            String cmd = sc.next();

            if (cmd.equals("u")) {
                player.move(-1, 0);
            }
            else if (cmd.equals("d")) {
                player.move(1, 0);
            }
            else if (cmd.equals("l")) {
                player.move(0, -1);
            }
            else if (cmd.equals("r")) {
                player.move(0, 1);
            }
            else if (cmd.equals("w")) {
                player.doWait();
            }
            else if (cmd.equals("au")) {
                player.attack(-1, 0);
            }
            else if (cmd.equals("ad")) {
                player.attack(1, 0);
            }
            else if (cmd.equals("al")) {
                player.attack(0, -1);
            }
            else if (cmd.equals("ar")) {
                player.attack(0, 1);
            }
            else if (cmd.equals("du")) {
                player.drug(1);
            }
            else if (cmd.equals("dd")) {
                player.drug(-1);
            }
            else if (cmd.equals("c")) {
                player.clear();
            }
        }

        sb.append(player.LEVEL).append(" ").append(player.EXP).append("\n");
        sb.append(player.TURN).append("\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Object o = map[i][j];

                if (o instanceof Player) {
                    sb.append("p");
                }
                else if (o instanceof Wall) {
                    sb.append("*");
                }
                else if (o instanceof Monster) {
                    sb.append("m");
                }
                else if (goalRow == i && goalColumn == j) {
                    sb.append("g");
                }
                else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }

        while (!monsterQueue.isEmpty()) {
            Monster m = monsterQueue.poll();

            if (m.HP <= 0) continue;

            sb.append(m.HP).append(" ");
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 18.
 * @see BoJ 30043 (D3)
 * @category Implement
 * @note Binding of Issac
 *
 * 그리디하게 갈 수 있는 방은 그리디하게 가고,
 * 확실하지 않은 방은 분기하여 백트래킹
 * 플레이어 변수와 방 별 방문 가능 여부, 방문 여부를 상태로 관리하여 hashCode를 이용해 메모이제이션해서
 * 연산을 줄임
 *
 */

public class Issac {
    static IssacScanner sc = new IssacScanner();
    static StringBuilder sb = new StringBuilder();
    static final int MAP_SIZE = 9;
    static PSRandom random;
    static int[][] movementDelta = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    static Room[][] map;
    static ArrayList<Room> rooms;
    static HashSet<Integer> memory = new HashSet<>();

    static final int TYPE_SHOP = 0;
    static final int TYPE_HIDDEN = 1;
    static final int TYPE_TREASURE1 = 2;
    static final int TYPE_TREASURE2 = 3;
    static final int TYPE_SACRIFICE = 4;
    static final int TYPE_CURSE = 5;

    static class PSRandom {
        int seed;
        final long A = 1_103_515_245L;
        final long C = 12_345L;
        final long M = 2_147_483_648L;
        boolean first = true;

        PSRandom(String seed) {
            long multiplier = 1;
            long sum = 0;

            for (int i = 8; i >= 0; i--) {
                if (i == 4) continue;

                char x = seed.charAt(i);
                long d = 0;
                if (x >= 48 && x <= 57) {
                    d = x - 48;
                }
                else if (x >= 65 && x <= 90) {
                    d = x - 65 + 10;
                }

                sum += multiplier * d;
                multiplier *= 36;
            }

            setSeed(sum);
        }

        void setSeed(long value) {
            this.seed = (int) (value % M);
        }

        int rand() {
            if (first) {
                first = false;
                return seed;
            }

            long temp = (A * (long) seed + C);
            setSeed(temp);

            return seed;
        }

        int randInt(int left, int right) {
            return left + (rand() % (right - left + 1));
        }

        boolean chance(int p) {
            return randInt(1, 100) <= p;
        }

        <T> T choice(ArrayList<T> arr) {
            return arr.get(randInt(0, arr.size() - 1));
        }
    }

    static class Room {
        String type;
        int posX, posY;
        int adjCount = 0;
        int requireATK = 0;

        int roomIndex;

        Room(String type, int posX, int posY) {
            this.type = type;
            this.posX = posX;
            this.posY = posY;
        }
    }

    static class Game {
        int mapHeight, mapWidth;
        int[] bounds = {4, 4, 4, 4};
        int roomCount;

        boolean shopPresented = false;

        Game() {
            roomCount = random.randInt(10, 20);
            map = new Room[MAP_SIZE][MAP_SIZE];
            rooms = new ArrayList<>();

            mapHeight = 1;
            mapWidth = 1;
        }

        // 2단계 : 방 배치 지정
        void createMap() {
            int roomToMake = roomCount;

            map[4][4] = new Room("Start", 4, 4);
            rooms.add(map[4][4]);

            Queue<Room> queue = new ArrayDeque<>();

            while (roomToMake > 0) {
                queue.offer(random.choice(rooms));

                while (!queue.isEmpty()) {
                    Room current = queue.poll();

                    for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                        int[] delta = movementDelta[deltaIndex];

                        if (roomToMake == 0) break;

                        int curX = current.posX + delta[1];
                        int curY = current.posY + delta[0];

                        if (curX < 0 || curX >= MAP_SIZE || curY < 0 || curY >= MAP_SIZE) continue;
                        if (map[curY][curX] != null) continue;

                        int adjRoomCount = 0;

                        for (int[] subDelta : movementDelta) {
                            if (curX + subDelta[1] < 0 || curX + subDelta[1] >= MAP_SIZE ||
                                    curY + subDelta[0] < 0 || curY + subDelta[0] >= MAP_SIZE) continue;
                            if (map[curY + subDelta[0]][curX + subDelta[1]] != null) {
                                ++adjRoomCount;
                            }
                        }

                        if (adjRoomCount >= 2) continue;
                        if (!random.chance(50)) continue;

                        Room newRoom = new Room("Empty", curX, curY);

                        newRoom.adjCount = adjRoomCount;

                        for (int[] subDelta : movementDelta) {
                            if (curX + subDelta[1] < 0 || curX + subDelta[1] >= MAP_SIZE ||
                                    curY + subDelta[0] < 0 || curY + subDelta[0] >= MAP_SIZE) continue;
                            if (map[curY + subDelta[0]][curX + subDelta[1]] != null) {
                                map[curY + subDelta[0]][curX + subDelta[1]].adjCount++;
                            }
                        }

                        map[curY][curX] = newRoom;
                        rooms.add(newRoom);
                        queue.offer(newRoom);
                        --roomToMake;

                        if (curY < bounds[0]) {
                            mapHeight++;
                            bounds[0]--;
                        }
                        if (curY > bounds[1]) {
                            mapHeight++;
                            bounds[1]++;
                        }
                        if (curX < bounds[2]) {
                            mapWidth++;
                            bounds[2]--;
                        }
                        if (curX > bounds[3]) {
                            mapWidth++;
                            bounds[3]++;
                        }
                    }
                }
            }

            rooms.remove(0);

            makeTypeRooms();
        }

        // 3단계 : 방의 타입 지정
        void makeTypeRooms() {
            ArrayList<Room> special = new ArrayList<>();
            ArrayList<Room> notSpecial = new ArrayList<>();
            for (Room room : rooms) {
                if (room.adjCount == 1) {
                    special.add(room);
                } else {
                    notSpecial.add(room);
                }
            }

            // 일반방 지정
            ArrayList<Integer> require = new ArrayList<>();

            for (int i = 0; i <= 9; i++) {
                for (int j = 10 - i; j > 0; j--) {
                    require.add(i);
                }
            }

            int normalRoomCursor = 0;

            while (normalRoomCursor < notSpecial.size()) {
                Room r = notSpecial.get(normalRoomCursor++);

                r.type = "Normal";
                r.requireATK = random.choice(require);
            }

            // 보스방 지정
            ArrayList<Room> boss = new ArrayList<>();

            for (Room r : special) {
                boolean available = true;
                for (int[] delta : movementDelta) {
                    if (r.posX + delta[1] < 0 || r.posX + delta[1] >= MAP_SIZE ||
                            r.posY + delta[0] < 0 || r.posY + delta[0] >= MAP_SIZE) continue;
                    if (map[r.posY + delta[0]][r.posX + delta[1]] != null &&
                            map[r.posY + delta[0]][r.posX + delta[1]].type.equals("Start")) {
                        available = false;
                        break;
                    }
                }

                if (available) boss.add(r);
            }

            Room bossRoom = random.choice(boss);
            bossRoom.type = "Boss";
            special.remove(bossRoom);

            // 비밀방 지정
            if (!special.isEmpty()) {
                Room hidden = random.choice(special);
                hidden.type = "Hidden";
                special.remove(hidden);
            }

            // 보물방 지정
            for (int i = 0; i < (roomCount >= 15 ? 2 : 1); i++) {
                if (!special.isEmpty()) {
                    if (i == 1) {
                        if (!random.chance(25)) break;
                    }

                    Room treasure = random.choice(special);
                    treasure.type = "Treasure";
                    special.remove(treasure);
                }
            }

            // 상점방 지정
            if (!special.isEmpty()) {
                if (roomCount <= 15 || random.chance(66)) {
                    Room shop = random.choice(special);
                    shop.type = "Shop";
                    special.remove(shop);
                    shopPresented = true;
                }
            }

            boolean isAngelExists = false;
            boolean isDevilExists = false;

            // 악마방, 천사방 지정
            if (random.chance(20)) {
                ArrayList<int[]> reward = new ArrayList<>();
                int[][] nearBossDelta = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

                for (int[] delta : nearBossDelta) {
                    int curX = bossRoom.posX + delta[1];
                    int curY = bossRoom.posY + delta[0];

                    if (curX < 0 || curX >= MAP_SIZE ||
                            curY < 0 || curY >= MAP_SIZE) continue;
                    if (map[curY][curX] != null) continue;
                    if (curY < bounds[0] || curY > bounds[1] || curX < bounds[2] || curX > bounds[3]) continue;

                    reward.add(new int[]{curY, curX});
                }

                if (!reward.isEmpty()) {
                    int[] pos = random.choice(reward);
                    String rewardType = (random.chance(50) ? "Devil" : "Angel");

                    if (rewardType.equals("Angel")) isAngelExists = true;
                    else isDevilExists = true;

                    map[pos[0]][pos[1]] = new Room(rewardType, pos[1], pos[0]);

                    rooms.remove(map[pos[0]][pos[1]]);
                }
            }

            // 희생방 지정
            if (!special.isEmpty()) {
                if (isAngelExists || random.chance(14)) {
                    Room sacrifice = random.choice(special);
                    sacrifice.type = "Sacrifice";
                    special.remove(sacrifice);
                }
            }

            // 저주방 지정
            if (!special.isEmpty() && isDevilExists) {
                if (random.chance(50)) {
                    Room curse = random.choice(special);
                    curse.type = "Curse";
                    special.remove(curse);
                }
            }

            // 일반방 추가 지정
            for (Room r : special) {
                r.type = "Normal";
                r.requireATK = random.choice(require);
            }
        }

        void drawRoom(ArrayList<StringBuilder> lines, int startLine, Room room) {
            char center = ' ';
            char wallColumn = '-';
            char wallRow = '|';
            char corner = '+';

            switch (room.type) {
                case "Start":
                    center = 'R';
                    wallColumn = '@';
                    wallRow = '@';
                    corner = '@';
                    break;
                case "Normal":
                    center = (char) (room.requireATK + 48);
                    break;
                case "Boss":
                    center = 'B';
                    wallColumn = '@';
                    wallRow = '@';
                    corner = '@';
                    break;
                case "Hidden":
                    center = 'X';
                    break;
                case "Treasure":
                    center = 'T';
                    break;
                case "Shop":
                    center = 'M';
                    break;
                case "Devil":
                    center = 'D';
                    break;
                case "Angel":
                    center = 'A';
                    break;
                case "Sacrifice":
                    center = 'S';
                    break;
                case "Curse":
                    center = 'C';
                    break;
                case "Empty":
                    wallColumn = ' ';
                    wallRow = ' ';
                    corner = ' ';
                    break;
            }

            boolean[] adj = new boolean[4];

            if (!room.type.equals("Empty") && !room.type.equals("Devil") && !room.type.equals("Angel") && !room.type.equals("Hidden")) {
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    int[] delta = movementDelta[deltaIndex];

                    int curX = room.posX + delta[1];
                    int curY = room.posY + delta[0];

                    if (curX < 0 || curX >= MAP_SIZE || curY < 0 || curY >= MAP_SIZE
                            || map[curY][curX] == null || map[curY][curX].type.equals("Empty")
                            || map[curY][curX].type.equals("Devil") || map[curY][curX].type.equals("Angel")
                            || map[curY][curX].type.equals("Hidden")) continue;

                    adj[deltaIndex] = true;
                }
            }


            if (room.type.equals("Devil") || room.type.equals("Angel")) {
                lines.get(startLine).append('/');

                for (int i = 0; i < 3; i++) lines.get(startLine).append('^');
                lines.get(startLine).append('\\');

                for (int i = 1; i <= 3; i++) {
                    lines.get(startLine + i).append('<').append(' ').append((i == 2 ? center : ' ')).append(' ').append('>');
                }

                lines.get(startLine + 4).append('\\');
                for (int i = 0; i < 3; i++) lines.get(startLine + 4).append('v');
                lines.get(startLine + 4).append('/');
            } else {
                lines.get(startLine).append(corner);

                if (adj[3]) lines.get(startLine).append(corner).append(' ').append(corner);
                else for (int i = 0; i < 3; i++) lines.get(startLine).append(wallColumn);

                lines.get(startLine).append(corner);

                for (int i = 1; i <= 3; i++) {
                    if (i == 2) lines.get(startLine + i)
                            .append(adj[2] ? ' ' : wallRow).append(' ')
                            .append(center).append(' ').append(adj[0] ? ' ' : wallRow);
                    else lines.get(startLine + i)
                            .append(adj[2] ? corner : wallRow).append("   ").append(adj[0] ? corner : wallRow);
                }

                lines.get(startLine + 4).append(corner);

                if (adj[1]) lines.get(startLine + 4).append(corner).append(' ').append(corner);
                else for (int i = 0; i < 3; i++) lines.get(startLine + 4).append(wallColumn);

                lines.get(startLine + 4).append(corner);
            }

            // 벽 잇기
            for (int i = 0; i < 5; i++) {
                if (adj[0] && (i == 1 || i == 3)) lines.get(startLine + i).append('-');
                else lines.get(startLine + i).append(' ');
            }

            if (adj[1]) lines.get(startLine + 5).append(" | |  ");
            else lines.get(startLine + 5).append("      ");
        }

        void drawMap() {
            ArrayList<StringBuilder> lines = new ArrayList<>();

            for (int i = 0; i < 6 * mapHeight + 3; i++) {
                lines.add(new StringBuilder());
                if (i != 0 && i != 6 * mapHeight + 3 - 1) lines.get(i).append("# ");
            }

            for (int i = 0; i < 6 * mapWidth + 3; i++) {
                lines.get(0).append("#");
                lines.get(6 * mapHeight + 3 - 1).append("#");
            }

            // 1번 라인은 way 처리를 해주지 않고 있으므로 예외적으로 직접 처리
            for (int i = 0; i < 6 * mapWidth; i++) lines.get(1).append(" ");

            for (int i = bounds[0]; i <= bounds[1]; i++) {
                for (int j = bounds[2]; j <= bounds[3]; j++) {
                    if (map[i][j] == null) map[i][j] = new Room("Empty", j, i);

                    drawRoom(lines, 6 * (i - bounds[0]) + 2, map[i][j]);
                }
            }

            for (int i = 0; i < 6 * mapHeight + 3; i++) {
                if (i != 0 && i != 6 * mapHeight + 3 - 1) lines.get(i).append("#");

                sb.append(lines.get(i)).append("\n");
            }

            System.out.print(sb);
        }

        void setRoomIndexes() {
            boolean treasureFound = false;

            for (int i = rooms.size() - 1; i >= 0; i--) {
                if (!rooms.get(i).type.equals("Normal")) {
                    switch (rooms.get(i).type) {
                        case "Shop":
                            rooms.get(i).roomIndex = TYPE_SHOP;
                            break;
                        case "Hidden":
                            rooms.get(i).roomIndex = TYPE_HIDDEN;
                            break;
                        case "Treasure":
                            if (!treasureFound) {
                                rooms.get(i).roomIndex = TYPE_TREASURE1;
                                treasureFound = true;
                            } else {
                                rooms.get(i).roomIndex = TYPE_TREASURE2;
                            }
                            break;
                        case "Sacrifice":
                            rooms.get(i).roomIndex = TYPE_SACRIFICE;
                            break;
                        case "Curse":
                            rooms.get(i).roomIndex = TYPE_CURSE;
                            break;
                    }

                    rooms.remove(i);
                }
            }

            for (int i = 0; i < rooms.size(); i++) {
                rooms.get(i).roomIndex = i;
            }
        }
    }

    static class SimulationData {
        int normalVisited, normalAvailable, specialVisited, specialAvailable;
        int HP, ATK, COIN, BOMB;

        SimulationData() {
            normalVisited = 0;
            normalAvailable = 0;
            specialVisited = 0;
            specialAvailable = 0;

            HP = 6;
            ATK = 1;
            COIN = 0;
            BOMB = 3;

            traverseNear(4, 4);
        }

        SimulationData(SimulationData data) {
            this.normalVisited = data.normalVisited;
            this.normalAvailable = data.normalAvailable;
            this.specialVisited = data.specialVisited;
            this.specialAvailable = data.specialAvailable;

            this.HP = data.HP;
            this.ATK = data.ATK;
            this.COIN = data.COIN;
            this.BOMB = data.BOMB;
        }

        void traverseNear(int x, int y) {
            for (int[] delta : movementDelta) {
                int curX = x + delta[1];
                int curY = y + delta[0];

                // 일단 커서에 방이 없으면 continue
                if (curX < 0 || curX >= MAP_SIZE || curY < 0 || curY >= MAP_SIZE || map[curY][curX] == null) continue;

                // 일반 방이라면
                if (map[curY][curX].type.equals("Normal")) {
                    if ((normalVisited & (1 << map[curY][curX].roomIndex)) == 0) {
                        // 방문한 적 없는 방이면 방문 가능 처리
                        normalAvailable |= (1 << map[curY][curX].roomIndex);
                    }
                }
                else { // 특수 방이라면
                    if ((specialVisited & (1 << map[curY][curX].roomIndex)) == 0) {
                        // 방문한 적 없는 방이면

                        switch (map[curY][curX].type) {
                            case "Treasure":
                                // 보물방은 무조건 먹는다
                                specialVisited |= (1 << map[curY][curX].roomIndex);
                                this.ATK++;
                                break;
                            case "Hidden":
                                // 비밀방은 가능하면 무조건 먹는다
                                if (this.BOMB >= 1) {
                                    specialVisited |= (1 << TYPE_HIDDEN);

                                    this.BOMB--;
                                    this.HP += 2;
                                    this.ATK += 2;
                                    this.COIN += 2;

                                } else {
                                    //throw new RuntimeException();
                                    specialAvailable |= (1 << TYPE_HIDDEN);
                                }
                                break;
                            case "Boss":
                            case "Start":
                            case "Devil":
                            case "Angel":
                                break;
                            default:
                                specialAvailable |= (1 << map[curY][curX].roomIndex);
                        }
                    }
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(normalVisited, normalAvailable, specialVisited, specialAvailable, HP, ATK, COIN, BOMB);
        }
    }

    static boolean simulate(SimulationData sim) {
        if (sim.ATK >= 10) return true;
        if (memory.contains(sim.hashCode())) return false;
        memory.add(sim.hashCode());

        // 먼저 소모 없이 입장 가능한 일반방이 있다면 입장한다
        for (int i = 0; i < rooms.size(); i++) {
            if ((sim.normalAvailable & (1 << i)) != 0 &&
                    (sim.normalVisited & (1 << i)) == 0 &&
                    rooms.get(i).requireATK <= sim.ATK) {
                sim.normalVisited |= (1 << i);
                sim.normalAvailable ^= (1 << i);
                sim.traverseNear(rooms.get(i).posX, rooms.get(i).posY);
                sim.COIN++;

                return simulate(sim);
            }
        }

        // 더이상 안전하게 입장 가능한 일반방이 없다면

        // 1. 상점을 간다

        boolean result = false;
        // 1-1) 빨간 물약을 산다? (체력이 필요할 때만 산다)
        //if (sim.COIN >= 2 && (sim.specialAvailable & (1 << TYPE_SHOP)) != 0) {
        if (sim.normalAvailable != 0 && sim.COIN >= 2 && (sim.specialAvailable & (1 << TYPE_SHOP)) != 0 &&
                ((sim.HP <= 2 && sim.BOMB <= 1) || (((sim.specialAvailable & (1 << TYPE_SACRIFICE)) != 0 && sim.HP < 3)))) {
            SimulationData copy = new SimulationData(sim);
            copy.HP++;
            copy.COIN -= 2;

            result = result || simulate(copy);
        }

        // 1-2) 파란 물약을 산다?
        if (sim.COIN >= 2 && (sim.specialAvailable & (1 << TYPE_SHOP)) != 0) {
            SimulationData copy = new SimulationData(sim);
            copy.ATK++;
            copy.COIN -= 2;

            result = result || simulate(copy);
        }

        // 2. 희생방을 간다
        if (sim.HP >= 3 && (sim.specialAvailable & (1 << TYPE_SACRIFICE)) != 0) {
            SimulationData copy = new SimulationData(sim);
            copy.HP -= 2;
            copy.ATK += 3;
            copy.specialAvailable ^= (1 << TYPE_SACRIFICE);
            copy.specialVisited |= (1 << TYPE_SACRIFICE);

            result = result || simulate(copy);
        }

        // 3. 저주방을 간다? (현재 공격력 + (비밀방?) + (희생방?) + (현재 코인 + 남은 일반방의 수) / 2 - 2) 가 10 미만이 되어버린다면 가면 안됨
        if ((sim.specialAvailable & (1 << TYPE_CURSE)) != 0) {
            SimulationData copy = new SimulationData(sim);
            copy.ATK -= 2;
            copy.COIN += 3;
            copy.BOMB += 1;
            copy.specialAvailable ^= (1 << TYPE_CURSE);
            copy.specialVisited |= (1 << TYPE_CURSE);

            if ((sim.specialAvailable & (1 << TYPE_HIDDEN)) != 0) {
                copy.specialAvailable ^= (1 << TYPE_HIDDEN);
                copy.specialVisited |= (1 << TYPE_HIDDEN);

                copy.BOMB--;
                copy.HP += 2;
                copy.ATK += 2;
                copy.COIN += 2;
            }

            result = result || simulate(copy);
        }

        // 4. 폭탄 혹은 체력을 소모해서 일반방을 뚫는다
        if (sim.HP <= 1 && sim.BOMB == 0) return result;

        for (int i = 0; i < rooms.size(); i++) {
            if ((sim.normalAvailable & (1 << i)) != 0 &&
                    (sim.normalVisited & (1 << i)) == 0) {
                SimulationData copy = new SimulationData(sim);
/*
                if (sim.HP >= 2) {
                    SimulationData copy = new SimulationData(sim);
                    copy.HP--;
                    copy.normalVisited |= (1 << i);
                    copy.normalAvailable ^= (1 << i);
                    copy.traverseNear(rooms.get(i).posX, rooms.get(i).posY);
                    copy.COIN++;

                    result = result || simulate(copy);
                }

                if (sim.BOMB >= 1) {
                    SimulationData copy = new SimulationData(sim);
                    copy.BOMB--;
                    copy.normalVisited |= (1 << i);
                    copy.normalAvailable ^= (1 << i);
                    copy.traverseNear(rooms.get(i).posX, rooms.get(i).posY);
                    copy.COIN++;

                    result = result || simulate(copy);
                }
*/

                if (copy.HP == 1) copy.BOMB--;
                else {
                    if ((copy.specialVisited & (1 << TYPE_HIDDEN)) == 0) {
                        if (copy.BOMB >= 2) {
                            copy.BOMB--;
                        }
                        else if (copy.BOMB == 1) {
                            SimulationData scopy = new SimulationData(sim);
                            scopy.HP--;
                            copy.BOMB--;

                            scopy.normalVisited |= (1 << i);
                            scopy.normalAvailable ^= (1 << i);
                            scopy.traverseNear(rooms.get(i).posX, rooms.get(i).posY);
                            scopy.COIN++;

                            result = result || simulate(scopy);
                        }
                        else {
                            copy.HP--;
                        }
                    } else {
                        if (copy.BOMB >= 1) copy.BOMB--;
                        else copy.HP--;
                    }
                }

                copy.normalVisited |= (1 << i);
                copy.normalAvailable ^= (1 << i);
                copy.traverseNear(rooms.get(i).posX, rooms.get(i).posY);
                copy.COIN++;

                result = result || simulate(copy);


            }
        }

        return result;
    }

    static void actual() {
        Game game = new Game();

        game.createMap();
        game.setRoomIndexes();

        if (game.shopPresented && simulate(new SimulationData())) System.out.println("CLEAR");
        else System.out.println("GAME OVER");

        game.drawMap();
    }

    public static void main(String[] args) throws IOException {


        random = new PSRandom(sc.next());
        //long start = System.nanoTime();
        actual();
        //long end = System.nanoTime();

        //System.out.println(((end - start) / 1_000_000) + "ms");
        //System.out.println(MAX_ATK);
    }
}

class IssacScanner {
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
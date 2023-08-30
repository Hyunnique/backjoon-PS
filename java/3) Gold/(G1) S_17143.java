import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 25.
 * @see BoJ 17143 (G1)
 * @category Subset, DFS
 * @note 낚시왕
 *
 *
 *
 */

class Shark implements Comparable<Shark> {
    int row, column;
    int speed, direction, size;
    boolean moved, dead;

    Shark(int row, int column, int speed, int direction, int size) {
        this.row = row;
        this.column = column;
        this.speed = speed;
        this.direction = direction;
        this.size = size;

        this.moved = false;
        this.dead = false;
    }

    @Override
    public int compareTo(Shark o) {
        return Integer.compare(size, o.size);
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }
}
public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int R, C, M;
    static int[][] movementDelta = { {}, { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

    public static void main(String[] args) throws Exception {

        R = sc.nextInt();
        C = sc.nextInt();
        M = sc.nextInt();

        List<Shark> sharkList = new ArrayList<>();
        Shark[][] sharkMap = new Shark[R][C];

        for (int i = 0; i < M; i++) {
            Shark shark = new Shark(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt(), sc.nextInt(), sc.nextInt());
            sharkList.add(shark);
            sharkMap[shark.row][shark.column] = shark;
        }

        int result = 0;
        for (int i = 0; i < C; i++) {
            // 상어 잡기
            int cursor = 0;

            /*
            System.out.println("t=" + (i + 1));
            for (int rr = 0; rr < R; rr++) {
                System.out.println(Arrays.toString(sharkMap[rr]));
            }
            */
            while (cursor + 1 < R && (sharkMap[cursor][i] == null || sharkMap[cursor][i].dead)) {
                ++cursor;
            }

            if (sharkMap[cursor][i] != null && !sharkMap[cursor][i].dead) {
                result += sharkMap[cursor][i].size;
                //System.out.println(result);
                sharkList.remove(sharkMap[cursor][i]);
                sharkMap[cursor][i] = null;
            }

            // 상어 이동

            for (int j = 0; j < sharkList.size() - 1; j++) {
                sharkList.get(j).moved = false;
            }

            for (int j = sharkList.size() - 1; j >= 0; j--) {
                Shark shark = sharkList.get(j);
                if (shark.dead) {
                    sharkList.remove(j);
                    continue;
                }

                int moveCount = shark.speed;

                int prevRow = shark.row;
                int prevColumn = shark.column;
                boolean prevAlreadyFilled = (sharkMap[prevRow][prevColumn] != shark);

                while (moveCount-- > 0) {
                    int curRow = shark.row + movementDelta[shark.direction][0];
                    int curColumn = shark.column + movementDelta[shark.direction][1];

                    if (curRow < 0 || curRow >= R || curColumn < 0 || curColumn >= C) {
                        if (shark.direction == 1) shark.direction = 2;
                        else if (shark.direction == 2) shark.direction = 1;
                        else if (shark.direction == 3) shark.direction = 4;
                        else shark.direction = 3;

                        moveCount++;
                        continue;
                    }

                    shark.row = curRow;
                    shark.column = curColumn;
                }

                if (sharkMap[shark.row][shark.column] == shark) {
                    ;
                }
                else if (sharkMap[shark.row][shark.column] == null || (sharkMap[shark.row][shark.column] != null && sharkMap[shark.row][shark.column].dead)) {
                    sharkMap[shark.row][shark.column] = shark;
                    if (!prevAlreadyFilled) sharkMap[prevRow][prevColumn] = null;
                }
                else if (sharkMap[shark.row][shark.column] != null && sharkMap[shark.row][shark.column].moved) {
                    if (shark.compareTo(sharkMap[shark.row][shark.column]) > 0) {
                        sharkMap[shark.row][shark.column].dead = true;
                        sharkMap[shark.row][shark.column] = shark;
                    } else {
                        shark.dead = true;
                    }

                    if (!prevAlreadyFilled) sharkMap[prevRow][prevColumn] = null;
                }
                else if (sharkMap[shark.row][shark.column] != null && !sharkMap[shark.row][shark.column].moved) {
                    sharkMap[shark.row][shark.column] = shark;
                    if (!prevAlreadyFilled) sharkMap[prevRow][prevColumn] = null;
                }

                shark.moved = true;
                //System.out.println("shark " + shark.size + " moved to " + shark.row + "," + shark.column);
            }
        }

        System.out.println(result);
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
import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 21.
 * @see BoJ 15683 (G4)
 * @category Implement, Bruteforce
 * @note 감시
 *
 * 모든 감시카메라의 방향 조합을 고려해서 완전탐색 
 * 
 */

class Camera {
    int row, column, type;

    Camera(int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }
}

public class Main {
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static int[][][][] cameraType = {
        {
            { { -1, 0 } },
            { { 1, 0 } },
            { { 0, -1 } },
            { { 0, 1 } }
        },
        {
            { { -1, 0 }, { 1, 0 } },
            { { 0, -1 }, { 0, 1 } }
        },
        {
            { { -1, 0 }, { 0, 1 } },
            { { 0, 1 }, { 1, 0 } },
            { { 1, 0 }, { 0, -1 } },
            { { 0, -1 }, { -1, 0 } }
        },
        {
            { { -1, 0 }, { 0, 1 }, { 1, 0 } },
            { { 0, 1 }, { 1, 0 }, { 0, -1 } },
            { { 1, 0 }, { 0, -1 }, { -1, 0 } },
            { { 0, -1 }, { -1, 0 }, { 0, 1 } }
        },
        {
            { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }
        }
    };
    static List<Camera> cameraList;
    static int totalTile = 0, minTile = Integer.MAX_VALUE;
    static char[][] map;
    public static void main(String[] args) throws Exception {
        N = sc.nextInt();
        M = sc.nextInt();
        
        map = new char[N][M];

        cameraList = new ArrayList<>();

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < M; column++) {
                map[row][column] = sc.nextChar();
                
                if (map[row][column] == '0') ++totalTile;

                if (map[row][column] != '0' &&
                    map[row][column] != '#' &&
                    map[row][column] != '6') {
                    cameraList.add(new Camera(row, column, map[row][column] - 49));
                }
            }
        }
        //sb.append("total=").append(totalTile).append("\n");
        simulate(0, totalTile);

        System.out.println(minTile);
    }

    static void simulate(int nth, final int remainTile) {
        //System.out.println(nth + "th, " + remainTile);
        if (nth == cameraList.size()) {
            /*
                if (minTile > remainTile) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        sb.append(map[i][j]).append(" ");
                    }
                    sb.append("\n");
                }
                sb.append(remainTile).append("\n");
            }
            */
            minTile = Math.min(minTile, remainTile);

            return;
        }

        int curRow, curColumn;
        Camera currentCamera = cameraList.get(nth);
        boolean[][] paint = new boolean[N][M];
        
        for (int[][] directionSet : cameraType[currentCamera.type]) {
            int currentRemainTile = remainTile;
            

            for (int[] direction : directionSet) {
                curRow = currentCamera.row + direction[0];
                curColumn = currentCamera.column + direction[1];

                while (curRow >= 0 && curRow < N &&
                curColumn >= 0 && curColumn < M &&
                map[curRow][curColumn] != '6') {
                    if (map[curRow][curColumn] == '0') {
                        paint[curRow][curColumn] = true;
                        map[curRow][curColumn] = '#';
                        currentRemainTile--;
                    }

                    curRow += direction[0];
                    curColumn += direction[1];
                }
            }

            simulate(nth + 1, currentRemainTile);
            repaint(paint);
        }
    }

    static void repaint(boolean[][] paint) {
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < M; column++) {
                if (paint[row][column]) {
                    paint[row][column] = false;
                    map[row][column] = '0';
                }
            }
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
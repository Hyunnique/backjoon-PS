import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 16.
 * @see BoJ 12094 (P4)
 * @category Backtracking
 * @note 2048 (Hard)
 * 
 * 보드의 크기 N (1 <= N <= 20)
 * 이동 횟수 10번
 * 그냥 Easy 코드 동일하게 넣으니 맞음
 * 
 */

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
    static BufferedScanner sc = new BufferedScanner();
    static List<int[][]> initialArray = new ArrayList<>();
    static int result = 0;

    public static void main(String[] args) throws Exception {

        N = sc.nextInt();

        int[][] arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        game(arr, 0);

        System.out.println(result);
    }

    static void game(int[][] arr, int cnt) {
        if (arr == null) return;
        /*
        System.out.println("-------");
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
        */
        for (int i = 0; i < 4; i++) {
            game(cascade(i, arr, cnt + 1), cnt + 1);
        }
    }

    static int[][] cascade(int direction, int[][] arr, int cnt) {
        if (cnt == 11) {
            for (int row = 0; row < N; row++) {
                for (int column = 0; column < N; column++) {
                    result = Math.max(result, arr[row][column]);
                }
            }

            return null;
        }

        int[][] result = new int[N][N];
        int cursor = 0, latest = 0;
        switch (direction) {
            case 0: // Down
                for (int column = 0; column < N; column++) {
                    cursor = 0;
                    latest = 0;
                    for (int row = N - 1; row >= 0; row--) {
                        if (arr[row][column] == 0) continue;

                        if (latest == 0) {
                            latest = arr[row][column];
                        } else {
                            if (latest == arr[row][column]) {
                                result[N - 1 - cursor++][column] = latest * 2;
                                latest = 0;
                            } else {
                                result[N - 1 - cursor++][column] = latest;
                                latest = arr[row][column];
                            }
                        }
                    }

                    if (latest != 0) result[N - 1 - cursor++][column] = latest;
                }
                break;
            case 1: // Up
                for (int column = 0; column < N; column++) {
                    cursor = 0;
                    latest = 0;
                    for (int row = 0; row < N; row++) {
                        if (arr[row][column] == 0) continue;

                        if (latest == 0) {
                            latest = arr[row][column];
                        } else {
                            if (latest == arr[row][column]) {
                                result[cursor++][column] = latest * 2;
                                latest = 0;
                            } else {
                                result[cursor++][column] = latest;
                                latest = arr[row][column];
                            }
                        }
                    }

                    if (latest != 0) result[cursor++][column] = latest;
                }
                break;
            case 2: // Right
                for (int row = 0; row < N; row++) {
                    cursor = 0;
                    latest = 0;
                    for (int column = N - 1; column >= 0; column--) {
                        if (arr[row][column] == 0) continue;

                        if (latest == 0) {
                            latest = arr[row][column];
                        } else {
                            if (latest == arr[row][column]) {
                                result[row][N - 1 - cursor++] = latest * 2;
                                latest = 0;
                            } else {
                                result[row][N - 1 - cursor++] = latest;
                                latest = arr[row][column];
                            }
                        }
                    }

                    if (latest != 0) result[row][N - 1 - cursor++] = latest;
                }
                break;
            case 3: // Left
                for (int row = 0; row < N; row++) {
                    cursor = 0;
                    latest = 0;
                    for (int column = 0; column < N; column++) {
                        if (arr[row][column] == 0) continue;

                        if (latest == 0) {
                            latest = arr[row][column];
                        } else {
                            if (latest == arr[row][column]) {
                                result[row][cursor++] = latest * 2;
                                latest = 0;
                            } else {
                                result[row][cursor++] = latest;
                                latest = arr[row][column];
                            }
                        }
                    }

                    if (latest != 0) result[row][cursor++] = latest;
                }
                break;
        }

        return result;
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 09.
 * @see BoJ 16935
 * @category 구현
 * @note
 * 
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
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static int[][] arr;
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        M = cr.nextInt();
        int R = cr.nextInt();

        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                arr[i][j] = cr.nextInt();
            }
        }

        for (int i = 0; i < R; i++) {
            switch (cr.nextInt()) {
                case 1:
                    for (int row = 0; row < N / 2; row++) {
                        for (int column = 0; column < M; column++) {
                            swap(row, column, N - row - 1, column);
                        }
                    }
                    break;
                case 2:
                    for (int column = 0; column < M / 2; column++) {
                        for (int row = 0; row < N; row++) {
                            swap(row, column, row, M - column - 1);
                        }
                    }
                    break;
                case 3:
                    rotate(true);
                    break;          
                case 4:
                    rotate(false);
                    break;
                case 5:
                    rotateGroup(true);
                    break;
                case 6:
                    rotateGroup(false);
                    break;
            }
        }

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < M; column++) {
                sb.append(arr[row][column]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    static void swap(int r1, int c1, int r2, int c2) {
        int t = arr[r1][c1];
        arr[r1][c1] = arr[r2][c2];
        arr[r2][c2] = t;
    }

    static void rotate(boolean clockwise) {
        int t = N;
        N = M;
        M = t;

        int[][] newArr = new int[N][M];

        for (int row = 0; row < M; row++) {
            for (int column = 0; column < N; column++) {
                int destRow = (clockwise ? column : N - 1 - column);
                int destColumn = (clockwise ? M - 1 - row : row);

                newArr[destRow][destColumn] = arr[row][column];
            }
        }

        arr = newArr;
    }

    static void rotateGroup(boolean clockwise) {
        int[][] newArr = new int[N][M];

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < M; column++) {
                if (row < N / 2 && column < M / 2) {
                    // 제2사분면
                    if (clockwise) newArr[row][column + M / 2] = arr[row][column];
                    else newArr[row + N / 2][column] = arr[row][column];
                }
                else if (row < N / 2 && column >= M / 2) {
                    // 제1사분면
                    if (clockwise) newArr[row + N / 2][column] = arr[row][column];
                    else newArr[row][column - M / 2] = arr[row][column];
                }
                else if (row >= N / 2 && column < M / 2) {
                    // 제3사분면
                    if (clockwise) newArr[row - N / 2][column] = arr[row][column];
                    else newArr[row][column + M / 2] = arr[row][column];
                }
                else {
                    // 제4사분면
                    if (clockwise) newArr[row][column - M / 2] = arr[row][column];
                    else newArr[row - N / 2][column] = arr[row][column];
                }
            }
        }

        arr = newArr;
    }
}

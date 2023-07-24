// Default
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static int N, M;
    public static int[][] arr;
    public static int[][][] tetris = {
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } },
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } },

            { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },

            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } },
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 } },
            { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } },
            { { 1, 0 }, { 1, 1 }, { 1, 2 }, { 0, 2 } },
            { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 2, 0 } },
            { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 } },
            { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 } },
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } },

            { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } },
            { { 1, 0 }, { 1, 1 }, { 0, 1 }, { 0, 2 } },
            { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 2, 0 } },
            { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 } },

            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } },
            { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, 2 } },
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } },
            { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }
        };
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }

            
        }

        int max = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int[][] tetrimino : tetris) {
                    int ss = getSum(tetrimino, i, j);
                    
                    max = Math.max(max, ss);
                }
            }
        }

        System.out.println(max);
        
    }

    public static int getSum(int[][] tetrimino, int r, int c) {
        int blockSum = 0;
        for (int[] v : tetrimino) {
            if (r + v[0] >= 0 && r + v[0] < N && c + v[1] >= 0 && c + v[1] < M) {
                blockSum += arr[r + v[0]][c + v[1]];
            } else return -1;
        }

        return blockSum;
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @author Hyunnique
 * @since 2023. 08. 10.
 * @see BoJ 17406 (G4)
 * @category Shortest Path
 * @note
 * 
 * 또열 돌리기
 * 회전 연산들의 모든 Permutation을 구해서
 * 그 연산 순서에 대해서 직접 구현 계산해보기
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

class RotationArea {
    int index;
    int startRow, startColumn, endRow, endColumn;
    int size;

    RotationArea(int index, int r, int c, int s) {
        this.index = index;
        this.size = s * 2 + 1;

        this.startRow = r - s;
        this.startColumn = c - s;
        this.endRow = r + s;
        this.endColumn = c + s;
    }
}

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static RotationArea[] rotAreas;
    static int[][] vec = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        int M = cr.nextInt();
        int K = cr.nextInt();

        int[][] originalArr = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                originalArr[i][j] = cr.nextInt();
            }
        }

        rotAreas = new RotationArea[K];

        for (int i = 0; i < K; i++) {
            int r = cr.nextInt();
            int c = cr.nextInt();
            int s = cr.nextInt();

            rotAreas[i] = new RotationArea(i, r - 1, c - 1, s);
        }

        int min = Integer.MAX_VALUE;

        int[][] arr = new int[N][M];

        do {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    arr[i][j] = originalArr[i][j];
                }
            }

            for (int i = 0; i < rotAreas.length; i++) {
                for (int rotLine = 0; rotLine < (int)(Math.ceil((double) rotAreas[i].size / 2)); rotLine++) {
                    Queue<Integer> q = new ArrayDeque<>();

                    int mv = 0, vecIndex = 0;
                    int curN = rotAreas[i].startRow + rotLine, curM = rotAreas[i].startColumn + rotLine;
                    int move = (rotAreas[i].size - rotLine * 2 - 1);

                    for (int j = 0; j < Math.max(1, move * 4); j++) {
                        //System.out.println(curN + "," + curM + "," + mv);
                        q.offer(arr[curN][curM]);

                        if (mv == move) {
                            vecIndex++;
                            mv = 1;
                        } else {
                            mv++;
                        }

                        curN += vec[vecIndex][0];
                        curM += vec[vecIndex][1];

                        if (vecIndex == 4) break;
                    }

                    q.offer(q.poll());

                    mv = 0;
                    vecIndex = 0;
                    curN = rotAreas[i].startRow + rotLine;
                    curM = rotAreas[i].startColumn + rotLine;

                    for (int j = 0; j < move * 4; j++) {
                        arr[curN][curM] = q.poll();

                        if (mv == move) {
                            vecIndex++;
                            mv = 1;
                        } else {
                            mv++;
                        }

                        curN += vec[vecIndex][0];
                        curM += vec[vecIndex][1];

                        if (vecIndex == 4) break;
                    }
                }
            }

            int tableSum = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                int sum = 0;
                for (int j = 0; j < M; j++) {
                    sum += arr[i][j];
                }

                tableSum = Math.min(tableSum, sum);
            }

            min = Math.min(min, tableSum);
        } while (nextPermutation());

        System.out.println(min);
    }

    static boolean nextPermutation() {
        int lastPeak = rotAreas.length - 1;
        while (lastPeak > 0 && rotAreas[lastPeak - 1].index >= rotAreas[lastPeak].index) --lastPeak;
        if (lastPeak == 0) return false;

        int nextPeak = rotAreas.length - 1;
        while (rotAreas[lastPeak - 1].index >= rotAreas[nextPeak].index) --nextPeak;

        swap(rotAreas, nextPeak, lastPeak - 1);

        for (int left = lastPeak, right = rotAreas.length - 1; left < right; left++, right--) {
            swap(rotAreas, left, right);
        }

        return true;
    }

    static void swap(RotationArea[] src, int a, int b) {
        RotationArea temp = src[a];
        src[a] = src[b];
        src[b] = temp;
    }
}

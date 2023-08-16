import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 11.
 * @see BoJ 15686 (G5)
 * @note 치킨 배달
 * @category Combination
 * 
 * 폐업시키지 않고 유지할 치킨집의 조합을 구하고
 * 각각 인덱스 비교
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

class Position {
    int row, column;

    Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int compareDistance(Position o) {
        return Math.abs(row - o.row) + Math.abs(column - o.column);
    }
}

public class Main {   
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static List<Position> chickenShops, houses;
    static int[] minArray;
    static int minResult = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        M = cr.nextInt();

        int[][] city = new int[N][N];

        chickenShops = new ArrayList<>();
        houses = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                city[i][j] = cr.nextInt();

                if (city[i][j] == 1) houses.add(new Position(i, j));
                else if (city[i][j] == 2) chickenShops.add(new Position(i, j));
            }
        }

        minArray = new int[houses.size()];

        makeCombination(0, new Position[M], 0);

        System.out.println(minResult);
    }

    static void makeCombination(int nth, Position[] chosen, int startIndex) {
        if (nth == chosen.length) {
            Arrays.fill(minArray, Integer.MAX_VALUE);
            for (int i = 0; i < houses.size(); i++) {
                int minChicken = Integer.MAX_VALUE;
                for (int j = 0; j < chosen.length; j++) {
                    minChicken = Math.min(minChicken, houses.get(i).compareDistance(chosen[j]));
                }

                minArray[i] = Math.min(minArray[i], minChicken);
            }

            int chickenDistance = 0;
            for (int i = 0; i < minArray.length; i++) {
                chickenDistance += minArray[i];
            }

            if (minResult > chickenDistance) {
                //System.out.println(Arrays.toString(minArray));
                minResult = Math.min(minResult, chickenDistance);
            }
            return;
        }
        
        for (int i = startIndex; i < chickenShops.size(); i++) {
            chosen[nth] = chickenShops.get(i);
            makeCombination(nth + 1, chosen, i + 1);
        }
    }
}

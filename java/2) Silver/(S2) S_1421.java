import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * BoJ 1421
 * 
 * 문제)
 * N개의 나무가 있고 각각 길이가 다름
 * 자를 때 C원이 소모, 나무를 판매할 때 단위 당 W원 흭득
 * 최고 이익을 보게끔 나무의 길이를 모두 같게 만들기
 * 
 * 
 */

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        int max = -1;
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            max = Math.max(max, arr[i]);
        }

        long profitMax = 0;
        for (int pLength = 1; pLength <= max; pLength++) { // 나무 조각의 길이를 pLength 로 통일시키고 나머지 버림
            long profit = 0;

            for (int i = 0; i < N; i++) {
                long expenseCount = (arr[i] % pLength == 0 ? arr[i] / pLength - 1 : arr[i] / pLength);
                long currentProfit = (arr[i] / pLength) * pLength * W - expenseCount * C;

                if (currentProfit > 0) profit += currentProfit;
            }

            profitMax = Math.max(profitMax, profit);
        }

        System.out.println(profitMax);
    }

}
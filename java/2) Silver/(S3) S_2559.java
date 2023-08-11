import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int maximum, currentSum = 0;

        // 헷갈리니까 그냥 처음은 먼저 구해놓기
        for (int i = 0; i < K; i++) {
            currentSum += arr[i];
        }

        maximum = currentSum;

        for (int i = K; i < N; i++) {
            currentSum -= arr[i - K];
            currentSum += arr[i];

            maximum = Math.max(currentSum, maximum);
        }

        System.out.println(maximum);
    }

}
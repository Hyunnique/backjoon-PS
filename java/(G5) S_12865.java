import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Pair {
    int weight, value;

    Pair(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Pair[] th = new Pair[N];
        int[][] dp = new int[N + 1][K + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            th[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (int i = 1; i <= N; i++) {
            if (th[i - 1].weight <= K) dp[i][th[i - 1].weight] = Math.max(dp[i][th[i - 1].weight], th[i - 1].value);

            for (int j = 0; j <= K; j++) {
                if (j - th[i - 1].weight < 0 || dp[i - 1][j - th[i - 1].weight] == 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - th[i - 1].weight] + th[i - 1].value);
                }
            }

            //System.out.println(Arrays.toString(dp[i]));
        }

        int max = -1;
        for (int i = 0; i <= K; i++) {
            max = Math.max(max, dp[N][i]);
        }

        System.out.println(max);
    }
}

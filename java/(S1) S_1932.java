import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1932
 * 
 * 그냥 Tree Traversal 하면 2^500
 * 밑에서부터 최소합 계산해서 DP Table의 형태로 저장
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        int[][] arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j <= i; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        for (int cursor = N - 1; cursor > 0; cursor--) {
            for (int i = 0; i < cursor; i++) {
                arr[cursor - 1][i] += Math.max(arr[cursor][i], arr[cursor][i+1]);
            }
        }

        System.out.println(arr[0][0]);
    }
}

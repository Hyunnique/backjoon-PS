import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        int[][] arr = new int[N][N];

        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine());
            
            for (int column+
            ++ = 0; column < N; column++) {
                arr[row][column] = Integer.parseInt(st.nextToken());
                if (row - 1 >= 0) arr[row][column] += arr[row - 1][column];
                if (column - 1 >= 0) arr[row][column] += arr[row][column - 1];
                if (row - 1 >= 0 && column - 1 >= 0) arr[row][column] -= arr[row - 1][column - 1];
            }
        }
        
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y1 = Integer.parseInt(st.nextToken()) - 1;
            int x1 = Integer.parseInt(st.nextToken()) - 1;
            int y2 = Integer.parseInt(st.nextToken()) - 1;
            int x2 = Integer.parseInt(st.nextToken()) - 1;
            
            int sum = arr[y2][x2];
            if (x1 - 1 >= 0) sum -= arr[y2][x1 - 1];
            if (y1 - 1 >= 0) sum -= arr[y1 - 1][x2];
            if (x1 - 1 >= 0 && y1 - 1 >= 0) sum += arr[y1 - 1][x1 - 1];

            result.append(sum + "\n");
        }

        System.out.println(result);
    }
}

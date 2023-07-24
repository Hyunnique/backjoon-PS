// Default
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];

        int sum = 0;
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            sum += Integer.parseInt(st.nextToken());
            arr[i] = sum;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (a == 1) System.out.println(arr[b - 1]);
            else System.out.println(arr[b - 1] - arr[a - 2]);
        }
    }
}

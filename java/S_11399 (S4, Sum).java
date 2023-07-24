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
        
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(arr);

        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
            arr[i] = sum;
        }

        sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        System.out.println(sum);
    }
}

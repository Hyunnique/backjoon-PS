import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        double sum = 0;
        int max = 0;
        double[] arr = new double[N];

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(st.nextToken());

            max = Math.max(max, x);
            arr[i] = x;
        }

        for (int i = 0; i < N; i++) {
            sum += arr[i] / (double)max * 100;
        }

        System.out.println(sum / N);
    }
}

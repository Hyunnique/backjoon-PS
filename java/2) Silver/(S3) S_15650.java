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
        
        int[] arr = new int[M];

        for (int i = 0; i < M; i++) {
            arr[i] = i + 1;
        }

        int cursor = M - 1;

        for (int i = 0; i < M; i++) {
            result.append(arr[i] + " ");
        }
        result.append("\n");
        
        while (cursor != -1) {
            if (arr[cursor] >= N - (M - 1 - cursor)) {
                cursor--;
                continue;
            }

            arr[cursor]++;

            for (int i = cursor + 1; i < M; i++) {
                arr[i] = arr[cursor] + (i - cursor);
            }
            cursor = M - 1;

            if (arr[cursor] >= N - (M - 1 - cursor)) {
                cursor--;
            }

            for (int i = 0; i < M; i++) {
                result.append(arr[i] + " ");
            }
            result.append("\n");
        }

        System.out.println(result);
    }
}

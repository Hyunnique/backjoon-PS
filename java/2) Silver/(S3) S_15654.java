import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main {
    static int N, M;
    static int cnt = 0;
    static StringBuilder sb = new StringBuilder();
    static int[] numbers;
    static int[] dict;
    static boolean[] chosen;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dict = new int[N];
        chosen = new boolean[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            dict[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(dict);

        numbers = new int[M];
        makePermutation(0);

        System.out.println(sb.toString());
    }

    static void makePermutation(int idx) {
        if (idx == M) {
            for (int i = 0; i < M; i++) {
                sb.append(numbers[i]).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i = 0; i < N; i++) {
            if (chosen[i]) continue;

            numbers[idx] = dict[i];
            chosen[i] = true;
            makePermutation(idx + 1);
            chosen[i] = false;
        }
    }
}
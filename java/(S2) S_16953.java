import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static int A;
    static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder sb = new StringBuilder();
        
        A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        int result = dp(B);
        if (result == -1) System.out.println(-1);
        else System.out.println(result + 1);
    }

    static int dp(int x) {
        if (map.containsKey(x)) return map.get(x);

        if (x < A) return -1;
        if (x == A) return 0;

        int min = Integer.MAX_VALUE;

        if (x % 10 == 1) {
            int a = dp(x / 10);
            if (a != -1) min = Math.min(min, a + 1);
        }
        if (x % 2 == 0) {
            int b = dp(x / 2);
            if (b != -1) min = Math.min(min, b + 1);
        }

        if (min == Integer.MAX_VALUE) min = -1;

        map.put(x, min);
        return min;
    }
}
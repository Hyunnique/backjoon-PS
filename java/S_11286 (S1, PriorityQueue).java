import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        
        PriorityQueue<Integer> q = new PriorityQueue<>((x, y) -> {
            if (Math.abs(x) == Math.abs(y)) {
                return x > y ? 1 : -1;
            } else {
                return Math.abs(x) - Math.abs(y);
            }
        });

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(br.readLine());

            if (x == 0) {
                if (q.isEmpty()) result.append("0\n");
                else result.append(q.poll() + "\n");
            } else {
                q.add(x);
            }
        }

        System.out.println(result);
    }
}

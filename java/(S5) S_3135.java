import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(br.readLine());

        int nearestDiff = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(br.readLine());
            if (Math.abs(x - B) < nearestDiff) {
                nearestDiff = Math.abs(x - B);
            }
        }

        System.out.println(Math.min(Math.abs(A - B), nearestDiff + 1));

    }
}
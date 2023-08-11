import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //N = Integer.parseInt(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        
        int result = 0;

        if (A == B && B == C) {
            result = 10000 + (1000 * A);
        }
        else if (A == B || B == C || A == C) {
            result = 1000 + (100 * (A == B ? A : (B == C ? B : C)));
        } else {
            result = 100 * Math.max(A, Math.max(B, C));
        }

        System.out.println(result);
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        int yak = gcd(N, M);
        System.out.println(yak);
        System.out.println((N * M) / yak);
    }

    public static int gcd(int N, int M) {
        int big = Math.max(N, M);
        int small = Math.min(N, M);

        if (big % small == 0) return small;
        
        return gcd(big % small, small);
    }
}

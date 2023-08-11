import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author 김대현
 * @since 2023. 08. 03.
 * @note
 * 
 * 
 */

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        if (N == 1) {
            System.out.println("2\n3\n5\n7\n");
        } else {
            getPrime(2, 2);
            getPrime(3, 2);
            getPrime(5, 2);
            getPrime(7, 2);

            System.out.println(sb.toString());
        }
    }

    static void getPrime(int startsWith, int len) {
        startsWith = startsWith * 10;
        for (int i = 1; i <= 9; i++) {
            if (i % 2 == 0) continue;
            if (isPrime(startsWith + i)) {
                if (len == N) sb.append(startsWith + i).append("\n");
                else getPrime(startsWith + i, len + 1);
            }
        }
    }
    
    static boolean isPrime(int x) {
        boolean p = true;

        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                p = false;
                break;
            }
        }

        return p;
    }
}

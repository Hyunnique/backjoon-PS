import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        
        int result = -1;
        if (N == 0) result = 0;
        else {
            for (int i = 0; i < 5; i++) {
                if ((N - 3 * i) % 5 == 0 && (N - 3 * i) >= 0) {
                    result = (int)((N - 3 * i) / 5) + i; 
                    break;
                }
            }
        }

        System.out.println(result);
    }
}

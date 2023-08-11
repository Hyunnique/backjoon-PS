import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder result = new StringBuilder();

        while (true) {
            StringBuilder str = new StringBuilder(br.readLine());
            StringBuilder comp = new StringBuilder(str);
            comp.reverse();
            
            if (str.charAt(0) == '0') break;

            boolean result = true;
            for (int i = 0; i < str.length() / 2; i++) {
                if (str.charAt(i) != comp.charAt(i)) {
                    result = false;
                    break;
                }
            }

            if (result) System.out.println("yes");
            else System.out.println("no");
        }
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        
        Set<Integer> s = new HashSet<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            
            String input = st.nextToken();
            int x = -1;

            switch (input) {
                case "add":
                x = Integer.parseInt(st.nextToken());
                s.add(x);
                break;
                case "remove":
                x = Integer.parseInt(st.nextToken());
                s.remove(x);
                break;
                case "check":
                x = Integer.parseInt(st.nextToken());
                result.append(s.contains(x) ? "1\n" : "0\n");
                break;
                case "toggle":
                x = Integer.parseInt(st.nextToken());
                if (s.contains(x)) s.remove(x);
                else s.add(x);
                break;
                case "all":
                for (int t = 1; t <= 20; t++) {
                    s.add(t);
                }
                break;
                case "empty":
                for (int t = 1; t <= 20; t++) {
                    s.remove(t);
                }
                break;
            }
        }

        System.out.println(result);
    }
}

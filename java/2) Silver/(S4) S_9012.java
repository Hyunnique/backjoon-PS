import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        
        
        
        for (int i = 0; i < N; i++) {
            Stack<Character> s = new Stack<>();
            String str = br.readLine();
            boolean r = false;

            for (char c : str.toCharArray()) {
                if (c == '(') {
                    s.add('(');
                } else {
                    if (s.size() == 0) r = true;
                    else s.pop();
                }
            }

            if (s.size() == 0 && !r) System.out.println("YES");
            else System.out.println("NO");
        }
        System.out.println(result);
    }
}

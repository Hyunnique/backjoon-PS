import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder result = new StringBuilder();

        Stack<Character> s = new Stack<>();

        String str = br.readLine();
        String bomb = new StringBuilder(br.readLine()).reverse().toString();
        int bombLength = bomb.length();
        StringBuilder sb = new StringBuilder();

        int cursor = 0;
        while (cursor <= str.length()) {

            sb.setLength(0);
            //System.out.println(s.toString());
            int _s = s.size();

            if (s.size() >= bombLength && s.lastElement() == bomb.charAt(0)) {
                for (int i = 0; i < Math.min(_s, bombLength); i++) {
                    sb.append(s.pop());
                }
                
                //System.out.println(sb.toString());
                if (sb.toString().equals(bomb)) {
                    continue;
                } else {
                    for (int i = sb.length() - 1; i >= 0; i--) {
                        s.add(sb.charAt(i));
                    }
                }
            }
            if (cursor == str.length()) break;

            s.add(str.charAt(cursor));
            cursor++;
        }
        
        if (s.size() == 0) System.out.println("FRULA");
        else {
            StringBuilder result = new StringBuilder();

            while (s.size() != 0) {
                result.append(s.pop());
            }

            System.out.println(result.reverse());
        }
    }
}

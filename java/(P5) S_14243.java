import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0);
        map.put('B', 0);
        map.put('C', 0);

        for (char s : br.readLine().toCharArray()) {
            map.put(s, map.get(s) + 1);
        }

        char prev = '0', pprev = '0';
        while (true) {
            int cntA = map.get('A');
            int cntB = map.get('B');
            int cntC = map.get('C');

            boolean availableC = (prev != 'C' && pprev != 'C' && cntB - cntA - cntC <= 1 && cntC > 0);
            boolean availableB = (prev != 'B' && 2 * cntC - cntA - cntB <= 1 && cntB > 0);

            pprev = prev;

            if (availableB && availableC) {
                if (cntC >= cntB) {
                    sb.append('C');
                    map.put('C', map.get('C') - 1);
                    prev = 'C';
                } else {
                    sb.append('B');
                    map.put('B', map.get('B') - 1);
                    prev = 'B';
                }
            } else if (availableC) {
                sb.append('C');
                map.put('C', map.get('C') - 1);
                prev = 'C';
            } else if (availableB) {
                sb.append('B');
                map.put('B', map.get('B') - 1);
                prev = 'B';
            } else {
                if (cntA > 0) {
                    sb.append('A');
                    map.put('A', map.get('A') - 1);
                    prev = 'A';
                } else {
                    sb = new StringBuilder("-1");
                    break;
                }
            }

            //System.out.println(sb.toString());
            if (map.get('A') == 0 && map.get('B') == 0 && map.get('C') == 0) break;
        }

        System.out.println(sb.toString());
    }
}

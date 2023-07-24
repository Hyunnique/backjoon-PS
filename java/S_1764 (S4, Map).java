import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Map<String, Integer> map = new TreeMap<>();

        for (int i = 0; i < N; i++) {
            map.put(br.readLine(), 1);
        }

        Map<String, Integer> smap = new TreeMap<>(map);

        int sum = 0;
        for (int i = 0; i < M; i++) {
            String t = br.readLine();
            if (smap.containsKey(t)) {
                sum++;
                smap.put(t, 2);
            }
        }

        result.append(sum + "\n");

        for (String k : smap.keySet()) {
            if (smap.get(k) == 2) result.append(k + "\n");
        }
        System.out.println(result);
    }
}

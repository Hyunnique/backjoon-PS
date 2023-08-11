import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Map<String, Integer> map = new HashMap<>();
        String[] arr = new String[N];

        for (int i = 1; i <= N; i++) {
            String str = br.readLine();
            map.put(str, i);
            arr[i - 1] = str;
        }

        for (int i = 0; i < M; i++) {
            String str = br.readLine();
            if (map.containsKey(str)) {
                result.append(map.get(str) + "\n");
            } else {
                result.append(arr[Integer.parseInt(str) - 1] + "\n");
            }
        }
        
        System.out.println(result);
    }
}

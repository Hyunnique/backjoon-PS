import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        int[] tower = new int[N];
        int[] result = new int[N];
        TreeMap<Integer, Integer> map = new TreeMap<>();

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            tower[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = N - 1; i >= 0; i--) {
            while (true) {
                if (map.size() == 0 || map.firstKey() > tower[i]) break;
                
                result[map.firstEntry().getValue()] = i + 1;
                map.remove(map.firstKey());
            }

            map.put(tower[i], i);
        }

        for (int i = 0; i < N; i++) sb.append(result[i]).append(" ");
        System.out.println(sb.toString());
    }
}

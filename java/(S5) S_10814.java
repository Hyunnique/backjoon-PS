import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        
        TreeMap<Integer, ArrayList<String>> map = new TreeMap<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            String y = st.nextToken();
            
            if (map.containsKey(x)) {
                map.get(x).add(y);
            } else {
                ArrayList<String> l = new ArrayList<>();
                l.add(y);
                map.put(x, l);
            }
        }
        
        for (Integer key : map.keySet()) {
            
            
            for (int i = 0; i < map.get(key).size(); i++) {
                result.append(key + " " + map.get(key).get(i) + "\n");
            }
        }
        System.out.println(result);
    }
}

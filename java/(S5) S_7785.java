import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        
        //List<String> list = new ArrayList<>();
        Map<String, Boolean> list = new TreeMap<>(Comparator.reverseOrder());
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            String name = st.nextToken();
            String action = st.nextToken();

            if (action.equals("enter")) list.put(name, true);
            else list.remove(name);
        }

        for (String name : list.keySet()) {
            sb.append(name + "\n");
        }

        System.out.println(sb.toString());
    }
}
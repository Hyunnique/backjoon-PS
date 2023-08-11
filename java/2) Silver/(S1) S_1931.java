import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
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

        Map<Integer, List<Integer>> list = new TreeMap<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            
            if (list.containsKey(end)) {
                list.get(end).add(start);
            } else {
                List<Integer> t = new ArrayList<>();
                t.add(start);
                list.put(end, t);
            }
        }

        for (List<Integer> l : list.values()) {
            l.sort(Comparator.naturalOrder());
        }

        int time = 0;
        int cnt = 0;

        for (int end : list.keySet()) {
            for (int start : list.get(end)) {
                if (start < time) continue;
                else {
                    cnt++;
                    time = end;
                }
            }
        }

        System.out.println(cnt);
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;

public class s_s3_20291 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		
		HashMap<String, Integer> dict = new HashMap<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			st.nextToken(".");
			String f = st.nextToken(".");
			
			if (dict.containsKey(f)) {
				dict.put(f, dict.get(f) + 1);
			} else {
				dict.put(f, 1);
			}
		}
		
		TreeMap<String, Integer> sorted = new TreeMap<>();
		sorted.putAll(dict);
		
		for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}

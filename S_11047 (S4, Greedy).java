import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class s_s4_Greedy_11047 {
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[] coins = new int[N];
		int cursor = N - 1;
		int cnt = 0;
		
		for (int i = 0; i < N; i++) {
			coins[i] = Integer.parseInt(br.readLine());
		}
		
		while (K > 0) {
			if (cursor < 0) break;
			if (coins[cursor] <= K) {
				cnt++;
				K -= coins[cursor];
			} else {
				cursor--;
			}
		}
		
		System.out.println(cnt);
	}
}

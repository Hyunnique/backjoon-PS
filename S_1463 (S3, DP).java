import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class s_s3_DP_1463 {
	static int[] mem = new int[1000001];
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		System.out.println(dp(T));
	}
	
	public static int dp(int n) {
		int min = Integer.MAX_VALUE;
		
		if (n == 1) return 0;
		if (mem[n] != 0) return mem[n];
		
		if (n % 3 == 0 && n / 3 >= 1) min = dp(n/3) + 1;
		if (n % 2 == 0 && n / 2 >= 1) min = Math.min(min, dp(n/2) + 1);
		min = Math.min(min, dp(n-1) + 1);
		
		mem[n] = min;
		return min;
	}
}

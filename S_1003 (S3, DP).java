import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;

public class s_s3_DP_1003 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			
			int zero = 1, one = 1;
			if (N == 0) System.out.println("1 0");
			else if (N == 1) System.out.println("0 1");
			else if (N == 2) System.out.println("1 1");
			else {
				for (int s = 3; s <= N; s++) {
					int _t = zero + one;
					zero = one;
					one = _t;
				}
				System.out.println(zero + " " + one);
			}
		}
	}
}

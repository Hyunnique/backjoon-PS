import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class s_16114 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int X = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		
		if (N % 2 != 0 && N != 1) System.out.println("ERROR");
		else if (N == 1) {
			if (X < 0) System.out.println("INFINITE");
			else System.out.println(0);
		}
		else if (N == 0 && X <= 0) System.out.println(0);
		else if (N == 0) System.out.println("INFINITE");
		else {
			
			int cnt = 0;
			while (0 < (X - (int)(N / 2))) {
				X -= (int)(N / 2);
				cnt++;
			}
			
			System.out.println(cnt);
		}
	}
}

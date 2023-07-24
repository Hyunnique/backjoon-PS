import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class s_s2_BFS_1012 {
	
	static int[][] dv = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static int N, M;
	static int[][] arr;
	static int result = 0;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		
		int test_case = Integer.parseInt(br.readLine());
		
		for (int _t = 0; _t < test_case; _t++) {
			result = 0;
			StringTokenizer st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			
			arr = new int[N][M];
			
			int K = Integer.parseInt(st.nextToken());
			
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int X = Integer.parseInt(st.nextToken());
				int Y = Integer.parseInt(st.nextToken());
				
				arr[Y][X] = 1;
			}
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (arr[i][j] == 1) {
						result++;
						bfs(i, j);
					}
				}
			}
			System.out.println(result);
		}
	}
	
	public static void bfs(int row, int column) {
		Queue<int[]> q = new LinkedList<>();
		
		q.add(new int[] { row, column });
		arr[row][column] = 0;
		
		int curR, curC;
		
		while (!q.isEmpty()) {
			int[] pos = q.poll();
			
			for (int[] v : dv) {
				curR = pos[0] + v[0];
				curC = pos[1] + v[1];
				
				if (curR < 0 || curR >= N || curC < 0 || curC >= M) continue;
				if (arr[curR][curC] == 1) {
					arr[curR][curC] = 0;
					q.add(new int[] { curR, curC });
				}
			}
		}
	}
}

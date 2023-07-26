import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class s_s2_BFS_21736 {
	
	static int[][] dv = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static int N, M;
	static int[][] arr;
	static int result = 0;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N][M];
		
		boolean[][] visited = new boolean[N][M];
		Queue<int[]> q = new LinkedList<>();
		
		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			
			for (int j = 0; j < s.length(); j++) {
				switch (s.charAt(j)) {
				case 'O':
					arr[i][j] = 0;
					break;
				case 'X':
					arr[i][j] = -1;
					break;
				case 'P':
					arr[i][j] = 1;
					break;
				default:
					q.add(new int[] { i, j });
				}
			}
			
			
		}
		
		bfs(visited, q);
		if (result != 0) System.out.println(result);
		else System.out.println("TT");
	}
	
	public static void bfs(boolean[][] visited, Queue<int[]> q) {
		int curR, curC;
		
		while (!q.isEmpty()) {
			int[] pos = q.poll();
			
			for (int[] v : dv) {
				curR = pos[0] + v[0];
				curC = pos[1] + v[1];
				
				if (curR < 0 || curR >= N || curC < 0 || curC >= M || visited[curR][curC]) continue;
				
				visited[curR][curC] = true;
				if (arr[curR][curC] == 1) result++;
				if (arr[curR][curC] != -1) q.add(new int[] { curR, curC });
			}
		}
	}
}

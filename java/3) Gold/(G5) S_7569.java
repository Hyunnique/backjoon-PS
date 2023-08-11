import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

public class s_g5_BFS_7569 {
	static int[][] dv = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // R, C
	static int[][] tomato;
	static int C, R;
	static int tomatoLeft = 0;
	static int days = 0;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		C = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		tomato = new int[R][C];
		boolean[][] visited = new boolean[R][C];
		Queue<int[]> q = new LinkedList<>();
		
		for (int row = 0; row < R; row++) {
			st = new StringTokenizer(br.readLine());
			
			for (int column = 0; column < C; column++) {
				tomato[row][column] = Integer.parseInt(st.nextToken());
				if (tomato[row][column] == 1) {
					q.add(new int[] { row, column, 0 });
					visited[row][column] = true;
				}else if (tomato[row][column] == 0) tomatoLeft++;
			}
		}
		
		if (tomatoLeft == 0) {
			System.out.println(0);
			return;
		}
		
		dfs(visited, q);
		
		if (tomatoLeft != 0) {
			System.out.println(-1);
			return;
		}
		
		System.out.println(days);
	}
	
	public static void dfs(boolean[][] visited, Queue<int[]> q) {
		int curR, curC;
		
		while (!q.isEmpty()) {
			int[] pos = q.poll();
			
			for (int[] v : dv) {
				curR = pos[0] + v[0];
				curC = pos[1] + v[1];
				
				if (curR < 0 || curR >= R || curC < 0 || curC >= C || tomato[curR][curC] != 0 || visited[curR][curC]) {
					continue;
				}
				
				tomato[curR][curC] = 1;
				visited[curR][curC] = true;
				tomatoLeft--;
				days = Math.max(days, pos[2] + 1);
				q.add(new int[] { curR, curC, pos[2] + 1 });
			}
		}
	}
}

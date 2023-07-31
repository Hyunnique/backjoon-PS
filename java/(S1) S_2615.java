import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int size = 19;
	
	public static void main(String[] args) throws Exception {
		//////////////////////////////////////////////////////////////
		// 테스트 후 아래 파일 입력을 표준입력으로 처리하는 문장은 주석 처리해주세요!!!! ( System.setIn처리 코드 )
		//////////////////////////////////////////////////////////////
		//System.setIn(new FileInputStream("Test5.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// row, column
		// column이 -1로 가거나 row만 -1로 가는 경우는 탐색 순서로 인해 없음
		int[][] vec = { { -1, 1 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		
		int[][] arr = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j < size; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int winColor = 0;
		int winRow = -1, winColumn = -1;
		
		// row column 뒤집어주면 왼쪽부터 -> 위에서부터 탐색 순서 정렬 가능
		for (int column = 0; column < size; column++) {
			if (winColor != 0) break;
			for (int row = 0; row < size; row++) {
				if (winColor != 0) break;
				if (arr[row][column] != 0) {					
					for (int[] dv : vec) {
						// 줄이 바운더리 안에 들어갈 수 없으면 스킵
						if (!isIn(row + (dv[0] * 4), column + (dv[1] * 4))) continue;
						
						boolean available = true;
						// 위에서 체크해서 IndexOutOfRange는 발생하지 않을 것
						for (int i = 1; i < 5; i++) {
							if (!(arr[row][column] == arr[row + (dv[0] * i)][column + (dv[1] * i)])) {
								available = false;
								break;
							}
						}
						
						if (available) {
							// 전이나 후에 돌이 놓여있으면 그건 안됨
							if ((isIn(row + (dv[0] * -1), column + (dv[1] * -1)) && arr[row + (dv[0] * -1)][column + (dv[1] * -1)] == arr[row][column]) ||
									(isIn(row + (dv[0] * 5), column + (dv[1] * 5)) && arr[row + (dv[0] * 5)][column + (dv[1] * 5)] == arr[row][column])) {
								available = false;
							}
						}
						
						if (available) {
							winColor = arr[row][column];
							winRow = row;
							winColumn = column;
							break;
						}
					}
				}
			}
		}
		
		System.out.println(winColor);
		if (winColor != 0) System.out.println((winRow + 1) + " " + (winColumn + 1));
	}
	
	static boolean isIn(int row, int column) {
		return row >= 0 && row < size && column >= 0 && column < size;
	}
}
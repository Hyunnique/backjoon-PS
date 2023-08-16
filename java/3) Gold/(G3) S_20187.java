import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * 
 * 1 <= K <= 8 (손수건을 가로, 세로 각각 접는 횟수)
 * 0 <= H <= 3 (구멍을 뚫는 위치)
 * 
 */

class Square { // 각 격자를 저장할 Square 클래스
	int posRow, posColumn, hole; // 격자의 위치 (row, column) 와 구멍의 위치 (0~3)
	
	Square(int posRow, int posColumn, int hole) { // 생성자
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.hole = hole;
	}
}

public class Main {
	public static void main(String[] args) throws IOException {
		// 입력을 받을 BufferedReader 및 출력을 담을 StringBuilder 객체 생성
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int K = Integer.parseInt(br.readLine()); // 격자의 크기 (2^K) 를 위한 K 받기
		
		Stack<Character> cmd = new Stack<>(); // 접는 순서의 역순으로 펼치면서 구멍을 늘려나갈 것이기 때문에 스택 사용
		
		// 각 격자의 구멍 위치 및 격자의 위치에 따라서 위치를 옮겨주거나
		// 새로운 격자를 생성하고 구멍을 만들 것이기 때문에 구현의 편리함을 위한 큐 사용
		Queue<Square> squares = new ArrayDeque<>();
		
		int mapSize = (int) Math.pow(2, K); // 2^K를 계산해서 mapSize에 저장
		int[][] arr = new int[mapSize][mapSize]; // mapSize를 이용해서 손수건 공간 생성
		
		// 접는 명령어를 잘라줄 StringTokenizer
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 명령어를 하나씩 받아서 스택에 저장
		for (int i = 0; i < K * 2; i++) {
			cmd.push(st.nextToken().charAt(0)); // 명령어는 한글자이기 때문에 charAt을 사용해서 character 배열에 저장
		}
		
		// 최초 격자를 생성해서 큐에 저장
		squares.offer(new Square(0, 0, Integer.parseInt(br.readLine())));
		
		// 진행 도중의 격자 크기를 저장할 size 변수들
		int sizeRow = 1, sizeColumn = 1;
		
		// 명령어를 모두 확인할 때 까지 반복
		while (!cmd.isEmpty()) {
			char c = cmd.pop(); // 제일 뒤의 명령어 순으로 pop
			int squareCount = squares.size(); // 반복문 도중의 size 변동에 영향을 받지 않게끔 미리 size 저장
			
			Square s, n; // switch case 안에서 사용할 Square 객체를 담아둘 변수
			int holePos; // switch case 안에서 사용할 구멍의 위치를 계산할 변수
			
			// 
			switch (c) { // 명령어가
			case 'D': // D이면
				for (int i = 0; i < squareCount; i++) { // squareCount만큼 반복
					s = squares.poll(); // squares 맨 앞에서 꺼내서
					n = new Square(sizeRow - s.posRow - 1, s.posColumn, (s.hole + 2) % 4); // 연산에 따른 새로운 격자를 생성
					squares.offer(n); // 큐에 저장
					s.posRow = sizeRow + s.posRow; // 기존 격자의 위치 변경
					squares.offer(s); // 큐에 저장
				}
				
				sizeRow *= 2; // Row 크기를 2배 해줌
				break; // switch case 종료
			case 'U': // U이면
				sizeRow *= 2; // Row 크기를 미리 2배 해줌
				
				for (int i = 0; i < squareCount; i++) { // squareCount만큼 반복
					s = squares.poll(); // squares 맨 앞에서 꺼내서
					n = new Square(sizeRow - s.posRow - 1, s.posColumn, (s.hole + 2) % 4); // 연산에 따른 새로운 격자를 생성
					squares.offer(n); // 큐에 저장
					squares.offer(s); // 기존 격자는 변경 없이 큐에 저장
				}
				break; // switch case 종료
			case 'R': // R이면
				for (int i = 0; i < squareCount; i++) { // squareCount만큼 반복
					s = squares.poll(); // squares 맨 앞에서 꺼내서
					
					holePos = s.hole; // 새로운 격자의 구멍 위치 계산
					if (holePos == 0) holePos = 1; // 새로운 격자의 구멍 위치 계산
					else if (holePos == 1) holePos = 0; // 새로운 격자의 구멍 위치 계산
					else if (holePos == 2) holePos = 3; // 새로운 격자의 구멍 위치 계산
					else holePos = 2; // 새로운 격자의 구멍 위치 계산
					
					n = new Square(s.posRow, sizeColumn - s.posColumn - 1, holePos); // 연산에 따른 새로운 격자를 생성
					squares.offer(n); // 큐에 저장
					s.posColumn = sizeColumn + s.posColumn; // 기존 격자의 위치 변경
					squares.offer(s); // 큐에 저장
				}
				
				sizeColumn *= 2; // Column 크기를 2배 해줌
				break; // switch case 종료
			case 'L': // L이면
				sizeColumn *= 2; // 미리 Column 크기를 2배 해줌
				
				for (int i = 0; i < squareCount; i++) { // squareCount만큼 반복
					s = squares.poll(); // squares 맨 앞에서 꺼내서
					
					holePos = s.hole; // 새로운 격자의 구멍 위치 계산
					if (holePos == 0) holePos = 1; // 새로운 격자의 구멍 위치 계산
					else if (holePos == 1) holePos = 0; // 새로운 격자의 구멍 위치 계산
					else if (holePos == 2) holePos = 3; // 새로운 격자의 구멍 위치 계산
					else holePos = 2; // 새로운 격자의 구멍 위치 계산
					
					n = new Square(s.posRow, sizeColumn - s.posColumn - 1, holePos); // 연산에 따른 새로운 격자를 생성
					squares.offer(n); // 큐에 저장
					squares.offer(s); // 기존 격자는 위치 변경 없이 큐에 저장
				}
				break; // switch case 종료
			}
		}
		
		while (!squares.isEmpty()) { // 큐를 모두 돌면서
			Square n = squares.poll(); // 꺼내서
			arr[n.posRow][n.posColumn] = n.hole; // 손수건 배열에 하나씩 반영
		}
		
		for (int row = 0; row < mapSize; row++) { // 이중 for문으로 배열을 탐색하면서
			for (int column = 0; column < mapSize; column++) {
				sb.append(arr[row][column]).append(" "); // 결과 출력
			}
			sb.append("\n"); // 줄바꿈
		}
		
		// 출력
		System.out.println(sb.toString());
	}
}

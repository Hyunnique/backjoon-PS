import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    // 입력과 출력을 담당할 static 변수들
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer tokens;
    static int[][] movementDelta = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } }; // 시작지점에서 사용할 기본 이동 델타
    static Map<Character, int[]> movementMap = new HashMap<>(); // 각 블럭마다 이동할수 있는 방향을 저장할 맵
    static int R, C; // row, column 값을 저장할 static 변수
    static final int Direction_UP = 0, Direction_LEFT = 1, Direction_DOWN = 2, Direction_RIGHT = 3; // 쉽게 구현하기 위한 상수 선언

    public static void main(String[] args) throws Exception {

        // 각 블럭별로 이동할 수 있는 방향을 담아둠 (0, 1, 2, 3 각 인덱스 별로 해당 방향 Direction을 가지고 왔을 때 이동해야 하는 방향)
        movementMap.put('M', new int[] { -1, -1, -1, -1 }); // 시작점은 특별해서 인덱스를 신경쓰지 않고 탐색
        movementMap.put('Z', new int[] { -1, -1, -1, -1 }); // 도착점은 모든 방향에서 받아줌 (더 탐색하지 않을것이므로 값은 의미 없음)
        movementMap.put('|', new int[] { Direction_UP, -1, Direction_DOWN, -1 }); // 위아래만 가능
        movementMap.put('-', new int[] { -1, Direction_LEFT, -1, Direction_RIGHT }); // 왼쪽 오른쪽만 가능
        movementMap.put('+', new int[] { Direction_UP, Direction_LEFT, Direction_DOWN, Direction_RIGHT }); // 모두 가능, 한번 지나면 안지난 직선의 블럭으로 교체해주기
        movementMap.put('1', new int[] { Direction_RIGHT, Direction_DOWN, -1, -1 }); // from down, from right
        movementMap.put('2', new int[] { -1, Direction_UP, Direction_RIGHT, -1 }); // from up, from right
        movementMap.put('3', new int[] { -1, -1, Direction_LEFT, Direction_UP }); // from up, from left
        movementMap.put('4', new int[] { Direction_LEFT, -1, -1, Direction_DOWN }); // from down, from left

        char[][] map; // 맵을 저장할 character 배열
        char[][] origin;

            tokens = new StringTokenizer(br.readLine()); // 입력 받기

            // 입력
            R = Integer.parseInt(tokens.nextToken());
            C = Integer.parseInt(tokens.nextToken());


            map = new char[R][C]; // 맵 초기화
            origin = new char[R][C];

            int curRow = 0, curColumn = 0; // 현재 커서를 저장할 변수
            int direction = 0; // 진행 방향을 저장할 변수

            for (int row = 0; row < R; row++) { // 이중 for문으로 맵 입력받기
                String line = br.readLine(); // 입력
                for (int column = 0; column < C; column++) { // 이중 for문으로 맵 입력받기
                    map[row][column] = line.charAt(column); // 입력
                    origin[row][column] = line.charAt(column);

                    if (line.charAt(column) == 'M') { // 만약 시작점이면
                        curRow = row; // 커서에 저장해준다
                        curColumn = column; // 커서에 저장해준다
                    }
                }
            }

            int moveRow, moveColumn; // 임시 커서를 저장할 변수
            char result = '0'; // 결과를 저장할 변수
            int resultRow = 0, resultColumn = 0; // 결과의 좌표를 저장할 변수

            while (map[curRow][curColumn] != 'Z') { // 도착점에 도달할 때 까지 탐색
                boolean hasMoved = false; // 이동을 성공했는지를 저장할 boolean 변수
                //System.out.println(curRow + "," + curColumn);
                if (map[curRow][curColumn] == 'M') { // 시작점에서는
                    // M은 특별해서 첫 방향을 잡아주자

                    for (int moveIndex = 0; moveIndex < 4; moveIndex++) { // 각 방향별로 탐색하면서
                        moveRow = curRow + movementDelta[moveIndex][0]; // 임시 커서를 옮겨주고
                        moveColumn = curColumn + movementDelta[moveIndex][1]; // 임시 커서를 옮겨주고

                        // M과 Z가 바로 붙어있는 경우는 없다고 가정 (지운 블럭이 존재해야 하고 M과 Z에 붙어있는 블럭은 무조건 1개이기 때문)

                        // 탐색한 방향이 맵을 벗어나지 않고, 빈 칸이 아니며, 해당 블럭이 현재 방향에서 갈 수 있을 경우
                        if (isIn(moveRow, moveColumn) && map[moveRow][moveColumn] != '.' && map[moveRow][moveColumn] != 'Z' && movementMap.get(map[moveRow][moveColumn])[moveIndex] != -1) {
                            hasMoved = true; // 이동했습니다
                            curRow = moveRow; // 현재 커서를 임시 커서의 위치로 옮겨줌
                            curColumn = moveColumn; // 현재 커서를 임시 커서의 위치로 옮겨줌
                            direction = movementMap.get(map[moveRow][moveColumn])[moveIndex]; // 진행방향을 바꿔줌

                            break;
                        }
                    }
                }
                else { // 시작점이 아니라면
                    moveRow = curRow + movementDelta[direction][0]; // 임시 커서를 진행 방향으로 옮겨준다
                    moveColumn = curColumn + movementDelta[direction][1]; // 임시 커서를 진행 방향으로 옮겨준다

                    // 탐색한 방향이 맵을 벗어나지 않고, 빈 칸이 아니며, 해당 블럭이 현재 방향에서 갈 수 있을 경우
                    if (isIn(moveRow, moveColumn) && map[moveRow][moveColumn] != '.' && movementMap.get(map[moveRow][moveColumn])[direction] != -1) {
                        hasMoved = true; // 이동했습니다
                        curRow = moveRow; // 현재 커서를 임시 커서의 위치로 이동
                        curColumn = moveColumn; // 현재 커서를 임시 커서의 위치로 이동
                        direction = movementMap.get(map[moveRow][moveColumn])[direction]; // 진행방향을 바꿔줌

                        if (map[moveRow][moveColumn] == '+') { // 만약 십자길이면
                            if (direction == Direction_UP || direction == Direction_DOWN) {
                                map[moveRow][moveColumn] = '-'; // 지나간 방향의 길은 없애고 일자길로 만들어줌
                            } else {
                                map[moveRow][moveColumn] = '|'; // 지나간 방향의 길은 없애고 일자길로 만들어줌
                            }
                        }
                    } else {
                        curRow = moveRow; // 갈 수 없으면 현재 커서를 임시 커서의 위치로 이동시키고 아래 !hasMoved로 이동
                        curColumn = moveColumn;
                    }
                }

                if (!hasMoved) { // 움직이지 못했는데 아직 빈 블럭을 발견하지 못했다면
                    // 움직이지 못했다면 탐색할 수 있는 방향에 있는 블럭이 지워진 블럭이다
                    //System.out.println("!!" + curRow + "," + curColumn);
                    for (char key : movementMap.keySet()) { // 모든 블럭에 대해 탐색하면서
                        if (key == 'M' || key == 'Z') continue; // 시작점과 도착점은 지워질 수 없으므로 스킵
                        if (movementMap.get(key)[direction] == -1) continue; // 현재 방향에서 갈 수 없는 블럭이면 스킵

                        boolean isMatched = true;

                        for (int i = 0; i < 4; i++) {
                            moveRow = curRow + movementDelta[i][0];
                            moveColumn = curColumn + movementDelta[i][1];

                            if (movementMap.get(key)[(i + 2) % 4] != -1) { // 위, 왼, 아래, 오른 순으로 해당 방향이 열려 있을 경우 (위부터 확인)
                                if (!isIn(moveRow, moveColumn) || origin[moveRow][moveColumn] == '.' || movementMap.get(origin[moveRow][moveColumn])[i] == -1) {
                                    isMatched = false;
                                    break;
                                }
                            }

                            if (movementMap.get(key)[(i + 2) % 4] == -1) { // 닫혀있는 곳에 대해서는
                                if (!isIn(moveRow, moveColumn)) continue; // 벗어나도 상관없음
                                if (origin[moveRow][moveColumn] != '.' && movementMap.get(origin[moveRow][moveColumn])[i] != -1) {
                                    isMatched = false;
                                    break;
                                }
                            }
                        }

                        if (isMatched) {
                            result = key;
                            resultRow = curRow + 1;
                            resultColumn = curColumn + 1;

                            map[curRow][curColumn] = key;
                            direction = movementMap.get(map[curRow][curColumn])[direction];

                            //System.out.println(result);
                            break;
                        }
                        /*
                        // 만약 갈 수 있다면 이은 블럭에서도 받아줄 수 있는지 확인
                        moveRow = curRow + movementDelta[movementMap.get(key)[direction]][0]; // 임시 커서를 이어줄 블럭의 위치로 이동
                        moveColumn = curColumn + movementDelta[movementMap.get(key)[direction]][1]; // 임시 커서를 이어줄 블럭의 위치로 이동

                        // 만약 탐색한 방향이 맵을 벗어나지 않고, 빈칸이 아니며, 해당 블럭이 현재 예상 블럭과 이어질 경우
                        if (isIn(moveRow, moveColumn) && map[moveRow][moveColumn] != '.' && movementMap.get(map[moveRow][moveColumn])[movementMap.get(key)[direction]] != -1) {
                            result = key; // 정답은 이 블럭이다
                            resultRow = curRow + 1; // 정답 칸을 출력할 변수에 저장
                            resultColumn = curColumn + 1; // 정답 칸을 출력할 변수에 저장

                            map[curRow][curColumn] = key; // 예상한 블럭으로 맵을 채워줌
                            direction = movementMap.get(map[curRow][curColumn])[direction]; // 진행방향을 바꿔줌
                            resultVisible = true; // 빈 블럭을 찾았어!

                            System.out.println(result);
                            //break;
                        }
                        */
                        // 이후에 계속 탐색을 이어간다
                    }
                }
            }

            // 출력
            sb.append(resultRow).append(" ").append(resultColumn).append(" ").append(result);

        System.out.println(sb.toString());
    }

    static boolean isIn(int row, int column) { // row, column이 맵을 벗어나지 않는지 확인하는 메소드
        return row >= 0 && row < R && column >= 0 && column < C;
    }
}

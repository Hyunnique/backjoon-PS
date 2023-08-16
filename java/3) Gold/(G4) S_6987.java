import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 16.
 * @see BoJ 6987 (G4)
 * @category Backtracking
 * @note 월드컵
 * 
 * 6개 팀이 경기하는 모든 경우의 수 (A-B, A-C, A-D, ... , E-F)
 * 를 하나씩 돌면서 승/무/패 모두 확인해서 재귀 돌기
 * 
 * 변수로 주어진 팀 간에 승/무/패 어떠한 경기도 수행할 수 없으면 알아서 return
 * E-F까지 모두 탐색한 후에 승/무/패 하나라도 경기가 남아있으면 또한 return
 * 위의 조건을 모두 넘어간다면 가능한 조합임
 * 
 */

class BufferedScanner {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = null;

    public String next() throws IOException {
        if (st == null || !hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }

        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public char nextChar() throws IOException {
        return next().charAt(0);
    }

    public String readLine() throws IOException {
        String str = br.readLine();
        st = new StringTokenizer(str);

        return str;
    }

    public boolean hasMoreTokens() {
        return st.hasMoreTokens();
    }
}

class Result {
    int index, win, draw, lose;

    Result(int index, int win, int draw, int lose) {
        this.index = index;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    public boolean done() {
        return win == 0 && draw == 0 && lose == 0;
    }
}

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static boolean caseResult = false;
    public static void main(String[] args) throws Exception {

        List<List<Result>> results = new ArrayList<>();

        for (int i = 0; i < 4; i++) results.add(new ArrayList<>());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                results.get(i).add(new Result(i, sc.nextInt(), sc.nextInt(), sc.nextInt()));
            }
        }

        for (int i = 0; i < 4; i++) {
            //System.out.println("---- case ----\n");
            caseResult = false;
            game(results.get(i), 0, 1);
            if (caseResult) sb.append("1 ");
            else sb.append("0 ");
            //break;
        }
        

        System.out.println(sb.toString());
    }

    static void game(List<Result> results, int gameFrom, int gameTo) {
        if (caseResult) return; // 하나라도 가능한 조합이 나왔으면 다른 조합은 수행해 볼 필요 없음

        if (gameFrom == 5) { // 만약 E-F 끝까지 돌았으면
            boolean realEnd = true;
            for (int i = 0; i < 6; i++) { // 경기가 남았는지 확인해봐서 안 남았으면 진짜 가능
                if (!results.get(i).done()) realEnd = false;
            }

            if (realEnd) caseResult = true;
            return;
        } else if (gameTo == 6) { // gameTo가 끝까지 돌았으면 gameFrom을 증가시켜주고 확인
            gameFrom++;
            gameTo = gameFrom + 1;
            game(results, gameFrom, gameTo);
            return;
        }

        //System.out.println(results.toString() + ", " + gameFrom + ", " + gameTo);

        Result r1 = results.get(gameFrom);
        Result r2 = results.get(gameTo);

        // Win
        if (r1.win > 0 && r2.lose > 0) {
            r1.win--;
            r2.lose--;
            game(results, gameFrom, gameTo + 1);
            r1.win++;
            r2.lose++;
        }
        
        // Draw
        if (r1.draw > 0 && r2.draw > 0) {
            r1.draw--;
            r2.draw--;
            game(results, gameFrom, gameTo + 1);
            r1.draw++;
            r2.draw++;
        }

        // Lose
        if (r1.lose > 0 && r2.win > 0) {
            r1.lose--;
            r2.win--;
            game(results, gameFrom, gameTo + 1);
            r1.lose++;
            r2.win++;
        }
    }
}

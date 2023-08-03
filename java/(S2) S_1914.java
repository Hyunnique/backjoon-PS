import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static List<Stack<Integer>> tower = new ArrayList<>();
    static int N;
    static int moveCount = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < 3; i++) {
            tower.add(new Stack<>());
        }

        for (int i = 0; i < N; i++) {
            tower.get(0).add(1);
        }

        if (N <= 20) {
            hanoi(0, 2, 1, N);

            System.out.println(moveCount);
            System.out.println(sb.toString());
        } else {
            BigInteger r = new BigInteger("1");
            for (int i = 0; i < N; i++) r = r.multiply(new BigInteger("2"));
            r = r.subtract(new BigInteger("1"));
            System.out.println(r);
        }
    }

    static void hanoi(int start, int end, int temp, int cnt) {
        if (cnt == 1) {
            moveCount++;
            tower.get(end).add(tower.get(start).pop());
            if (N <= 20) sb.append(start + 1).append(" ").append(end + 1).append("\n");
            return;
        }
        hanoi(start, temp, end, cnt - 1);
        hanoi(start, end, temp, 1);
        hanoi(temp, end, start, cnt - 1);
    }
}
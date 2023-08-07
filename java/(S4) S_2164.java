import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author 김대현
 * @since 2023. 08. 04.
 * @note
 * 
 * 제일 위에서 아래로 카드를 보내야 하므로
 * 가장 수월하게 작성할 수 있는 자료구조는 Queue
 * 카드를 버리는 것과 큐에서 자료를 빼거나 삽입하는데
 * 모두 상수 시간이 걸리기 때문에
 * O(N) 의 시간복잡도를 가지게 된다
 * 그래서 다 직접 해봐도 된다
 */

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(br.readLine());

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            queue.offer(i);
        }

        while (queue.size() > 1) {
            queue.poll();
            queue.offer(queue.poll());
        }

        System.out.println(queue.poll());
    }
}

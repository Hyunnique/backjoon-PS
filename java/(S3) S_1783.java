import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken()); // row
        int M = Integer.parseInt(st.nextToken()); // column

        if (N == 1) {
            // 어떠한 방법으로도 이동 불가
            System.out.println(1);
        } else if (N == 2) {
            // 위, 아래 1칸짜리 이동 방식으로 왔다갔다만 가능
            // 4번 이상 (가로 길이 7) 이 되면 이동방식 한번씩 사용 조건때문에 최대 3번까지만 이동 가능
            System.out.println(Math.min(4, (M + 1) / 2));
        } else if (M < 7) {
            // 이동방식을 다 사용하는데 가로 7칸이 소모됨
            // 그 가로칸을 만족하지 않는다면 4번 이상 이동이 불가능하므로 최대 3번까지만 이동 가능
            System.out.println(Math.min(4, M));
        } else {
            // 그게 아니면 이동방식 먼저 한번씩 다 사용하고 출발하면 column = 6에서 시작
            // 이후에는 오른쪽으로 한칸 가는 이동방식만 선택하면 되므로 이동방식을 모두 사용하면서 가로 7을 소모해 5칸 밟고 이후에는 1만 소모해서 1칸 밟을 수 있음
            System.out.println(M - 2);
        }
    }
}
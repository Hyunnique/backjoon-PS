import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
 * BoJ 1946
 * 
 * 문제)
 * N명의 지원자
 * 서류 / 면접 두 점수 모두 어떤 지원자에게 떨어질 경우, 선발하지 않음
 * 
 * N <= 100,000
 * TreeMap에 (서류, 면접) -> (key, value) 의 형태로 어느 한 쪽을 정렬하고 체크한다고 해도,
 * 연산을 절반정도 줄일 수 있겠지만 여전히 O(N^2)의 시간복잡도를 가져 완탐은 불가능할 것 같음
 * 
 * 1 4
 * 2 3
 * 3 2
 * 4 1
 * 5 5
 * 
 * 조금 더 고민을 해보니, 위와 같이 한쪽 값으로 정렬을 해두고 위에서부터 탐색하면
 * 아래에 있는 데이터들은 이미 서류 점수로는 밀리기 때문에 위에 있는 모든 데이터들에 비해서 면접 점수가 높아야 함
 * 따라서 key로 정렬해두고 위에서부터 탐색하면서 순위 숫자의 min을 저장해두고 이보다 값이 높으면 무조건 떨어짐
 * 
 */
public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 0; test_case < T; test_case++) {
            N = Integer.parseInt(br.readLine());

            TreeMap<Integer, Integer> map = new TreeMap<>();

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                map.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            int cnt = 0, min = 0;
            for (int key : map.keySet()) {
                if (cnt == 0) {
                    // 1번은 무조건 통과
                    min = map.get(key);
                    cnt++;
                }

                if (map.get(key) < min) {
                    cnt++;
                    min = map.get(key);
                }
            }

            sb.append(cnt).append("\n");
        }

        System.out.println(sb.toString());
    }

}
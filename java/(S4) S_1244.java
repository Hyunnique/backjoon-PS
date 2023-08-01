import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author 김대현
 * @since 2023. 08. 01.
 * @see https://www.acmicpc.net/problem/1244
 * @note 0번부터 순차탐색
 */

public class Main {
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());

        boolean[] switches = new boolean[N];
        
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            switches[i] = (Integer.parseInt(st.nextToken()) == 1);
        }

        int P = Integer.parseInt(br.readLine());

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int gender = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());

            if (gender == 1) {
                int cursor = num - 1;

                while (cursor < N) {
                    switches[cursor] = !switches[cursor];
                    cursor += num;
                }
            } else {
                int cursor = num - 1, cnt = 1;
                
                while (true) {
                    if (isIn(cursor - cnt) && isIn(cursor + cnt) && switches[cursor - cnt] == switches[cursor + cnt]) {
                        cnt++;
                    } else {
                        cnt--;
                        break;
                    }
                }

                for (int k = cursor - cnt; k <= cursor + cnt; k++) {
                    switches[k] = !switches[k];
                }
            }
        }

        for (int i = 0; i < N; i++) {
            sb.append((switches[i] ? 1 : 0)).append(" ");
            if (i % 20 == 19) sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    static boolean isIn(int index) {
        return index >= 0 && index < N;
    }
}
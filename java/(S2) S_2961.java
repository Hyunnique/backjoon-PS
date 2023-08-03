import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author 김대현
 * @since 2023. 08. 03.
 * @note 재료의 개수가 10개 이하이므로 모든 경우의 수를 탐색할 수 있음
 * (시간복잡도 O(N * 2^N) = 10240)
 */

class Material {
    int s, b;

    public Material(int s, int b) {
        this.s = s;
        this.b = b;
    }
}

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    static Material[] mat;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(br.readLine());
        mat = new Material[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            mat[i] = new Material(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        System.out.println(cook());
    }

    static int cook() {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < (1 << N); i++) {
            int s = 1, b = 0;

            for (int j = 0; j < N; j++) {
                if ((i & (1 << j)) > 0) {
                    s *= mat[j].s;
                    b += mat[j].b;
                }
            }

            if (s != 1 || b != 0) min = Math.min(min, Math.abs(s - b));
        }

        return min;
    }
}

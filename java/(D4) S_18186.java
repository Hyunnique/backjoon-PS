import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        long B = Integer.parseInt(st.nextToken());
        long C = Integer.parseInt(st.nextToken());

        int[] arr = new int[N + 2];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        //BigInteger result = BigInteger.ZERO;
        long result = 0;
        for (int cycle = (B <= C ? 2 : 0); cycle < 3; cycle++) {
            for (int cur = 0; cur < N; cur++) {
                if (arr[cur] == 0) continue;
                else if (B <= C) result += B * arr[cur];
                else if (arr[cur + 2] > 0 && arr[cur + 1] > arr[cur + 2]) {
                    long x = Math.min(arr[cur], arr[cur + 1] - arr[cur + 2]);
                    result += x * (B + C);

                    arr[cur] -= x;
                    arr[cur + 1] -= x;

                    cur--;
                } else if (arr[cur + 1] != 0 && arr[cur + 2] != 0) {
                    long x = Math.min(Math.min(arr[cur], arr[cur + 1]), arr[cur + 2]);
                    result += (B + (2 * C)) * x;

                    arr[cur] -= x;
                    arr[cur + 1] -= x;
                    arr[cur + 2] -= x;
                } else if (arr[cur + 1] != 0) {
                    long x = Math.min(arr[cur], arr[cur + 1]);
                    result += (B + C) * x;

                    arr[cur] -= x;
                    arr[cur + 1] -= x;
                } else {
                    long x = arr[cur];
                    arr[cur] -= x;
                    result += B * x;
                }

                //System.out.println(Arrays.toString(arr));
            }
        }
        System.out.println(result);
    }
}

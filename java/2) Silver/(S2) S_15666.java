import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 11.
 * @see BoJ 15666 (S2)
 * @note N과 M (12)
 * 
 * N개의 수 중 M개의 수를 뽑아 만들 수 있는 모든 중복순열 출력하기
 * 
 */

class CustomReader {
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

public class Main {   
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static int[] numbers, dict;
    static boolean[] chosen;
    static int[] latest;
    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        M = cr.nextInt();

        dict = new int[N];
        chosen = new boolean[N];
        latest = new int[M];

        for (int i = 0; i < N; i++) {
            dict[i] = cr.nextInt();
        }

        Arrays.sort(dict);
        numbers = new int[M];
        makePermutation(0);

        System.out.println(sb.toString());
    }

    static void makePermutation(int idx) {
        if (idx == M) {
            boolean different = false;
            for (int i = 0; i < M; i++) {
                if (latest[i] != numbers[i]) {
                    different = true;
                    break;
                }
            }

            if (!different) return;

            for (int i = 0; i < M; i++) {
                latest[i] = numbers[i];
                sb.append(numbers[i]).append(" ");
            }
            sb.append("\n");
            return;
        }

        for (int i = 0; i < N; i++) {
            //if (chosen[i]) continue;
            if (idx != 0 && dict[i] < numbers[idx - 1]) continue;
            
            numbers[idx] = dict[i];
            //chosen[i] = true;
            makePermutation(idx + 1);
            //chosen[i] = false;

            int current = dict[i];
            while (i < N - 1 && current == dict[i + 1]) ++i;
        }
    }
}

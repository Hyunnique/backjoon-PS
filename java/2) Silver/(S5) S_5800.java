import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int K = Integer.parseInt(br.readLine());
        
        List<List<Integer>> list = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            list.add(new ArrayList<>());

            int N = Integer.parseInt(st.nextToken());

            for (int j = 0; j < N; j++) {
                list.get(i).add(Integer.parseInt(st.nextToken()));
            }

            list.get(i).sort(Comparator.reverseOrder());

            int max = -1, min = Integer.MAX_VALUE, largestGap = -1;
            for (int j = 0; j < N; j++) {
                int x = list.get(i).get(j);

                max = Math.max(max, x);
                min = Math.min(min, x);
                largestGap = Math.max(largestGap, (j == N - 1 ? -1 : x - list.get(i).get(j + 1)));
            }

            sb.append("Class " + (i + 1) + "\n");
            sb.append("Max " + max + ", Min " + min + ", Largest gap " + largestGap + "\n");
        }

        System.out.println(sb.toString());
    }
}
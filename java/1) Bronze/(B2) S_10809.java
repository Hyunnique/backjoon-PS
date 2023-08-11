import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[123]; // 97
        
        for (int i = 97; i < 123; i++) arr[i] = -1;

        String str = br.readLine();

        int cursor = 0;
        for (char s : str.toCharArray()) {
            if (arr[(int)s] == -1) arr[(int)s] = cursor;
            cursor++;
        }

        for (int i = 97; i < 123; i++) {
            sb.append(arr[i]).append(" ");
        }

        System.out.println(sb.toString());
    }
}

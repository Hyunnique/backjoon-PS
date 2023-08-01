import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder sb = new StringBuilder();
        
        String str = br.readLine();

        int[] arr = new int[9];

        for (int i = 0; i < str.length(); i++) {
            int x = str.charAt(i) - 48;
            if (x == 9) arr[6]++;
            else arr[x]++;
        }

        arr[6] = (int)(Math.ceil((double)arr[6] / 2));
        int max = 0;
        
        for (int i = 0; i < 9; i++) {
            max = Math.max(max, arr[i]);
        }

        System.out.println(max);
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());

        int left = 1, right = 1;
        int sum = 1, cnt = 0;
        while (true) {
            if (sum == N) {
                cnt++;
                sum += ++right;
            }
            else if (sum > N) {
                sum -= left++;
            }
            else if (sum < N) {
                sum += ++right;
            }

            if (right > N || left > N) break;
        }

        System.out.println(cnt);
    }
}
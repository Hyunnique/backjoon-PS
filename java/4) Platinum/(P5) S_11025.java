import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * @author 김대현
 * @since 2023. 08. 07.
 * @note
 * 
 * BoJ 11025 - 요세푸스 문제 3 (P5)
 * 
 * TL 1s, ML 16MB
 * 기존의 Queue를 사용하는 풀이는 메모리 제한으로 사용할 수 없음
 * boolean 배열을 사용해서 직접 순회탐색 하는 방법 또한 O(N^2), N <= 5,000,000으로
 * 사용할 수 없음

 */

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int r = 1;

        for (int i = 2; i <= N; i++)
            r = (r + K - 1) % i + 1;

        System.out.println(r);
    }
}

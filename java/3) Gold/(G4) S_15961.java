import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 24.
 * @see BoJ 15961 (G4)
 * @category ?
 * @note 회전 초밥
 *
 * 회전 초밥 테이블과 포인터를 큐로 만들어서
 * 포인터 큐 안에 몇개 들어있는지를 확인할 배열을 이용한
 * Two Pointer 방식으로 찾기
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        int N = sc.nextInt();
        int d = sc.nextInt();
        int discountLength = sc.nextInt();
        int coupon = sc.nextInt();

        int[] sushiPicked = new int[3001];
        int[] sushiTable = new int[N + discountLength - 1];

        int currentSum = 0, maxSum = 0;

        for (int i = 0; i < N + discountLength - 1; i++) {
            if (i < N) {
                sushiTable[i] = sc.nextInt();
                if (i < discountLength - 1) sushiTable[i + N] = sushiTable[i];
            }

            if (sushiPicked[sushiTable[i]] == 0) currentSum++;
            sushiPicked[sushiTable[i]]++;

            if (i >= discountLength) {
                if (sushiPicked[sushiTable[i - discountLength]] == 1) currentSum--;
                sushiPicked[sushiTable[i - discountLength]]--;
            }

            int additional = 0;
            if (sushiPicked[coupon] == 0) additional = 1;
            //System.out.println("head = " + sushiTable[i] + " / sum = " + (currentSum + additional));
            if (currentSum + additional > maxSum) maxSum = currentSum + additional;
        }

        //System.out.println(Arrays.toString(sushiTable));

        System.out.println(maxSum);
    }
}

class BufferedScanner {
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

    public long nextLong() throws IOException {
        return Long.parseLong(next());
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
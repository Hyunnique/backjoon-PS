import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author Hyunnique
 * @since 2023. 08. 11.
 * @see BoJ 16435 (S5)
 * @note 스네이크버드
 * 
 * 리스트 정렬해서 낮은것부터 먹으면 끝
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
    static int N;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        CustomReader cr = new CustomReader();

        N = cr.nextInt();
        int L = cr.nextInt();

        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            arr.add(cr.nextInt());
        }

        Collections.sort(arr);

        for (int s : arr) {
            if (s <= L) L++;
            else break;
        }
        
        System.out.println(L);
    }
}

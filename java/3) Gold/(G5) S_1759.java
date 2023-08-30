import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 22.
 * @see BoJ 1759 (G5)
 * @category Backtracking
 * @note 암호 만들기
 *
 * 백트래킹을 이용해서 규칙에 맞게 암호 만들기
 * 
 */

class Subchar implements Comparable<Subchar> {
    char value;
    int vowelInc, conInc;

    Subchar(char value) {
        this.value = value;

        if (value == 'a' || value == 'e' || value == 'i' || value == 'o' || value == 'u') {
            vowelInc = 1;
            conInc = 0;
        } else {
            vowelInc = 0;
            conInc = 1;
        }
    }

    @Override
    public int compareTo(Subchar o) {
        return Character.compare(value, o.value);
    }
}

public class Main {
    static int L, C;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    static Subchar[] src;

    public static void main(String[] args) throws Exception {
        
        L = sc.nextInt();
        C = sc.nextInt();

        src = new Subchar[C];

        for (int i = 0; i < C; i++) {
            src[i] = new Subchar(sc.nextChar());
        }

        Arrays.sort(src);
        makePassword(0, new char[L], 0, 0, 0);

        System.out.println(sb.toString());
    }

    static void makePassword(int nth, char[] chosen, int startIndex, int vowels, int cons) {
        if (nth == L) {
            if (vowels >= 1 && cons >= 2) {
                for (int i = 0; i < L; i++) {
                    sb.append(chosen[i]);
                }
                sb.append("\n");
            }
            return;
        }
        for (int i = startIndex; i < src.length; i++) {
            chosen[nth] = src[i].value;
            makePassword(nth + 1, chosen, i + 1, vowels + src[i].vowelInc, cons + src[i].conInc);
            chosen[nth] = '0';
        }
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
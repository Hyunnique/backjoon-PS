import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 24.
 * @see BoJ 17281 (G4)
 * @category Implement, Permutation
 * @note 야구
 *
 * 구현 완탐
 * 
 * 전후의 타자 순서에 따라 i번째 타자가 플레이하는 이닝이 예측 불가능하고
 * (앞에서 아웃을 당하는지 아닌지에 따라 i번째 타자의 플레이 또한 가변적임)
 * 이닝의 변화와 타자의 결과의 연관성이 없기 때문에
 * 
 * 그리디를 활용하거나 기존 이닝의 정보를 재사용하기는 어려움
 * -> 가지치기 없이 전부 확인해봐야 함
 * 
 * 타자는 9명밖에 없음.
 * 고정되는 4번 타자를 제외하고 NextPermutation을 이용한 순열을 생성해서
 * 모든 순열에 대해서 점수를 계산함
 */

class Player {
    int index;
    int[] roundResults;

    Player(int index, int roundCount) {
        this.index = index;
        this.roundResults = new int[roundCount];
    }
}

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static Player[] players;
    public static void main(String[] args) throws IOException {
        int N = sc.nextInt();

        // 4번타자는 고정
        Player staticPlayer = new Player(0, N);

        // 나머지 8명의 타자 배열
        players = new Player[8];

        for (int i = 0; i < 8; i++) players[i] = new Player(i + 1, N);

        // 입력을 받으면서 각 Player 클래스의 roundResults에 이닝 별 결과를 담아둠
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0) staticPlayer.roundResults[i] = sc.nextInt();
                else players[j - 1].roundResults[i] = sc.nextInt();
            }
        }

        int maxScore = 0;

        do {
            // 타순은 Queue를 이용하면서 회전하게 함
            Queue<Player> playerQueue = new ArrayDeque<>();
            int score = 0;

            // Queue에 선수들 추가
            for (int i = 0; i < 8; i++) {
                if (i == 3) playerQueue.offer(staticPlayer);
                playerQueue.offer(players[i]);
            }

            // N회 이닝 반복
            for (int i = 0; i < N; i++) {
                int out = 0;
                boolean[] base = new boolean[3];

                // 3아웃이 발생할 때 까지 무한 반복
                while (out != 3) {
                    // Queue의 맨 앞을 꺼내서 뒤로 보내고 해당 선수로 결과 확인
                    Player current = playerQueue.poll();
                    playerQueue.offer(current);

                    // 각 결과에 대해서 진루 및 득점처리
                    switch (current.roundResults[i]) {
                        case 0:
                            ++out;
                            break;
                        case 1:
                            score += run(base, 1);
                            break;
                        case 2:
                            score += run(base, 2);
                            break;
                        case 3:
                            score += run(base, 3);
                            break;
                        case 4:
                            score += run(base, 4);
                            break;
                    }
                }
            }

            maxScore = Math.max(maxScore, score);
        } while (nextPermutation());

        System.out.println(maxScore);
    }

    static int run(boolean[] base, int count) {
        int addictiveScore = 0;

        // 3루부터 진루시켜주고 범위를 벗어나면 득점
        for (int b = base.length - 1; b >= 0; b--) {
            if (base[b]) {
                if (b + count >= base.length) ++addictiveScore;
                else base[b + count] = true;

                base[b] = false;
            }
        }

        // 안타를 친 주자도 가야함
        if (count - 1 < base.length) base[count - 1] = true;
        else ++addictiveScore;

        return addictiveScore;
    }

    static boolean nextPermutation() { // NP
        int lastPeak = players.length - 1;
        while (lastPeak > 0 && players[lastPeak - 1].index >= players[lastPeak].index) --lastPeak;
        if (lastPeak == 0) return false;

        int nextPeak = players.length - 1;
        while (players[lastPeak - 1].index >= players[nextPeak].index) --nextPeak;

        swap(nextPeak, lastPeak - 1);

        for (int left = lastPeak, right = players.length - 1; left < right; left++, right--) {
            swap(left, right);
        }

        return true;
    }

    static void swap(int a, int b) {
        Player temp = players[a];
        players[a] = players[b];
        players[b] = temp;
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
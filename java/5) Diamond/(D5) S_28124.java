import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 01.
 * @see BoJ 28124 (D5)
 * @category Implement
 * @note 할래 갈래
 *
 *
 *
 */

public class Main {
    static BufferedScanner sc = new BufferedScanner();
    static StringBuilder sb = new StringBuilder();
    static int maxPlayerCount, initialHandCount, pictureCount, maxHalleCount, maxGalleCount;
    static Card[] cards;
    static boolean debug = false;

    static class Card {
        long target, sum, round;

        Card(long target) {
            this.target = target;

            sum = 0;
            round = -1;
        }
    }

    static class Player {
        int round, handCount, halleIndex, halleCount;
        int prev, next;

        Player() {
            this.round = 0;
            this.handCount = initialHandCount;
            this.halleIndex = 0;
            this.halleCount = 0;
        }

        public boolean dead() {
            return handCount <= 0;
        }
    }

    static class Game {
        Player[] players;

        int round; // 현재 라운드
        int currentPlayer; // 현재 턴 플레이어 커서
        int remainPlayers, ghostPlayers; // 현재 남아있는 플레이어 수, 해당 라운드에 죽었으나 아직 제외되지 않은 플레이어 수
        int cardSum; // 할래가 몇 번 일어났는지 카운팅
        int matched; // 그림의 sum과 target이 맞는 횟수 카운팅

        Game() {
            players = new Player[maxPlayerCount + 1];

            for (int i = 1; i < maxPlayerCount + 1; i++) {
                players[i] = new Player();

                if (i == 1) players[i].prev = maxPlayerCount;
                else players[i].prev = i - 1;

                if (i == maxPlayerCount) players[i].next = 1;
                else players[i].next = i + 1;
            }

            remainPlayers = maxPlayerCount;

            currentPlayer = 1;
            round = 0;
            newRound();
        }

        private void newRound() {
            ++round;
            matched = 0;
            cardSum = 0;
            ghostPlayers = 0;

            currentPlayer = players[currentPlayer].prev;
        }

        private void changePlayerHalle(int cardIndex, int cardCount) {
            Card c = cards[cardIndex];

            if (c.round != this.round) {
                c.round = this.round;
                c.sum = 0;
            }

            if (c.sum == c.target) {
                --matched;
            }

            c.sum += cardCount;

            if (c.sum == c.target) {
                ++matched;
            }
        }

        public void Halle(int cardIndex, int count) {

            currentPlayer = players[currentPlayer].next;

            while (players[currentPlayer].dead()) {
                if (debug) System.out.println("?");
                currentPlayer = players[currentPlayer].next;
            }

            // 플레이어에게 전 라운드 데이터가 있으면 초기화
            if (players[currentPlayer].round != this.round) {
                players[currentPlayer].round = this.round;
                players[currentPlayer].halleIndex = 0;
                players[currentPlayer].halleCount = 0;
            }

            // 놓여 있던 그림과 놓을 그림에 전 라운드 데이터가 있으면 초기화
            if (players[currentPlayer].halleIndex != 0) {
                Card c = cards[players[currentPlayer].halleIndex];
                if (c.round != this.round) {
                    c.round = this.round;
                    c.sum = 0;
                }
            }

            Card c = cards[cardIndex];
            if (c.round != this.round) {
                c.round = this.round;
                c.sum = 0;
            }

            if (players[currentPlayer].halleIndex != 0)
                changePlayerHalle(players[currentPlayer].halleIndex, players[currentPlayer].halleCount * (-1));

            // 현재 플레이어의 할래 칸에 그림을 올림
            players[currentPlayer].halleIndex = cardIndex;
            players[currentPlayer].halleCount = count;

            changePlayerHalle(cardIndex, count);

            ++cardSum;
            players[currentPlayer].handCount--;
            if (debug) System.out.println("Player " + currentPlayer + " Hale (-1)");

            // 만약 할래를 진행해서 패가 0장이 되었으면 유령 처리
            if (players[currentPlayer].handCount <= 0) {
                die(currentPlayer);
            }
        }

        public void Galle(int playerIndex) {
            if (matched > 0) {
                if (debug) System.out.println("Player " + playerIndex + " win Gale (" + cardSum + ")");
                currentPlayer = playerIndex;

                // 갈래 성공했을 때 유령 플레이어면 소생
                if (players[playerIndex].handCount <= 0 && cardSum > 0) {
                    revive(playerIndex);
                }

                players[playerIndex].handCount += cardSum;

                newRound();
            } else {
                if (debug) System.out.println("Player " + playerIndex + " lose Gale (-" + Math.min(players[playerIndex].handCount, remainPlayers + ghostPlayers) + ")");

                if (players[playerIndex].round != this.round) {
                    players[playerIndex].round = this.round;
                    players[playerIndex].halleIndex = 0;
                    players[playerIndex].halleCount = 0;
                }

                if (players[playerIndex].dead()) return;

                players[playerIndex].handCount -= Math.min(players[playerIndex].handCount, remainPlayers + ghostPlayers);

                if (players[playerIndex].handCount <= 0) {
                    die(playerIndex);
                }
            }
        }

        private void die(int playerIndex) {
            // 손패가 0장되면 게임에서 제외
            // 해당 라운드에는 살아있어야 하므로 유령 처리

            --remainPlayers;
            ++ghostPlayers;

            if (debug) System.out.println("set link " + players[playerIndex].prev + " -> " + players[playerIndex].next);
            players[players[playerIndex].prev].next = players[playerIndex].next;
            players[players[playerIndex].next].prev = players[playerIndex].prev;
        }

        private void revive(int playerIndex) {
            // 갈래 성공했을 때 유령 플레이어면 소생
            // 링크를 다시 빠르게 이어주는 방법은 떠오르지 않음
            // 일단 커서 옮겨보면서 bruteforce

            ++remainPlayers;
            --ghostPlayers;

            // 어차피 갈래 성공하면 유령은 다 죽으므로 고려할 필요 없음

            if (remainPlayers == 1) {
                players[playerIndex].prev = playerIndex;
                players[playerIndex].next = playerIndex;
                return;
            }

            int cursor = players[playerIndex].prev;

            while (players[cursor].dead()) {
                cursor = players[cursor].prev;
            }

            players[playerIndex].prev = cursor;
            players[cursor].next = playerIndex;

            cursor = players[playerIndex].next;

            while (players[cursor].dead()) {
                cursor = players[cursor].next;
            }

            players[playerIndex].next = cursor;
            players[cursor].prev = playerIndex;
        }

        public void showResult() {
            sb.append(remainPlayers + ghostPlayers).append("\n");
            currentPlayer += 1;
            if (currentPlayer > maxPlayerCount) currentPlayer = 1;

            for (int i = 1; i < maxPlayerCount + 1; i++) {
                Player p = players[currentPlayer];

                if (!p.dead() || p.round == this.round) {
                    if (debug) sb.append("Player " + currentPlayer + " : ");
                    sb.append(p.handCount).append(" ");

                    if (p.round != this.round) {
                        sb.append("0 0\n");
                    } else {
                        sb.append(p.halleIndex).append(" ");
                        sb.append(p.halleCount).append("\n");
                    }
                }

                ++currentPlayer;
                if (currentPlayer > maxPlayerCount) currentPlayer = 1;
            }

            System.out.print(sb);
        }
    }

    public static void main(String[] args) throws IOException {

        maxPlayerCount = sc.nextInt();
        initialHandCount = sc.nextInt();
        pictureCount = sc.nextInt();
        maxHalleCount = sc.nextInt();
        maxGalleCount = sc.nextInt();

        cards = new Card[pictureCount + 1];

        for (int i = 1; i < pictureCount + 1; i++) {
            cards[i] = new Card(sc.nextInt());
        }

        Game game = new Game();
        for (int i = 0; i < maxHalleCount + maxGalleCount; i++) {
            int a = sc.nextInt();

            if (a == 0) {
                game.Galle(sc.nextInt());
            } else {
                game.Halle(a, sc.nextInt());
            }
        }

        game.showResult();
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

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
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
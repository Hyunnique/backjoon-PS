import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 09. 01.
 * @see BoJ 17114 (G1)
 * @category BFS
 * @note 하이퍼 토마토
 *
 * 3차원 토마토를 11차원 토마토로!
 * 시간 초과나서 Math.max 함수 콜을 최소화하고
 * FastIO Scanner 빌려서 해결함
 *
 */

public class Main {
    //static BufferedScanner sc = new BufferedScanner();
    static PScanner sc = new PScanner(System.in);

    static StringBuilder sb = new StringBuilder();

    static int m, n, o, p, q, r, s, t, u, v, w;
    static int[] z;
    static int[][][][][][][][][][][] tomato;
    static boolean[][][][][][][][][][][] visited;
    static int tomatoLeft = 0, days = 0;

    public static void main(String[] args) throws IOException {

        z = new int[11];

        for (int i = 0; i < 11; i++) {
            z[i] = sc.nextInt();
        }
        /*
        m = sc.nextInt();
        n = sc.nextInt();
        o = sc.nextInt();
        p = sc.nextInt();
        q = sc.nextInt();
        r = sc.nextInt();
        s = sc.nextInt();
        t = sc.nextInt();
        u = sc.nextInt();
        v = sc.nextInt();
        w = sc.nextInt();
        */
        tomato = new int[z[0]][z[1]][z[2]][z[3]][z[4]][z[5]][z[6]][z[7]][z[8]][z[9]][z[10]];
        visited = new boolean[z[0]][z[1]][z[2]][z[3]][z[4]][z[5]][z[6]][z[7]][z[8]][z[9]][z[10]];

        Queue<int[]> queue = new ArrayDeque<>();

        for (int a = 0; a < z[10]; a++) {
            for (int b = 0; b < z[9]; b++) {
                for (int c = 0; c < z[8]; c++) {
                    for (int d = 0; d < z[7]; d++) {
                        for (int e = 0; e < z[6]; e++) {
                            for (int f = 0; f < z[5]; f++) {
                                for (int g = 0; g < z[4]; g++) {
                                    for (int h = 0; h < z[3]; h++) {
                                        for (int i = 0; i < z[2]; i++) {
                                            for (int j = 0; j < z[1]; j++) {
                                                for (int k = 0; k < z[0]; k++) {
                                                    int x = sc.nextInt();
                                                    tomato[k][j][i][h][g][f][e][d][c][b][a] = x;

                                                    if (x == 1) {
                                                        queue.offer(new int[] { k, j, i, h, g, f, e, d, c, b, a, 0 });
                                                        visited[k][j][i][h][g][f][e][d][c][b][a] = true;
                                                    } else if (x == 0) {
                                                        tomatoLeft++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (tomatoLeft == 0) {
            System.out.println(0);
            return;
        }

        bfs(queue);

        if (tomatoLeft != 0) {
            System.out.println(-1);
            return;
        }

        System.out.println(days);
    }

    static void bfs(Queue<int[]> queue) {

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int movementDelta = -1; movementDelta <= 1; movementDelta += 2) {
                for (int targetDimension = 0; targetDimension < 11; targetDimension++) {
                    current[targetDimension] += movementDelta;

                    if (current[targetDimension] < 0 || current[targetDimension] >= z[targetDimension] ||
                            tomato[current[0]][current[1]][current[2]][current[3]][current[4]][current[5]][current[6]][current[7]][current[8]][current[9]][current[10]] != 0 ||
                            visited[current[0]][current[1]][current[2]][current[3]][current[4]][current[5]][current[6]][current[7]][current[8]][current[9]][current[10]]) {
                        current[targetDimension] -= movementDelta;
                        continue;
                    }

                    tomato[current[0]][current[1]][current[2]][current[3]][current[4]][current[5]][current[6]][current[7]][current[8]][current[9]][current[10]] = 1;
                    visited[current[0]][current[1]][current[2]][current[3]][current[4]][current[5]][current[6]][current[7]][current[8]][current[9]][current[10]] = true;
                    tomatoLeft--;

                    if (current[11] + 1 > days) days = current[11] + 1;
                    queue.offer(new int[] { current[0], current[1], current[2], current[3], current[4], current[5], current[6], current[7], current[8], current[9], current[10], current[11] + 1 });

                    current[targetDimension] -= movementDelta;
                }
            }
        }
    }
}

class PScanner{private final InputStreamReader in;private final char[]buf;private int len,ptr;public PScanner(InputStream input){in=new InputStreamReader(input);buf=new char[8192];}public boolean hasNext(){consume();return ptr<len&&buf[ptr]>' ';}public String next(){consume();char[]cbuf=new char[16];char clen=0;while((cbuf[clen++]=read())>' '){if(clen==cbuf.length)cbuf=Arrays.copyOf(cbuf,clen << 2);}return new String(cbuf,0,clen - 1);}public int nextInt(){consume();int v=0;char c=read();boolean neg=c=='-';if(neg)c=read();do{v=v * 10+c - '0';}while('0'<=(c=read())&&c<='9');return neg?-v:v;}public long nextLong(){consume();long v=0;char c=read();boolean neg=c=='-';if(neg)c=read();do{v=v * 10+c - '0';}while('0'<=(c=read())&&c<='9');return neg?-v:v;}private char read(){if(ptr==len)fill();return ptr<len?buf[ptr++]:0;}private void fill(){try{len=in.read(buf);ptr=0;}catch(IOException e){throw new RuntimeException(e.getMessage());}}private void consume(){char c;while((c=read())<=' '&&c!=0);ptr--;}}

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
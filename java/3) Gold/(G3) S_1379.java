import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Hyunnique
 * @since 2023. 08. 11.
 * @see BoJ 1379 (G3)
 * @category Greedy
 * @note 강의실 2
 * 
 * 방의 강의가 끝나는 시간,
 * 강의가 시작하는 시간으로
 * pq를 2개 만들어서 가장 먼저 끝난 강의실에
 * 가장 먼저 시작하는 강의를 넣고
 * 불가능하면 방 증설
 * 
 */

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

class Lecture implements Comparable<Lecture> {
    int index, start, end;

    Lecture(int index, int start, int end) {
        this.index = index;
        this.start = start;
        this.end = end;
    }

    @Override
    public int compareTo(Lecture o) {
        return Integer.compare(start, o.start);
    }
}

class Room implements Comparable<Room> {
    int index, end;

    Room(int index, int end) {
        this.index = index;
        this.end = end;
    }

    @Override
    public int compareTo(Room o) {
        return Integer.compare(end, o.end);
    }
}

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();
    public static void main(String[] args) throws Exception {

        N = sc.nextInt();

        PriorityQueue<Lecture> pq = new PriorityQueue<>();
        PriorityQueue<Room> rooms = new PriorityQueue<>();
        TreeMap<Integer, Integer> result = new TreeMap<>();
        
        int roomCount = 1;
        rooms.offer(new Room(1, 0));

        for (int i = 0; i < N; i++) {
            pq.offer(new Lecture(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }

        while (!pq.isEmpty()) {
            Lecture current = pq.poll();

            if (rooms.peek().end > current.start) {
                rooms.offer(new Room(++roomCount, 0));
            }

            Room r = rooms.poll();
            r.end = current.end;
            result.put(current.index, r.index);
            rooms.offer(r);
        }

        for (int a : result.values()) {
            sb.append(a).append("\n");
        }

        System.out.println(rooms.size());
        System.out.println(sb.toString());
    }
}

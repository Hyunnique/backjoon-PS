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
 * @since 2023. 08. 17.
 * @see BoJ 1700 (G1)
 * @category Greedy
 * @note 멀티탭 스케줄링
 * 
 * 우선순위 큐를 사용해서 전기용품을 사용 순서로 구분한다.
 * 사이클이 돌아야하고, 단방향이기 때문에 직전의 요소가
 * 가장 낮은 순위를 가져야 한다.
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

class Device implements Comparable<Device> {
    int index;
    Queue<Integer> order = new ArrayDeque<>();
    
    Device(int index) {
        this.index = index;
    }

    public void addOrder(int order) {
        this.order.offer(order);
    }

    public void pollOrder() {
        this.order.poll();
    }

    @Override
    public int compareTo(Device o) {
        int a, b;
        if (order.isEmpty()) a = Integer.MAX_VALUE;
        else a = order.peek();

        if (o.order.isEmpty()) b = Integer.MAX_VALUE;
        else b = o.order.peek();

        //System.out.println(b + " vs " + a);
        return Integer.compare(b, a);
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}

public class Main {   
    static int N;
    static StringBuilder sb = new StringBuilder();
    static BufferedScanner sc = new BufferedScanner();

    public static void main(String[] args) throws Exception {

        N = sc.nextInt();
        int K = sc.nextInt();
        
        Map<Integer, Device> deviceMap = new TreeMap<>();
        int[] devices = new int[K + 1];
        for (int i = 1; i <= K; i++) {
            int x = sc.nextInt();
            devices[i] = x;
            
            if (!deviceMap.containsKey(x)) {
                deviceMap.put(x, new Device(x));
            }

            deviceMap.get(x).addOrder(i);
        }

        PriorityQueue<Device> pq = new PriorityQueue<>();
        int pollCount = 0;
        for (int i = 1; i <= K; i++) {
            deviceMap.get(devices[i]).pollOrder();

            if (pq.contains(deviceMap.get(devices[i]))) {
                pq.remove(deviceMap.get(devices[i]));
                pq.offer(deviceMap.get(devices[i]));

                //System.out.println(pq.toString() + ", pollCount=" + pollCount + ", index=" + i);
                continue;
            }

            if (pq.size() >= N) {
                pq.poll();
                pollCount++;
            }

            // 인덱스로 존재하면 Order Queue size() > 0임을 보장함
            pq.offer(deviceMap.get(devices[i]));
            //System.out.println(pq.toString() + ", pollCount=" + pollCount + ", index=" + i);
        }

        System.out.println(pollCount);
    }
}

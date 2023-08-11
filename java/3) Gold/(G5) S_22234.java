import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author 김대현
 * @since 2023. 08. 08.
 * @note BoJ 22234 (G5)
 * 
 * TL 1.5s / ML 512MB
 * 
 * 대기하는 N명의 손님
 * 최대 1회당 업무 처리 시간 T
 * > 손님의 업무 시간이 T보다 크다면 T만큼만 하고 나머지는 뒤로 돌려보내서 다시 시킴
 * 영업 시간 W
 * > W-1초까지만 영업 후에 W초에 영업 종료
 * 
 * x번 손님에 대한 정보가 id P, 업무 처리에 필요한 시간 t로 주어짐
 * 
 * 업무 시작 이후에 들어오는 M명의 손님
 * x번 손님에 대한 정보가 id P, 업무 처리에 필요한 시간 t, 영업 시작 후 들어오는 시간 c로 주어짐
 * 
 * 
 */

class Customer implements Comparable<Customer> {
    int index, time, delay;

    Customer(int index, int time, int delay) {
        this.index = index;
        this.time = time;
        this.delay = delay;
    }

    @Override
    public int compareTo(Customer o) {
        return this.delay - o.delay;
    }
}

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());
        
        ArrayDeque<Customer> CustomerQueue = new ArrayDeque<>();
        PriorityQueue<Customer> DelayedQueue = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            CustomerQueue.offer(new Customer(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0));
        }

        int M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            DelayedQueue.offer(new Customer(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        int time = 0;

        while (!CustomerQueue.isEmpty() && time < W) {
            // CustomerQueue에서 손님 뽑아 처리
            Customer processing = CustomerQueue.poll();
            
            //System.out.println((W-time) + "," + processing.time + "," + T);
            int elapsed = Math.min(W - time, Math.min(processing.time, T));
            processing.time -= elapsed;
            time += elapsed;

            for (int i = 0; i < elapsed; i++) {
                sb.append(processing.index).append("\n");
            }

            // DelayedQueue에서 지나간 시간동안 출발할 손님 Queue에 offer
            while (!DelayedQueue.isEmpty() && DelayedQueue.peek().delay <= time) {
                CustomerQueue.offer(DelayedQueue.poll());
            }

            // 이후 processing을 CustomerQueue 맨 뒤로 보냄
            if (processing.time > 0) CustomerQueue.offer(processing);
        }

        System.out.println(sb.toString());
    }
}

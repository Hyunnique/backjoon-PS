import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        
        Queue<Integer> q = new LinkedList<>();

        int latest = -1;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            
            switch (st.nextToken()) {
                case "push":
                    int x = Integer.parseInt(st.nextToken());
                    q.add(x);
                    latest = x;
                    break;
                case "pop":
                    if (q.isEmpty()) result.append("-1\n");
                    else result.append(q.poll() + "\n");
                    break;
                case "size":
                    result.append(q.size() + "\n");
                    break;
                case "empty":
                    if (q.isEmpty()) result.append("1\n");
                    else result.append("0\n");
                    break;
                case "front":
                    if (q.isEmpty()) result.append("-1\n");
                    else result.append(q.peek() + "\n");
                    break;
                case "back":
                    if (q.isEmpty()) result.append("-1\n");
                    else result.append(latest + "\n");
                    break;
            }
        }
        System.out.println(result);
    }
}

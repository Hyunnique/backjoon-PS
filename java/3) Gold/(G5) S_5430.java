import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder result = new StringBuilder();

        int test_case = Integer.parseInt(br.readLine());

        for (int _t = 0; _t < test_case; _t++) {
            String func = br.readLine();
            int N = Integer.parseInt(br.readLine());

            List<Integer> arr = new ArrayList<>();
            int start = 0, end = N - 1;
            boolean r = false;

            String str = br.readLine();
            if (str.length() == 2) str = "";
            else {
                str = str.substring(1, str.length() - 1);
                for (String s : str.split(",")) {
                    arr.add(Integer.parseInt(s));
                }
            }


            boolean errored = false;
            for (char f : func.toCharArray()) {
                if (f == 'R') r = !r;
                else {
                    if (r) end--;
                    else start++;
                }

                if (start > end + 1) {
                    result.append("error\n");
                    errored = true;
                    break;
                }
            }

            if (!errored) {
                result.append("[");
                if (r) {
                    for (int i = end; i >= start; i--) {
                        result.append(arr.get(i));
                        if (i != start) result.append(",");
                    }
                } else {
                    for (int i = start; i <= end; i++) {
                        result.append(arr.get(i));
                        if (i != end) result.append(",");
                    }
                }
                result.append("]\n");
            }
        }

        System.out.println(result);
    }
}

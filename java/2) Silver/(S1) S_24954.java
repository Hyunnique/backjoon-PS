import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static List<List<Pair>> discountList = new ArrayList<>();
    static class Pair {
        int index, price;

        Pair(int index, int price) {
            this.index = index;
            this.price = price;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        List<Integer> priceList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            priceList.add(Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < N; i++) {
            int p = Integer.parseInt(br.readLine());
            discountList.add(new ArrayList<>());

            for (int j = 0; j < p; j++) {
                st = new StringTokenizer(br.readLine());

                discountList.get(i).add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            }
        }

        System.out.println(buyPotion(priceList));
    }

    static int buyPotion(List<Integer> priceList) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < priceList.size(); i++) {
            if (priceList.get(i) == -1) continue;

            List<Integer> subList = new ArrayList<>(priceList);

            for (int j = 0; j < discountList.get(i).size(); j++) {
                Pair p = discountList.get(i).get(j);
                if (subList.get(p.index - 1) == -1) continue;
                subList.set(p.index - 1, Math.max(subList.get(p.index - 1) - p.price, 1));
            }

            subList.set(i, -1);
            min = Math.min(min, priceList.get(i) + buyPotion(subList));
        }

        if (min == Integer.MAX_VALUE) min = 0;

        return min;
    }
}
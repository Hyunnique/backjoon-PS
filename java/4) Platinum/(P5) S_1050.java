import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class Craft {
    public String result;
    public List<Material> material = new ArrayList<>();

    Craft(String result, String materialString) {
        this.result = result;

        addMaterials(materialString);
    }

    private void addMaterials(String materialString) {
        String[] mats = materialString.split("\\+");

        for (String mat : mats) {
            String mat_string = mat.replaceAll("[0-9]", "");
            int mat_count = Integer.parseInt(mat.replaceAll("[^0-9]", ""));

            material.add(new Material(mat_string, mat_count));
        }
    }
}

class Material {
    private String name;
    private int count;

    Material(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder result = new StringBuilder();

        Map<String, Double> map = new HashMap<>();
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Craft> crafting = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            map.put(st.nextToken(), (double)(Integer.parseInt(st.nextToken())));
        }

        for (int i = 0; i < M; i++) {
            String line = br.readLine();

            String[] _a = line.split("=");
            crafting.add(new Craft(_a[0], _a[1]));
        }

        br.close();

        boolean changed = false;

        while (true) {
            changed = false;

            for (Craft craft : crafting) {
                double sum = 0;
                boolean available = true;
                for (Material mat : craft.material) {
                    if (map.containsKey(mat.getName())) {
                        sum += mat.getCount() * map.get(mat.getName());
                    } else {
                        available = false;
                        break;
                    }
                }

                if (!available) continue;
                else {
                    if (map.containsKey(craft.result)) {
                        if (map.get(craft.result) > sum) {
                            map.put(craft.result, sum);
                            changed = true;
                        }
                    } else {
                        map.put(craft.result, sum);
                        changed = true;
                    }

                    //System.out.println("B-" + key + " : " + sum + ", " + changed);
                    if (changed) break;
                }
            }

            if (!changed) break;
        }

        if (map.containsKey("LOVE")) {
            if (map.get("LOVE") > 1000000000.0) System.out.println("1000000001\n");
            else System.out.println(String.format("%.0f", map.get("LOVE")) + "\n");
        } else {
            System.out.println("-1\n");
        }
    }
}

import java.util.*;

public class Problem7_AutocompleteSystem {

    private Map<String, Integer> queryFrequency = new HashMap<>();

    public void addQuery(String query) {
        queryFrequency.put(query,
                queryFrequency.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {
        List<String> results = new ArrayList<>();

        for (String query : queryFrequency.keySet()) {
            if (query.startsWith(prefix)) {
                results.add(query);
            }
        }

        results.sort((a, b) ->
                queryFrequency.get(b) - queryFrequency.get(a));

        return results.subList(0,
                Math.min(5, results.size()));
    }

    public static void main(String[] args) {
        Problem7_AutocompleteSystem auto = new Problem7_AutocompleteSystem();

        auto.addQuery("java tutorial");
        auto.addQuery("javascript guide");
        auto.addQuery("java download");

        System.out.println(auto.search("jav"));
    }
}
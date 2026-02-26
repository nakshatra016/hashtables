import java.util.*;

public class Problem5_AnalyticsDashboard {

    private Map<String, Integer> pageViews = new HashMap<>();
    private Map<String, Set<String>> uniqueVisitors = new HashMap<>();
    private Map<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSources.put(source,
                trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {

        System.out.println("Top Pages:");
        pageViews.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(10)
                .forEach(e -> System.out.println(
                        e.getKey() + " - " + e.getValue()
                                + " views (" +
                                uniqueVisitors.get(e.getKey()).size()
                                + " unique)"));

        System.out.println("\nTraffic Sources:");
        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        Problem5_AnalyticsDashboard dashboard = new Problem5_AnalyticsDashboard();

        dashboard.processEvent("/news", "user1", "Google");
        dashboard.processEvent("/news", "user2", "Facebook");
        dashboard.processEvent("/sports", "user1", "Direct");

        dashboard.getDashboard();
    }
}

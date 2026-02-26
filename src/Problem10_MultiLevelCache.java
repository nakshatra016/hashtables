import java.util.*;

public class Problem10_MultiLevelCache {

    private final int L1_SIZE = 3;

    private LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(
                        Map.Entry<String, String> eldest) {
                    return size() > L1_SIZE;
                }
            };

    private Map<String, String> L2 = new HashMap<>();
    private Map<String, String> L3 = new HashMap<>();

    public Problem10_MultiLevelCache() {
        L3.put("video1", "Video Data 1");
        L3.put("video2", "Video Data 2");
        L3.put("video3", "Video Data 3");
    }

    public String getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            return "L1 HIT → " + L1.get(videoId);
        }

        if (L2.containsKey(videoId)) {
            String data = L2.get(videoId);
            L1.put(videoId, data);
            return "L2 HIT → Promoted to L1";
        }

        if (L3.containsKey(videoId)) {
            String data = L3.get(videoId);
            L2.put(videoId, data);
            return "L3 HIT → Added to L2";
        }

        return "Video Not Found";
    }

    public static void main(String[] args) {

        Problem10_MultiLevelCache cache = new Problem10_MultiLevelCache();

        System.out.println(cache.getVideo("video1"));
        System.out.println(cache.getVideo("video1"));
    }
}
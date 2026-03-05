import java.util.*;

public class MultiLevelCache {

    static int L1_SIZE = 10000;

    static LinkedHashMap<String, String> L1 = new LinkedHashMap<String, String>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, String> e) {
            return size() > L1_SIZE;
        }
    };

    static HashMap<String, String> L2 = new HashMap<>();
    static HashMap<String, String> database = new HashMap<>();

    static int L1Hits = 0;
    static int L2Hits = 0;
    static int L3Hits = 0;

    public static String getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            L1Hits++;
            return "L1 Cache HIT";
        }

        if (L2.containsKey(videoId)) {
            L2Hits++;
            String data = L2.get(videoId);
            L1.put(videoId, data);
            return "L2 Cache HIT → Promoted to L1";
        }

        if (database.containsKey(videoId)) {
            L3Hits++;
            String data = database.get(videoId);
            L2.put(videoId, data);
            return "L3 Database HIT → Added to L2";
        }

        return "Video Not Found";
    }

    public static void getStatistics() {

        int total = L1Hits + L2Hits + L3Hits;

        System.out.println("L1 Hits: " + L1Hits);
        System.out.println("L2 Hits: " + L2Hits);
        System.out.println("L3 Hits: " + L3Hits);
        System.out.println("Total Requests: " + total);
    }

    public static void main(String[] args) {

        database.put("video_123", "data1");
        database.put("video_999", "data2");

        System.out.println(getVideo("video_123"));
        System.out.println(getVideo("video_123"));
        System.out.println(getVideo("video_999"));

        getStatistics();
    }
}
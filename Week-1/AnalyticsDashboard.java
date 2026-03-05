import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class AnalyticsDashboard {

    static HashMap<String, Integer> pageViews = new HashMap<>();
    static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    static HashMap<String, Integer> trafficSources = new HashMap<>();

    public static void processEvent(Event e) {

        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        trafficSources.put(e.source, trafficSources.getOrDefault(e.source, 0) + 1);
    }

    public static void getDashboard() {

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        System.out.println("Top Pages:");

        int i = 1;

        while (!pq.isEmpty() && i <= 10) {

            Map.Entry<String, Integer> e = pq.poll();

            int unique = uniqueVisitors.get(e.getKey()).size();

            System.out.println(i + ". " + e.getKey() +
                    " - " + e.getValue() + " views (" +
                    unique + " unique)");

            i++;
        }
    }

    public static void main(String[] args) {

        processEvent(new Event("/article/breaking-news", "user_123", "google"));
        processEvent(new Event("/article/breaking-news", "user_456", "facebook"));
        processEvent(new Event("/sports/championship", "user_777", "direct"));
        processEvent(new Event("/sports/championship", "user_888", "google"));

        getDashboard();
    }
}
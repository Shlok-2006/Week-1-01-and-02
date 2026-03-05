import java.util.*;

class DNSEntry {
    String ip;
    long expiryTime;

    DNSEntry(String ip, long ttl) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + ttl * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    static int MAX_SIZE = 5;

    static LinkedHashMap<String, DNSEntry> cache = new LinkedHashMap<String, DNSEntry>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
            return size() > MAX_SIZE;
        }
    };

    static int hits = 0;
    static int misses = 0;

    public static String queryUpstream(String domain) {
        return "172.217.14." + new Random().nextInt(255);
    }

    public static String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                return "Cache HIT → " + entry.ip;
            } else {
                cache.remove(domain);
            }
        }

        misses++;
        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(ip, 300));

        return "Cache MISS → " + ip;
    }

    public static void getCacheStats() {

        int total = hits + misses;
        double rate = (total == 0) ? 0 : (hits * 100.0 / total);

        System.out.println("Hit Rate: " + rate + "%");
    }

    public static void main(String[] args) {

        System.out.println(resolve("google.com"));
        System.out.println(resolve("google.com"));

        getCacheStats();
    }
}
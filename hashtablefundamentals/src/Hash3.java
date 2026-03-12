import java.util.*;

class hash3 {
    String domain;
    String ipAddress;
    long expiryTime;

    hash3(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttl * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNS {

    private LinkedHashMap<String, hash3> cache;
    private int capacity;
    private int hits = 0;
    private int misses = 0;

    public DNS(int capacity) {

        this.capacity = capacity;

        cache = new LinkedHashMap<String, hash3>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, hash3> eldest) {
                return size() > DNS.this.capacity;
            }
        };
    }

    // Simulate upstream DNS lookup
    private String queryUpstream(String domain) {
        return "172.217.14." + new Random().nextInt(255);
    }

    // Resolve domain
    public String resolve(String domain) {

        hash3 entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            System.out.println(domain + " -> Cache HIT -> " + entry.ipAddress);
            return entry.ipAddress;
        }

        misses++;
        System.out.println(domain + " -> Cache MISS -> Query upstream");

        String ip = queryUpstream(domain);

        hash3 newEntry = new hash3(domain, ip, 300);
        cache.put(domain, newEntry);

        return ip;
    }

    // Cache statistics
    public void getCacheStats() {

        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits * 100.0) / total;

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        DNS dns = new DNS(5);

        dns.resolve("google.com");
        dns.resolve("google.com");
        dns.resolve("openai.com");

        dns.getCacheStats();
    }
}

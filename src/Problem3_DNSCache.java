import java.util.*;

/*
 Problem 3: DNS Cache with TTL
 Concepts Used:
 - HashMap for O(1) lookup
 - LinkedHashMap for LRU eviction
 - Time-based expiration using System.currentTimeMillis()
*/

public class Problem3_DNSCache {

    // Inner class for DNS Entry
    static class DNSEntry {
        String ipAddress;
        long expiryTime;

        DNSEntry(String ipAddress, long ttlSeconds) {
            this.ipAddress = ipAddress;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private final int MAX_CACHE_SIZE = 5;

    // LinkedHashMap for LRU (accessOrder = true)
    private LinkedHashMap<String, DNSEntry> cache;

    private int cacheHits = 0;
    private int cacheMisses = 0;

    public Problem3_DNSCache() {
        cache = new LinkedHashMap<>(16, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    // Simulated upstream DNS query
    private String queryUpstreamDNS(String domain) {
        // In real life, this would query actual DNS server
        return "192.168.1." + new Random().nextInt(255);
    }

    public String resolve(String domain) {

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                cacheHits++;
                return "Cache HIT → IP: " + entry.ipAddress;
            } else {
                cache.remove(domain);
            }
        }

        // Cache miss
        cacheMisses++;
        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(ip, 10)); // TTL = 10 seconds

        return "Cache MISS → New IP: " + ip;
    }

    public void getCacheStats() {
        int total = cacheHits + cacheMisses;
        double hitRate = (total == 0) ? 0 : (cacheHits * 100.0 / total);

        System.out.println("Cache Hits: " + cacheHits);
        System.out.println("Cache Misses: " + cacheMisses);
        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        Problem3_DNSCache dnsCache = new Problem3_DNSCache();

        System.out.println(dnsCache.resolve("google.com"));
        System.out.println(dnsCache.resolve("google.com"));

        Thread.sleep(11000); // wait for TTL to expire

        System.out.println(dnsCache.resolve("google.com"));

        dnsCache.getCacheStats();
    }
}
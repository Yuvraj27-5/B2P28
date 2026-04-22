//Problem 3: DNS Cache with TTL (Time To Live)
//Scenario: Build a DNS resolver cache that stores domain-to-IP mappings to reduce lookup
//times from 100ms to <1ms. Cache entries should expire after a specified TTL.
//Problem Statement: Create a DNS caching system that:
//        ● Stores domain name → IP address mappings
//● Implements TTL-based expiration (entries expire after X seconds)
//● Automatically removes expired entries
//● Handles cache misses by querying upstream DNS
//● Reports cache hit/miss ratios
//● Implements LRU eviction when cache is full
//Concepts Covered:
//        ● Hash table implementation with custom Entry class
//● Chaining for collision resolution
//● Time-based operations
//● Performance metrics
import java.util.*;

public class DNSCache {

    // Cache Entry class
    class Entry {
        String domain;
        String ip;
        long expiryTime;
        Entry prev;
        Entry next;

        Entry(String domain, String ip, long ttl) {
            this.domain = domain;
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttl * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private HashMap<String, Entry> cache;
    private Entry head, tail;
    private int capacity;

    private int hits = 0;
    private int misses = 0;

    public DNSCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
    }

    // Add to front (LRU)
    private void addToFront(Entry node) {
        node.next = head;
        node.prev = null;

        if (head != null)
            head.prev = node;

        head = node;

        if (tail == null)
            tail = node;
    }

    // Remove node
    private void removeNode(Entry node) {
        if (node.prev != null)
            node.prev.next = node.next;
        else
            head = node.next;

        if (node.next != null)
            node.next.prev = node.prev;
        else
            tail = node.prev;
    }

    // Move node to front
    private void moveToFront(Entry node) {
        removeNode(node);
        addToFront(node);
    }

    // Remove LRU node
    private void removeLRU() {
        if (tail != null) {
            cache.remove(tail.domain);
            removeNode(tail);
        }
    }

    // Put domain in cache
    public void put(String domain, String ip, long ttl) {

        if (cache.containsKey(domain)) {
            Entry node = cache.get(domain);
            node.ip = ip;
            node.expiryTime = System.currentTimeMillis() + ttl * 1000;
            moveToFront(node);
            return;
        }

        if (cache.size() >= capacity)
            removeLRU();

        Entry newNode = new Entry(domain, ip, ttl);
        cache.put(domain, newNode);
        addToFront(newNode);
    }

    // Get IP address
    public String get(String domain) {

        if (!cache.containsKey(domain)) {
            misses++;
            return queryUpstreamDNS(domain);
        }

        Entry node = cache.get(domain);

        if (node.isExpired()) {
            removeNode(node);
            cache.remove(domain);
            misses++;
            return queryUpstreamDNS(domain);
        }

        hits++;
        moveToFront(node);
        return node.ip;
    }

    // Simulate upstream DNS lookup
    private String queryUpstreamDNS(String domain) {

        // Simulated IP generation
        String ip = "192.168.1." + new Random().nextInt(255);

        // Store in cache with TTL 10 sec
        put(domain, ip, 10);

        return ip;
    }

    // Hit ratio
    public double getHitRatio() {
        int total = hits + misses;
        if (total == 0)
            return 0;
        return (double) hits / total;
    }

    public void displayStats() {
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Ratio: " + getHitRatio());
    }

    public static void main(String[] args) throws InterruptedException {

        DNSCache dns = new DNSCache(3);

        System.out.println(dns.get("google.com"));
        System.out.println(dns.get("facebook.com"));
        System.out.println(dns.get("google.com"));

        Thread.sleep(11000); // wait for TTL expire

        System.out.println(dns.get("google.com"));

        dns.displayStats();
    }
}

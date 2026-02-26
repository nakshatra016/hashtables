import java.util.*;

public class Problem6_RateLimiter {

    class TokenBucket {
        int tokens;
        int maxTokens;
        long lastRefillTime;
        int refillRate; // tokens per second

        TokenBucket(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long seconds = (now - lastRefillTime) / 1000;
            if (seconds > 0) {
                tokens = Math.min(maxTokens, tokens + (int)(seconds * refillRate));
                lastRefillTime = now;
            }
        }
    }

    private Map<String, TokenBucket> clients = new HashMap<>();

    public boolean checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId, new TokenBucket(5, 1));
        return clients.get(clientId).allowRequest();
    }

    public static void main(String[] args) {
        Problem6_RateLimiter limiter = new Problem6_RateLimiter();

        for (int i = 0; i < 7; i++) {
            System.out.println("Request " + i + ": "
                    + limiter.checkRateLimit("client1"));
        }
    }
}
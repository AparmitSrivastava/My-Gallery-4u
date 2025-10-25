class Pair {
    long time;
    int node;

    Pair(long time, int node) {
        this.time = time;
        this.node = node;
    }
}

class Solution {
    public int countPaths(int n, int[][] roads) {
        long MOD = 1_000_000_007L;

        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            // roads are bidirectional
            adj.get(road[0]).add(new Pair(road[2], road[1]));
            adj.get(road[1]).add(new Pair(road[2], road[0]));
        }

        long[] dist = new long[n];
        long[] ways = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);

        dist[0] = 0;
        ways[0] =1;

        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.time));
        pq.offer(new Pair(0, 0));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            long time = cur.time;
            int u = cur.node;

            // if longer path found then just move ahead
            if (time > dist[u])
                continue;

            for (Pair it : adj.get(u)) {
                int v = it.node;
                long edgeTime = it.time;
                //  A new shorter path to v is found
                if (dist[v] > time + edgeTime) {
                    dist[v] = time + edgeTime;
                    pq.offer(new Pair(dist[v], v));
                    ways[v] = ways[u];
                }

                // Another path with the same shortest time is found
                else if (dist[v] == time + edgeTime) {
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }
        return (int) ways[n - 1]; //number of ways to reach the destination (n-1)
    }
}

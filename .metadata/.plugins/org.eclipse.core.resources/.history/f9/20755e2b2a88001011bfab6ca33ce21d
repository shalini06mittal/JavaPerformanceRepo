package main;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PerformanceTuningChallenge {
    // Simulates a web application with performance issues
    
    private static final Map<String, Object> globalCache = new ConcurrentHashMap<>();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Performance Tuning Challenge");
        System.out.println("Monitor with JProfiler/JVisualVM to identify bottlenecks");
        
        // Simulate load
        simulateWebTraffic();
    }
    
    private static void simulateWebTraffic() throws InterruptedException {
        int requestCount = 1000;
        CountDownLatch latch = new CountDownLatch(requestCount);
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < requestCount; i++) {
            final int requestId = i;
            threadPool.submit(() -> {
                try {
                    processRequest(requestId);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        long endTime = System.currentTimeMillis();
        
        System.out.println("Total time: " + (endTime - startTime) + " ms");
        System.out.println("Average per request: " + (endTime - startTime) / (double) requestCount + " ms");
        
        threadPool.shutdown();
    }
    
    private static void processRequest(int requestId) {
        // Simulate request processing with various performance issues
        
        // Issue 1: Inefficient string operations
        String response = buildResponse(requestId);
        
        // Issue 2: Unnecessary object creation
        List<DataObject> data = fetchData(requestId);
        
        // Issue 3: Inefficient algorithms
        data = processData(data);
        
        // Issue 4: Cache misuse
        cacheResult(requestId, response + data.size());
    }
    
    private static String buildResponse(int requestId) {
        // ISSUE: Inefficient string concatenation
        String response = "";
        for (int i = 0; i < 100; i++) {
            response += "RequestId:" + requestId + ",Data:" + i + ";";
        }
        return response;
    }
    
    private static List<DataObject> fetchData(int requestId) {
        // ISSUE: Creating many unnecessary objects
        List<DataObject> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            data.add(new DataObject("Data" + requestId + "_" + i, 
                                  new HashMap<String, String>()));
        }
        return data;
    }
    
    private static List<DataObject> processData(List<DataObject> data) {
        // ISSUE: Inefficient sorting and processing
        Collections.sort(data, (a, b) -> {
            // Expensive comparison operation
            return expensiveStringComparison(a.getName(), b.getName());
        });
        
        // ISSUE: Creating new objects instead of modifying existing ones
        List<DataObject> processed = new ArrayList<>();
        for (DataObject obj : data) {
            DataObject newObj = new DataObject(obj.getName().toUpperCase(), 
                                             new HashMap<>(obj.getProperties()));
            processed.add(newObj);
        }
        
        return processed;
    }
    
    private static int expensiveStringComparison(String a, String b) {
        // Simulate expensive comparison
        for (int i = 0; i < 100; i++) {
            a = a.toLowerCase().trim();
            b = b.toLowerCase().trim();
        }
        return a.compareTo(b);
    }
    
    private static void cacheResult(int requestId, String result) {
        // ISSUE: Cache grows indefinitely
        globalCache.put("request_" + requestId, result);
    }
    
    static class DataObject {
        private String name;
        private Map<String, String> properties;
        
        public DataObject(String name, Map<String, String> properties) {
            this.name = name;
            this.properties = properties;
        }
        
        public String getName() { return name; }
        public Map<String, String> getProperties() { return properties; }
    }
}


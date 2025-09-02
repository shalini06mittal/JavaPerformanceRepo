package main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryLeakDemo {
    // Leak 1: Static collections that grow indefinitely
    private static Map<String, Object> staticCache = new HashMap<>();
    
    // Leak 2: Thread-local variables not cleaned up
    private static ThreadLocal<List<byte[]>> threadLocalLeak = new ThreadLocal<>();
    
    // Leak 3: Event listeners not removed
    private static List<EventListener> listeners = new ArrayList<>();
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Memory Leak Demo - Monitor with JVisualVM");
        
        // Start background threads that create leaks
        startStaticCacheLeak();
        startThreadLocalLeak();
        startListenerLeak();
        
        // Keep application running
        System.out.println("Application running... Press Ctrl+C to stop");
        while (true) {
            Thread.sleep(5000);
            printMemoryStats();
        }
    }
    
    private static void startStaticCacheLeak() {
        new Thread(() -> {
            int counter = 0;
            while (true) {
                try {
                    // Continuously add to static cache without cleanup
                    staticCache.put("key" + counter++, new byte[1024 * 100]); // 100KB objects
                    
                    if (counter % 100 == 0) {
                        System.out.println("Static cache size: " + staticCache.size());
                    }
                    
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "StaticCacheLeakThread").start();
    }
    
    private static void startThreadLocalLeak() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                // Create thread-local data but never clean it up
                List<byte[]> data = new ArrayList<>();
                for (int j = 0; j < 100; j++) {
                    data.add(new byte[1024 * 50]); // 50KB objects
                }
                threadLocalLeak.set(data);
                
                // Thread continues running without cleanup
                while (true) {
                    try {
                        Thread.sleep(1000);
                        // ThreadLocal data remains in memory
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }, "ThreadLocalLeakThread-" + i).start();
        }
    }
    
    private static void startListenerLeak() {
        new Thread(() -> {
            while (true) {
                try {
                    // Keep adding listeners without removing old ones
                    listeners.add(new EventListener("Listener-" + System.currentTimeMillis()));
                    
                    if (listeners.size() % 1000 == 0) {
                        System.out.println("Active listeners: " + listeners.size());
                    }
                    
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "ListenerLeakThread").start();
    }
    
    private static void printMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        System.out.printf("\nMemory Stats - Used: %d MB, Free: %d MB, Total: %d MB, Max: %d MB\n",
                         usedMemory / 1024 / 1024,
                         freeMemory / 1024 / 1024,
                         totalMemory / 1024 / 1024,
                         maxMemory / 1024 / 1024);
    }
    
    // Simple event listener class
    static class EventListener {
        private final String name;
        private final byte[] data; // Holds some data
        
        public EventListener(String name) {
            this.name = name;
            this.data = new byte[1024 * 10]; // 10KB per listener
        }
        
        public String getName() { return name; }
    }
}


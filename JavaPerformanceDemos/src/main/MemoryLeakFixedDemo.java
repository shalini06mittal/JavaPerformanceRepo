package main;


import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class MemoryLeakFixedDemo {
    // Fix 1: Use WeakHashMap or implement cache eviction
    private static Map<String, Object> staticCache = new ConcurrentHashMap<>();
    private static final int MAX_CACHE_SIZE = 1000;
    
    // Fix 2: Properly clean up ThreadLocal
    private static ThreadLocal<List<byte[]>> threadLocalData = new ThreadLocal<List<byte[]>>() {
        @Override
        protected List<byte[]> initialValue() {
            return new ArrayList<>();
        }
    };
    
    // Fix 3: Use WeakReferences for listeners
    private static List<WeakReference<EventListener>> listeners = new ArrayList<>();
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Fixed Memory Leak Demo");
        
        startFixedCacheDemo();
        startFixedThreadLocalDemo();
        startFixedListenerDemo();
        
        while (true) {
            Thread.sleep(5000);
            printMemoryStats();
            cleanupReferences();
        }
    }
    
    private static void startFixedCacheDemo() {
        new Thread(() -> {
            int counter = 0;
            while (true) {
                try {
                    // Implement cache size limit
                    if (staticCache.size() >= MAX_CACHE_SIZE) {
                        // Remove oldest entries (simple FIFO)
                        staticCache.clear();
                        System.out.println("Cache cleared due to size limit");
                    }
                    
                    staticCache.put("key" + counter++, new byte[1024 * 100]);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "FixedCacheThread").start();
    }
    
    private static void startFixedThreadLocalDemo() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    List<byte[]> data = threadLocalData.get();
                    for (int j = 0; j < 100; j++) {
                        data.add(new byte[1024 * 50]);
                    }
                    
                    try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // Simulate work
                    
                } finally {
                    // Always clean up ThreadLocal
                    threadLocalData.remove();
                }
            }, "FixedThreadLocalThread-" + i).start();
        }
    }
    
    private static void startFixedListenerDemo() {
        new Thread(() -> {
            while (true) {
                try {
                    listeners.add(new WeakReference<>(
                        new EventListener("Listener-" + System.currentTimeMillis())));
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "FixedListenerThread").start();
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
    
    private static void cleanupReferences() {
        // Clean up dead weak references
        listeners.removeIf(ref -> ref.get() == null);
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



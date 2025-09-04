package main;

public class GCMonitoringDemo {


	private static final int ARRAY_SIZE = 1024 * 1024; // 1MB arrays
	private static volatile Object[] memoryHolder;

	public static void main(String[] args) {
		System.out.println("Starting GC Monitoring Demo...");
		System.out.println("JVM Args should include: -Xms256m -Xmx512m -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps");
		// Run different allocation patterns
		runYoungGenerationTest();
		runOldGenerationTest();
		runMemoryLeakSimulation();
		runLargeObjectAllocation();

		System.out.println("Demo completed. Check GC logs for analysis.");


	}
	/**
	 * Creates many short-lived objects to trigger Young Generation GC
	 */
	private static void runYoungGenerationTest() {
		System.out.println("\n=== Young Generation GC Test ===");

		for (int i = 0; i < 100; i++) {
			// Create temporary objects that will be collected quickly
			byte[][] tempArrays = new byte[50][];
			for (int j = 0; j < tempArrays.length; j++) {
				tempArrays[j] = new byte[ARRAY_SIZE];
			}

			// Simulate some work
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}

			// Objects go out of scope and become eligible for GC
			if (i % 20 == 0) {
				System.out.println("Young Gen test iteration: " + i);
			}
		}

		System.gc(); // Suggest garbage collection
		System.out.println("Young Generation test completed");
	}

	/**
	 * Creates long-lived objects that will promote to Old Generation
	 */
	private static void runOldGenerationTest() {
		System.out.println("\n=== Old Generation GC Test ===");

		// Create objects that will survive multiple GC cycles
		Object[] longLivedObjects = new Object[100];

		for (int i = 0; i < longLivedObjects.length; i++) {
			longLivedObjects[i] = new LongLivedObject(ARRAY_SIZE);

			// Also create some short-lived objects
			for (int j = 0; j < 10; j++) {
				byte[] temp = new byte[ARRAY_SIZE / 2];
				// temp goes out of scope immediately
			}

			if (i % 25 == 0) {
				System.out.println("Old Gen test progress: " + i + "/100");
			}
		}

		// Keep references to prevent collection
		memoryHolder = longLivedObjects;

		System.gc(); // This should trigger both young and old generation collection
		System.out.println("Old Generation test completed");
	}

	/**
	 * Simulates a memory leak by creating objects without releasing references
	 */
	private static void runMemoryLeakSimulation() {
		System.out.println("\n=== Memory Leak Simulation ===");

		java.util.List<byte[]> memoryLeak = new java.util.ArrayList<>();

		try {
			for (int i = 0; i < 200; i++) {
				// Keep adding objects without removing them
				memoryLeak.add(new byte[ARRAY_SIZE]);

				if (i % 50 == 0) {
					System.out.println("Leak simulation: " + i + " objects allocated");
					System.out.println("Free memory: " + 
							Runtime.getRuntime().freeMemory() / (1024 * 1024) + " MB");
				}
			}
		} catch (OutOfMemoryError e) {
			System.out.println("OutOfMemoryError caught - this demonstrates heap exhaustion");
		} finally {
			// Clean up to prevent actual memory leak
			memoryLeak.clear();
			System.gc();
		}

		System.out.println("Memory leak simulation completed");
	}

	/**
	 * Allocates large objects that go directly to Old Generation
	 */
	private static void runLargeObjectAllocation() {
		System.out.println("\n=== Large Object Allocation Test ===");

		// Large objects (> G1 region size) may go directly to old generation
		for (int i = 0; i < 5; i++) {
			byte[] largeArray = new byte[10 * ARRAY_SIZE]; // 10MB array

			// Process the array to ensure it's not optimized away
			largeArray[0] = (byte) i;
			largeArray[largeArray.length - 1] = (byte) i;

			System.out.println("Allocated large object " + (i + 1) + "/5 (10MB each)");

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}

		System.out.println("Large object allocation test completed");
	}

	/**
	 * Helper class to create objects that survive multiple GC cycles
	 */
	private static class LongLivedObject {
		private final byte[] data;
		private final long creationTime;

		public LongLivedObject(int size) {
			this.data = new byte[size];
			this.creationTime = System.currentTimeMillis();

			// Fill with some data to prevent optimization
			for (int i = 0; i < Math.min(1000, size); i++) {
				data[i] = (byte) (i % 256);
			}
		}

		public long getAge() {
			return System.currentTimeMillis() - creationTime;
		}
	}

}

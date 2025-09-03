package main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The intern() method for strings, particularly in languages like Java, 
 * is used to leverage the string pool, a special memory area where unique 
 * string literals are stored. The primary reasons for using intern() are: 
Memory Optimization
intern() ensures that only one copy of each distinct string value exists in memory. 
If a string with the same content is already in the string pool, intern() 
returns a reference to that existing string instead of creating a new one.
 This significantly reduces memory consumption, especially in applications 
 dealing with many identical string values (e.g., in data processing or logging).
Faster Comparisons:
When strings are interned, they can be compared for equality using the == operator, 
which compares object references, rather than the equals() method, which compares content. 
Reference comparison (==) is generally faster than content comparison (equals()), 
leading to performance improvements in scenarios where frequent string comparisons are performed.
Ensuring Uniqueness for Specific Use Cases:
In certain situations, like using strings as keys in a HashMap or for synchronization purposes
 (e.g., locking on a unique string identifier), intern()
  can be used to guarantee that all instances of a logically identical 
  string refer to the same object in memory. 
  This ensures consistent behavior and avoids potential issues that could arise 
  from multiple identical but distinct string objects.
 */
public class Main {

	public static void main(String[] args) {
//		String one = "hello";
//		String two = "hello";
//		
//		System.out.println(one.equals(two));
//		System.out.println(one == two);
//		
//		Integer i = 76;
//		String three = i.toString();
//		String four = "76";
//		
//		System.out.println(three.equals(four));
//		System.out.println(three == four);
//		
//		Integer i = 76;
//		String three = i.toString().intern();
//		String four = "76";
//		
//		System.out.println(three.equals(four));
//		System.out.println(three == four);
		
		Date start = new Date();
		
		List<String> strings = new ArrayList<String>();
		for (Integer i = 1; i < 10000000; i++) {
			String s = i.toString().intern();
			strings.add(s);	
		}
		
		Date end = new Date();
		System.out.println("Elapsed time was " + (end.getTime() - start.getTime()) + " ms.");
		try { Thread.sleep( Duration.ofSeconds( 5 ).toMillis() ); } catch ( InterruptedException e ) { e.printStackTrace(); }
		
	}

}

package main; 

import java.util.ArrayList;
import java.util.List;
/**
 * If we say we want our heap to be four gigabytes, the virtual machine won't.

When your application starts, immediately reserve four gigabytes of your computer's memory just for

your application.

But as your application runs and more and more of that memory is needed, it goes to the operating system

and requests it.

Well, in Java 11, when garbage collection runs, the virtual machine will potentially say, and it

has done in this instance, I've got lots of memory allocated to me that I really don't need.

I'll give some of it back to the operating system.

So the amount of memory that the virtual machine has taken from the operating system to run your application

if you're running Java eight or below, it can never go down. But in Java 11, it can go down.

every time your virtual machine needs to go to the operating system to say, Can I have a bit more memory, please, there'll be a slight impact

on performance.

And the way that we get around that we've already seen is by using a flag to tell the virtual machine

when you start request a particular value to be the initial heap size and even in Java 11, if you use

that flag, the virtual machine will never let the amount of memory you've reserved go below that initial

heap size.

Letting the virtual machine decide that rather than us trying to tell the virtual machine is always

going to be a good option.
 */
public class Main {
	
	public static void main(String[] args) throws InterruptedException 
	{
		Runtime runtime = Runtime.getRuntime();

		long availableBytes = runtime.freeMemory();
		System.out.println("Available memory at start: " + availableBytes / 1024 + "k");

		// let's create lots of objects....
		List<Customer> customers = new ArrayList<Customer>();
		
//		for (int i=0; i<1000000; i++)
//		{
//			customers.add(new Customer("John"));	
//		}
		
		for (int i=0; i<1000000; i++)
		{
			customers.add(new Customer("Customer "+i));	
		}
		
		availableBytes = runtime.freeMemory();
		System.out.println("Available memory  when customers created: " + availableBytes / 1024 + "k");
		
		customers = new ArrayList<>();
		
		availableBytes = runtime.freeMemory();
		System.out.println("Available memory  when customers no longer referenced: " + availableBytes / 1024 + "k");
		
		Thread.sleep(3000);

		availableBytes = runtime.freeMemory();
		System.out.println("Available memory  1 second later: " + availableBytes / 1024 + "k");

		System.gc();
		
		availableBytes = runtime.freeMemory();
		System.out.println("Available memory  after GC command: " + availableBytes / 1024 + "k");
	}

	
}

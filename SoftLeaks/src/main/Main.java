package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("heyy");
		List<Customer> customers = new ArrayList<Customer>();
		while(true) {
			Customer c = new Customer("Matt");
			customers.add(c);
			//System.out.println(customers.size());
			if (customers.size() > 10000) {
				for (int i = 0; i < 100; i++)
					customers.remove(0);
				System.out.println("if");
			}
			Thread.sleep(100);
		}
	}
}

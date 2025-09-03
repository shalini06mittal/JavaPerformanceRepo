package escape.references;

import java.util.Iterator;

public class MainIterable {

	public static void main(String[] args) {
		CustomerRecordsIterable records = new CustomerRecordsIterable();

		records.addCustomer(new Customer("John"));
		records.addCustomer(new Customer("Simon"));

//		for (Customer next : records.getCustomers().values())
//		{
//			System.out.println(next);
//		}
		
		for(Customer next:records) {
			System.out.println(next);
		}

		// it still is not full proof safe but one of the strategy
		Iterator<Customer> it = records.iterator();
		it.next();
		it.remove();
		
		System.out.println();
		for(Customer next:records) {
			System.out.println("name "+next);
		}

	}

}

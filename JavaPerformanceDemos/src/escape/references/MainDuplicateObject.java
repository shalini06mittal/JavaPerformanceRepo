package escape.references;

import java.util.Iterator;

public class MainDuplicateObject {

	public static void main(String[] args) {
		CustomerRecordsDuplicateObject records = new CustomerRecordsDuplicateObject();

		records.addCustomer(new Customer("John"));
		records.addCustomer(new Customer("Simon"));


		for (Customer next : records.getCustomers().values())
		{
			System.out.println(next);
		}
		
		Customer c1 = records.find("John");
		
		System.out.println("1: "+c1);
		
		c1.setName("Jane"); // this is an issue as I have changed the data
		
		for (Customer next : records.getCustomers().values())
		{
			System.out.println(next);
		}
		System.out.println();
		System.out.println("2 : "+records.find("John"));
		System.out.println("3 : "+records.find("Jane"));//

	}

}


/**
 * You store a customer with name "John" using the name as the key
You retrieve the customer using find("John") - this works because "John" is the key
You change the customer's name to "Jane" using setName("Jane")
The problem: The customer object is still stored under the key "John" 
in your internal data structure, even though the customer's name property is now "Jane"
 */
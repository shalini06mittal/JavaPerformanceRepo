package escape.references;
public class MainDuplicate {

	public static void main(String[] args) {
		CustomerRecordsDuplicateCollection records = new CustomerRecordsDuplicateCollection();

		records.addCustomer(new Customer("John"));
		records.addCustomer(new Customer("Simon"));

		Customer c1 = new Customer("a");
		Customer c2 = new Customer(c1);
		Customer c3 = c1;
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		
		c1.setName("B");
		System.out.println();
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		
//		for (Customer next : records.getCustomers().values())
//		{
//			System.out.println(next);
//		}
//
//		records.getCustomers().clear();
//
//		for (Customer next : records.getCustomers().values())
//		{
//			System.out.println(next);
//		}
	}

}

package escape.references;
public class MainImmutable {

	public static void main(String[] args) {
		CustomerRecordsImmutableCollection records = new CustomerRecordsImmutableCollection();

		records.addCustomer(new Customer("John"));
		records.addCustomer(new Customer("Simon"));

		for (Customer next : records.getCustomers().values())
		{
			System.out.println(next);
		}

		//records.getCustomers().clear();

		
	}

}

package escape.references;
public class MainDuplicate {

	public static void main(String[] args) {
		CustomerRecordsDuplicateCollection records = new CustomerRecordsDuplicateCollection();

		records.addCustomer(new Customer("John"));
		records.addCustomer(new Customer("Simon"));

		for (Customer next : records.getCustomers().values())
		{
			System.out.println(next);
		}

		records.getCustomers().clear();

		for (Customer next : records.getCustomers().values())
		{
			System.out.println(next);
		}
	}

}

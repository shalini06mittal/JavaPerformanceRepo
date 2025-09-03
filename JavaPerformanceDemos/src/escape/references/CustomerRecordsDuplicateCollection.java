package escape.references;
import java.util.HashMap;
import java.util.Map;

public class CustomerRecordsDuplicateCollection {
	private Map<String, Customer> records;
	
	public CustomerRecordsDuplicateCollection() {
		this.records = new HashMap<String, Customer>();
	}
	
	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}
		
	public Map<String, Customer> getCustomers() {
		return new HashMap<>(this.records);
	}
}

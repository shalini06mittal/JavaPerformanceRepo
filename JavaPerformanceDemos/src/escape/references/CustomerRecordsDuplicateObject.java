package escape.references;
import java.util.HashMap;
import java.util.Map;

public class CustomerRecordsDuplicateObject {
	private Map<String, Customer> records;
	
	public CustomerRecordsDuplicateObject() {
		this.records = new HashMap<String, Customer>();
	}
	
	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}
		
	public Map<String, Customer> getCustomers() {
		return Map.copyOf(this.records);
	}
	public Customer find(String name) {
		//return records.get(name);
		return new Customer(records.get(name));
	}
	
}

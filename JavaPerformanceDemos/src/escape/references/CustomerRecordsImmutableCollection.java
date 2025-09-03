package escape.references;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerRecordsImmutableCollection {
	private Map<String, Customer> records;
	
	public CustomerRecordsImmutableCollection() {
		this.records = new HashMap<String, Customer>();
	}
	
	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}
		
	public Map<String, Customer> getCustomers() {
		
		//return Collections.unmodifiableMap(records); // java <=9
		return Map.copyOf(records); // java >=10
	}
}

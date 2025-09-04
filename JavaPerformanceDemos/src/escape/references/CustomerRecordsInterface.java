package escape.references;
import java.util.HashMap;
import java.util.Map;

public class CustomerRecordsInterface {
	private Map<String, CustomerInterface> records;
	
	public CustomerRecordsInterface() {
		this.records = new HashMap<String, CustomerInterface>();
	}
	
	public void addCustomer(CustomerInterface c) {
		this.records.put(c.getName(), c);
	}
		
	public Map<String, CustomerInterface> getCustomers() {
		return Map.copyOf(this.records);
	}
	
	public ReadOnlyCustomer find(String name) {
		//return records.get(name);
		return new CustomerInterface(records.get(name));
	}
	
}

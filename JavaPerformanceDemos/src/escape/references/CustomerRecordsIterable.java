package escape.references;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomerRecordsIterable implements Iterable<Customer>{
	
	private Map<String, Customer> records;
	
	public CustomerRecordsIterable() {
		this.records = new HashMap<String, Customer>();
	}
	
	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}

	@Override
	public Iterator<Customer> iterator() {
		// TODO Auto-generated method stub
		return records.values().iterator();
	}
}

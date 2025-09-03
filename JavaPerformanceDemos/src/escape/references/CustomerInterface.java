package escape.references;

public class CustomerInterface implements ReadOnlyCustomer {
	private String name;

	@Override
	public String getName() {
		return name;
	}

	public CustomerInterface(String name) {
		this.name = name;
	}
	
	public CustomerInterface(ReadOnlyCustomer c) {
		this.name = c.getName();
	}

	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}

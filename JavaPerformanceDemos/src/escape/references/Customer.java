package escape.references;

public class Customer {
	private String name;


	public String getName() {
		return name;
	}

	public Customer(String name) {
		this.name = name;
	}
	// copy constructor - as solution for duplicating objects
	public Customer(Customer c) {
		this.name = c.getName();
	}
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}

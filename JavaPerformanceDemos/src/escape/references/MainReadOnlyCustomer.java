package escape.references;

/**
 * Here we are creating a brand new customer object and we are copying each of the parameters in

that customer object.

So actually, this is duplicating completely the object on the heap.

Now, again, it shouldn't be a huge performance impact because what we're duplicating is the references.

So in this instance that's going to be eight, maybe 16 bytes, that's all.

And of course it's going to be short lived.

When we learn about garbage collection, we'll see that short lived objects are good in terms of memory

and performance.

So this should be a low impact solution as well.
 */
public class MainReadOnlyCustomer {

	public static void main(String[] args) {
		CustomerRecordsInterface records = new CustomerRecordsInterface();

		records.addCustomer(new CustomerInterface("John"));
		records.addCustomer(new CustomerInterface("Simon"));
		
		//records.find("John").setName("Jane"); // will not work

		
		ReadOnlyCustomer c1 = records.find("John");
		
		CustomerInterface obj = (CustomerInterface)c1; // still it will not update the original customer
		
		obj.setName("Jane");
		
		for (ReadOnlyCustomer next : records.getCustomers().values())
		{
			System.out.println(next);
		}

	}

}

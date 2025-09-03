package jconsole;
import java.util.Date;

public class Main {
//92007 ms(with -client)
//93542 ms(w/o -client)
	public static void main(String[] args) throws InterruptedException {
		//Thread.sleep(20000);
		//System.out.println("Starting work");
		Date start  = new Date();
		PrimeNumbers primeNumbers = new PrimeNumbers();
		Integer max = Integer.parseInt(args[0]);
		primeNumbers.generateNumbers(max);
		Date end  = new Date();
		System.out.println("Elapsed time "+ (end.getTime() - start.getTime()) +" ms");
	}

}

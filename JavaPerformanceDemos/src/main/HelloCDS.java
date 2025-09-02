package main;
// java -verbose:class main.Hello

// Below command will dump all classes th
// java -Xshare:off -XX:+UnlockDiagnosticVMOptions -XX:DumpLoadedClassList=classes.lst main.HelloCDS

// produce an archive of our file
// java -Xshare:dump -XX:+UnlockDiagnosticVMOptions -XX:SharedClassListFile=classes.lst -XX:SharedArchiveFile=appcds.jsa -cp .

// To read from CDS - Class Data Sharing
//java -Xshare:on -XX:SharedArchiveFile=appcds.jsa -cp . main.HelloCDS

// To check logs
//java -Xshare:on -XX:SharedArchiveFile=appcds.jsa -cp . -Xlog:cds main.HelloCDS

// Mac users:
// /usr/bin/time java -Xshare:off -cp . main.HelloCDS [ to see the time without CDS
// Windows : measure-command
public class HelloCDS {

	public static void main(String[] args) {
		
		System.out.println("Hello Class Data Sharing demo");

	}

}

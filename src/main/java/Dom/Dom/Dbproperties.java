package Dom.Dom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Dbproperties {
	public static void main(String[] args) throws IOException {
		Properties p = new Properties();
		OutputStream os = new FileOutputStream("Dbproperties.txt");
		
		p.setProperty("url", "jdbc:postgresql://localhost:5432/Base1");
		p.setProperty("user", "postgres");
		p.setProperty("pass", "2422");
		
		p.store(os, null);
		System.out.println("done");
		
		
	}
}

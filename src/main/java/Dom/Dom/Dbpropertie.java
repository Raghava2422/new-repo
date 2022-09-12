package Dom.Dom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Dbpropertie {
	
	public static Properties readPropertiesFile(String fileName) throws IOException {
		
		FileInputStream fis = new FileInputStream(fileName);
		Properties prop = new Properties();
		
		prop.load(fis);

		fis.close();

		return prop;
	}

}

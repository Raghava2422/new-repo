package Dom.Dom;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
			IllegalArgumentException, IllegalAccessException, DOMException, SQLException, ClassNotFoundException {
// TODO Auto-generated method stub
		ArrayList<PersonalDetails> list = new ArrayList<PersonalDetails>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse("C:\\Dom\\src\\main\\java\\Dom\\Dom\\details.xml");
		
		NodeList detailsList = document.getElementsByTagName("record");
		
		for (int i = 0; i < detailsList.getLength(); i++) 
		{
			PersonalDetails pdetails = new PersonalDetails();
			
			Node node = detailsList.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element element = (Element) node;
				
				Class<?> class1 = PersonalDetails.class;
				for (Field field : class1.getDeclaredFields()) {

					field.setAccessible(true);

					String value = field.getName();
					
					NodeList nodelist = element.getElementsByTagName(value);
					Node node1 = nodelist.item(0);
					Element element1 = (Element) node1;
					if (field.getType() == int.class) {
						field.set(pdetails, Integer.parseInt(element1.getTextContent()));
					} else {
						field.set(pdetails, element1.getTextContent());
					}

				}

			}
			list.add(pdetails);
			System.out.println(pdetails.toString());

		}

//
//		String url = "jdbc:postgresql://localhost:5432/Base1";
//		String user = "postgres";
//		String pass = "2422";
		
		Dbpropertie dp = new Dbpropertie();
		Properties p = dp.readPropertiesFile("C:\\Dom\\src\\main\\java\\Dom\\Dom\\userDetails.properties");
		
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection(p.getProperty("url"), p.getProperty("user"), p.getProperty("pass"));
		PreparedStatement pstmt = conn.prepareStatement(
				"INSERT INTO employee(\n" + " id,first_name,last_name,email,gender)\n" + "values(?,?,?,?,?)\n");
		for (PersonalDetails pd : list) {
			int i = 1;
			Class<?> test = pd.getClass();
			Field[] field = test.getDeclaredFields();
			for (Field filed : field) {
				filed.setAccessible(true);
				if (filed.getType() == int.class) {
					pstmt.setInt(i, filed.getInt(pd));
					i++;
				} else {
					pstmt.setString(i, filed.get(pd).toString());
					i++;
				}
			}
			pstmt.execute();
		}

	}

}

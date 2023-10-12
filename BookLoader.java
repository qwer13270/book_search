import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;

public class BookLoader implements IBookLoader{

@Override
public List<IBook> loadBooks(String filepath) throws FileNotFoundException {
// Instantiate the Factory
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
List<IBook> bookList = new ArrayList<>();
try {
// parse XML file
DocumentBuilder db = dbf.newDocumentBuilder();

Document doc = db.parse(new File(filepath));

// get <Record>
NodeList list = doc.getElementsByTagName("Row");

for (int temp = 1; temp < list.getLength(); temp++) {
	 Node node = list.item(temp);

	 if (node.getNodeType() == Node.ELEMENT_NODE) {
	 Element element = (Element) node;

	 // get Record's attribute
	 String title = element.getAttribute("B");
	 String authors = element.getAttribute("C");
	 String avgRating = element.getAttribute("D");
	 String lanCode = element.getAttribute("G");
	 String numPages = element.getAttribute("H");
	 String totalRating = element.getAttribute("I");

	 double rating = Double.parseDouble(avgRating);
	 int pages = Integer.parseInt(numPages);
	 int ratingCounts = Integer.parseInt(totalRating);

	 Book book = new Book(title,authors,rating,pages,lanCode,ratingCounts);
	 bookList.add(book);
	 }
	 else
	 continue;

	 }
	 } catch (ParserConfigurationException | SAXException | IOException e) {
	 e.printStackTrace();
	 }
	 return bookList;
	 }

	 }

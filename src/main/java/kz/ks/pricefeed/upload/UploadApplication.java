package kz.ks.pricefeed.upload;

import kz.ks.pricefeed.upload.xmlparcing.Baeldung;
import kz.ks.pricefeed.upload.xmlparcing.BaeldungArticle;
import kz.ks.pricefeed.upload.xmlparcing.XmlReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class UploadApplication extends DefaultHandler implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}

	public void printNodeList(NodeList list, String indent) {
		var l = list.getLength();
		for (int i = 0; i < l; i++) {
			System.out.println(indent + list.item(i).getNodeName() + " " +
					((Node.TEXT_NODE == list.item(i).getNodeType()) ? list.item(i).getNodeValue() : ""));
			if (list.item(i).hasChildNodes()) {
				printNodeList(list.item(i).getChildNodes(), indent + "  ");
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new File("src/main/resources/example.xml"));
		doc.getDocumentElement().normalize();

		var root = doc.getChildNodes();

		System.out.println("==================================");
		printNodeList(root, "");
		System.out.println("==================================");

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		var xmlReader = new XmlReader();
		saxParser.parse("src/main/resources/example.xml", xmlReader);

		System.out.println(xmlReader.getWebsite());
	}

}
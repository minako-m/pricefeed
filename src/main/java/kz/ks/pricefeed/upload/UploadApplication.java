package kz.ks.pricefeed.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SpringBootApplication
public class UploadApplication {

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
}
package fr.yncrea.cir3.securitydemo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.yncrea.cir3.securitydemo.domain.Product;

@Controller
@RequestMapping("/xxe")
public class XxeController {
	@GetMapping("")
	public String upload() {
		return "xxe";
	}
	
	@PostMapping("")
	public String parse(Model model, @RequestParam("import") MultipartFile file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		// product list
		List<Product> products = new ArrayList<>();
		
		// load uploaded file
		Document document = builder.parse(file.getInputStream());
		
		// get xml root element
		Element root = document.getDocumentElement();
		
		NodeList nl = root.getElementsByTagName("product");
		for (int cpt = 0; cpt < nl.getLength(); cpt++) {
			Node node = nl.item(cpt);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element item = (Element) node;
				
				// create new product
				Product product = new Product();
				
				// copy data
				product.setName(item.getElementsByTagName("name").item(0).getTextContent());
				product.setDescription(item.getElementsByTagName("description").item(0).getTextContent());
				product.setPrice(new Long(item.getElementsByTagName("price").item(0).getTextContent()));
				
				products.add(product);
			}
		}
		
		model.addAttribute("products", products);
		
		return "xxe-parse";
	}
}

package com.czy.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UrlParse {
	public List<String> parse(String url){
		List<String> urls = new ArrayList<String>();
		DOMParser parse = new DOMParser();
		
		try {
			parse.setProperty("http://cyberneko.org/html/properties/default-encoding","utf-8"); 
			parse.setFeature("http://xml.org/sax/features/namespaces", false);
			
			parse.parse(url);
			
			Document document = parse.getDocument();
			String expression = "//A[@href]";// XPath表达式

			NodeList nodeList = XPathAPI.selectNodeList(document, expression);
			
			for(int i=0; i<nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				
				NamedNodeMap attributes = node.getAttributes();
				
				for(int j=0; j<attributes.getLength(); j++){
					Node n = attributes.getNamedItem("href");
					String u = n.getTextContent();
					
					if(u.startsWith("http://")){
						urls.add(u.trim());
						
						//System.out.println("爬取到： " + u.trim());
					}
					
					if(n.getTextContent() != null) break;
				}
				
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return urls;
	}
}

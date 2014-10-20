package com.czy.crawler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class TitleExtract {
	private String url = null;
	private String title = null;
	private int titleFrom = 0;

	public TitleExtract(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public int getTitleFrom() {
		return titleFrom;
	}

	public void extractTitle(String key, String value) {
		getText(key, value);
	}

	public void getFirst(String key, String value, String subTag) {
		getFirstSubText(key, value, subTag);
	}

	public void getLast(String key, String value, String subTag) {
		getLastSubText(key, value, subTag);
	}

	private Document getRootDocument() {
		//创建一个解析器  
		DOMParser parser = new DOMParser();
		Document document = null;
		//设置网页的默认编码  
		try {
			parser.setProperty("http://cyberneko.org/html/properties/default-encoding", "gb2312");
			parser.setFeature("http://xml.org/sax/features/namespaces", false);

			//解析HTML文件  
			parser.parse(url);
			//获取解析后的DOM树  
			document = parser.getDocument();

		} catch (SAXNotRecognizedException e) {
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			System.out.println(toHtml(document));
//		} catch (TransformerConfigurationException e) {
//			e.printStackTrace();
//		} catch (TransformerFactoryConfigurationError e) {
//			e.printStackTrace();
//		}

		return document;
	}

	public void rr() {
		getRootDocument();
	}

	public void extractTitle() {
		//获取URL对应的正则表达式
		//String urlRegKey = "";//Config.getMatchUrlReg(url);
		//根据正则表达式作为key，读取配置文件
		String value = "";// Config.getUrlRegConfig().getProperty(urlRegKey);

		String[] arr = value.split("+");
		if (arr.length == 2) {
			getText(arr[0], arr[1]);
		} else if (arr.length == 3) {
			if (arr[2].substring(0, 1).equalsIgnoreCase("F")) {
				getFirstSubText(arr[0], arr[1], arr[2].substring(1));
			} else {
				getLastSubText(arr[0], arr[1], arr[2]);
			}
		}
	}

	/**
	 * 节点转成对应的字符串
	 */
	private String toHtml(Node node) throws TransformerConfigurationException, TransformerFactoryConfigurationError {
		if (node == null)
			return null;
		Transformer transformer = TransformerFactory.newInstance().newTransformer();

		if (transformer != null) {
			try {
				StringWriter sw = new StringWriter();
				transformer.transform(new DOMSource(node), new StreamResult(sw));
				return sw.toString();
			} catch (TransformerException te) {
				throw new RuntimeException(te.getMessage());

			}
		}
		return null;
	}

	/**
	 * 字符串转dom
	 */
	private Document string2Document(String xmlStr) {

		if(xmlStr == null) return null;
		
		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;

		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	/**直接提取标签内文本*/
	private Node getText(String tag, String attribute) {
		Document document = getRootDocument();
		String expression = "//*[@" + tag + "='" + attribute + "']";//XPath表达式

		if(document == null) return null;
		
		Node node = null;
		try {
			node = XPathAPI.selectSingleNode(document, expression);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		if (node != null) {
//			try {
//				System.out.println(toHtml(node));
//			} catch (TransformerConfigurationException e) {
//				e.printStackTrace();
//			} catch (TransformerFactoryConfigurationError e) {
//				e.printStackTrace();
//			}
			setPosition(getNodeContent(node));

			this.title = node.getTextContent();
		}

		return node;
	}

	private void setPosition(String content) {
		Scanner canner;
		try {
			String html = toHtml(getRootDocument());
			canner = new Scanner(html);

			int position = 0;
			while (canner.hasNextLine() && canner.nextLine().indexOf(content) < 0) {
				position++;
			}
			titleFrom = position + 1;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}

	}

	/**提取第一个子标签内文本*/
	private void getLastSubText(String tag, String attribute, String subTag) {
		NodeList nodeList = getParentNode(tag, attribute, subTag);

		if (nodeList != null && nodeList.getLength() > 0) {
			Node titleNode = nodeList.item(nodeList.getLength() - 1);

			setPosition(getNodeContent(titleNode));
			this.title = titleNode.getTextContent();
		}

	}

	private String getNodeContent(Node titleNode) {
		String content = null;
		try {
			content = toHtml(titleNode);
			content = content.substring(content.indexOf(">") + 1).trim();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}

		return content;
	}

	/**提取最后一个子标签内文本*/
	private void getFirstSubText(String tag, String attribute, String subTag) {
		NodeList nodeList = getParentNode(tag, attribute, subTag);

		if (nodeList != null && nodeList.getLength() > 0) {
			Node titleNode = nodeList.item(0);
			setPosition(getNodeContent(titleNode));

			this.title = titleNode.getTextContent();
		}
	}

	/**提取纯文本最近一个节点*/
	private NodeList getParentNode(String tag, String attribute, String subTag) {
		String content = null;
		try {
			content = toHtml(getText(tag, attribute));

		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerFactoryConfigurationError e1) {
			e1.printStackTrace();
		}

		Document subDoc = string2Document(content);
		
		if(subDoc == null) return null;
		
		String expression = "//" + subTag.toUpperCase();// XPath表达式

		NodeList nodeList = null;
		try {
			nodeList = XPathAPI.selectNodeList(subDoc, expression);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
}

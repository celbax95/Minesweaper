package fr.xmlmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.datafilesmanager.XMLManagerBackend;

// TODO doc
public class XMLManagerDOM implements XMLManagerBackend {

	/**
	 * static Singleton instance.
	 */
	private static volatile XMLManagerDOM instance;

	/**
	 * Return a singleton instance of XMLReader.
	 */
	public static XMLManagerDOM getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (XMLManagerDOM.class) {
				if (instance == null) {
					instance = new XMLManagerDOM();
				}
			}
		}
		return instance;
	}

	// Utilise pour recuperer le fichier xml
	private DocumentBuilder builder;

	// Utilise pour la sauvegarde des fichiers xml
	private Transformer transformer;

	// Utilise pour la recherche dans le fichier xml
	private XPath xpath;

	/**
	 * Private constructor for singleton.
	 */
	public XMLManagerDOM() {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			this.builder = dFactory.newDocumentBuilder();

			TransformerFactory tFactory = TransformerFactory.newInstance();
			tFactory.setAttribute("indent-number", 2);
			this.transformer = tFactory.newTransformer();
			this.transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			this.transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			this.transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			this.transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.xpath = XPathFactory.newInstance().newXPath();
	}

	public void createParam(Object doc, String paramName, Object value) {
		assert value instanceof Integer || value instanceof Double || value instanceof Boolean
				|| value instanceof String;

		Document document = (Document) doc;

		Element newParam = document.createElement("param");

		newParam.setAttribute("name", paramName);
		newParam.setAttribute("type", value.getClass().getSimpleName().toLowerCase());
		newParam.setAttribute("value", value.toString());

		((Node) this.getRoot(doc)).appendChild(newParam);

	}

	/**
	 * Cast dynamiquement la chaine 'value' dans le type 'type'
	 *
	 * @param value : la chaine a caster
	 * @param type  : le type dans lequel il faut caster
	 * @return : value caste dans le type 'type'
	 */
	private Object dynamicCast(String value, String type) {
		switch (type.toLowerCase()) {
		case "integer":
		case "long":
			return Integer.valueOf(value);
		case "double":
		case "float":
			return Double.valueOf(value);
		case "boolean":
			return Boolean.valueOf(value);
		case "string":
			return value;
		}
		return null;
	}

	@Override
	public String getAttribute(Object doc, String attributeName) {
		return ((Element) doc).getAttribute(attributeName);
	}

	@Override
	public Document getDocument(String filePath) throws FileNotFoundException {
		File fileXML = new File(filePath);

		if (!fileXML.exists())
			throw new FileNotFoundException();

		try {
			Document xml = this.builder.parse(fileXML);
			xml.getDocumentElement().normalize();
			return xml;
		} catch (final Exception e) {
		}
		return null;
	}

	@Override
	public Object getNode(Object doc, String name) {
		return this.getNode(doc, name, 1);
	}

	@Override
	public Object getNode(Object doc, String nodeName, int nb) {
		try {
			return this.xpath.evaluate("./" + nodeName + "[" + nb + "]", ((Document) doc).getDocumentElement(),
					XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getNode(Object doc, String nodeName, String attribute, String value) {
		if (nodeName == null) {
			nodeName = "*";
		}

		try {
			return this.xpath.evaluate("./" + nodeName + "[@" + attribute + " = \'" + value + "\']",
					((Document) doc).getDocumentElement(), XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object[] getNodes(Object doc, String nodeName) {

		NodeList nl = null;
		try {
			nl = (NodeList) this.xpath.evaluate("./" + nodeName, ((Document) doc).getDocumentElement(),
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
		}

		return this.nodeListToObjectcArray(nl);
	}

	@Override
	public Object getParam(Object doc, String paramName, Object def) {
		Element e = null;
		try {
			e = (Element) this.xpath.evaluate("./param[@name = '" + paramName + "']",
					((Document) doc).getDocumentElement(), XPathConstants.NODE);
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}

		if (e == null)
			return def;

		return this.dynamicCast(e.getAttribute("value"), e.getAttribute("type"));
	}

	@Override
	public Object[] getParams(Object doc) {

		NodeList nl = null;

		try {
			nl = (NodeList) this.xpath.evaluate("./param", ((Document) doc).getDocumentElement(),
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
		}

		Object[] oa = new Object[nl.getLength()];

		for (int i = 0, c = nl.getLength(); i < c; i++) {
			Element e = (Element) nl.item(i);

			oa[i] = this.dynamicCast(e.getAttribute("value"), e.getAttribute("type"));
		}

		return oa;
	}

	@Override
	public Object getRoot(Object document) {
		return ((Document) document).getDocumentElement();
	}

	/**
	 * Recupere l'objet XPath
	 *
	 * @return l'objet XPath
	 */
	public XPath getXpath() {
		return this.xpath;
	}

	/**
	 * Transform une NodeList en tableau d'objet
	 *
	 * @param nodeList : liste de noeud
	 * @return : le tableau de noeuds
	 */
	private Object[] nodeListToObjectcArray(NodeList nodeList) {
		Object[] oa = new Object[nodeList.getLength()];

		for (int i = 0, c = nodeList.getLength(); i < c; i++) {
			oa[i] = nodeList.item(i);
		}

		return oa;
	}

	private void removeWhiteSpaces(Object doc) {
		NodeList nl;
		try {
			nl = (NodeList) this.xpath.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);

			for (int i = 0; i < nl.getLength(); ++i) {
				Node node = nl.item(i);
				node.getParentNode().removeChild(node);
			}
		} catch (Exception e2) {
			// e2.printStackTrace();
		}
	}

	@Override
	public void saveFile(Object doc) {

		Document document = (Document) doc;

		this.removeWhiteSpaces(doc);

		DOMSource source = new DOMSource(((Document) doc).getDocumentElement());
		StreamResult result = null;
		try {
			result = new StreamResult(new File(URLDecoder.decode(document.getBaseURI(), "UTF-8").substring(6)));
		} catch (UnsupportedEncodingException e1) {
			// e1.printStackTrace();
		}
		try {
			this.transformer.transform(source, result);
		} catch (TransformerException e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void setParam(Object doc, String paramName, Object newValue) {

		assert newValue instanceof Integer || newValue instanceof Double || newValue instanceof Boolean
				|| newValue instanceof String;

		Node existingNode = (Node) this.getNode(doc, "param", "name", paramName);

		if (existingNode != null) {
			existingNode.getAttributes().getNamedItem("value").setTextContent(String.valueOf(newValue));
		} else {
			this.createParam(doc, paramName, newValue);
		}
	}
}

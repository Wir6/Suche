package wir6.rssImport.tools;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class NerSucher
{
	private final static String STD_CLASSIFIER = "english.all.3class.distsim.crf.ser.gz";
	private AbstractSequenceClassifier<CoreLabel> classifier;
	private ArrayList<String> pers;
	private ArrayList<String> orte;
	private ArrayList<String> orga;

	public NerSucher()
	{
		String serializedClassifier = STD_CLASSIFIER;
		classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	}

	public NerSucher(String classifierAbsolutePath)
	{
		String serializedClassifier = classifierAbsolutePath;
		classifier = CRFClassifier
				.getClassifierNoExceptions(serializedClassifier);
	}

	public Map<String, ArrayList<String>> nerParse(String text)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); // validieren beim Parsen
		dbf.setValidating(true); // validieren beim Parsen
		DocumentBuilder db;
		String nerWithInlineXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<text>\n"
				+ classifier.classifyWithInlineXML(toWellFormedXml(text))
				+ "</text>";
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		pers = new ArrayList<String>();
		orga = new ArrayList<String>();
		orte = new ArrayList<String>();

		try
		{
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new MyErrorHandler()); // validieren beim Parsen
			Document doc = db.parse(new InputSource(new StringReader(
					nerWithInlineXml)));
			NodeList nl = doc.getElementsByTagName("text");
			for (int i = 0; i < nl.getLength(); i++)
			{
				Element element = (Element) nl.item(i);
				for (int j = 0; j < element.getElementsByTagName("PERSON")
						.getLength(); j++)
				{
					Element person = (Element) element.getElementsByTagName(
							"PERSON").item(j);
					// System.out.println("PERSON:         " +
					// person.getTextContent().replaceAll("\n", " "));
					pers.add(person.getTextContent().replaceAll("\n", " "));
				}
				for (int j = 0; j < element
						.getElementsByTagName("ORGANIZATION").getLength(); j++)
				{
					Element orga = (Element) element.getElementsByTagName(
							"ORGANIZATION").item(j);
					// System.out.println("ORGANISATION:   " +
					// orga.getTextContent().replaceAll("\n", " "));
					this.orga.add(orga.getTextContent().replaceAll("\n", " "));
				}
				for (int j = 0; j < element.getElementsByTagName("LOCATION")
						.getLength(); j++)
				{
					Element ort = (Element) element.getElementsByTagName(
							"LOCATION").item(j);
					// System.out.println("ORT:            " +
					// ort.getTextContent().replaceAll("\n", " "));
					orte.add(ort.getTextContent().replaceAll("\n", " "));
				}
				// .replaceAll("\n", " ");
				map.put("PERSON", pers);
				map.put("ORGANIZATION", orga);
				map.put("LOCATION", orte);
			}
		} catch (ParserConfigurationException | SAXException | IOException e)
		{
			// System.out.println(nerWithInlineXml + "\n-------------\n");
			e.printStackTrace();
		}
		return map;
	}

	private String toWellFormedXml(String s)
	{
		String well = s.replaceAll("&amp;", "&");
		well = well.replaceAll("&quot;", "\"");
		well = well.replaceAll("&apos;", "'");
		well = well.replaceAll("&lt;", "<");
		well = well.replaceAll("&gt;", ">");
		
		well = well.replaceAll("&", "&amp;");
		well = well.replaceAll("\"", "&quot;");
		well = well.replaceAll("'", "&apos;");
		well = well.replaceAll("<", "&lt;");
		well = well.replaceAll(">", "&gt;");
		return well;
	}
}

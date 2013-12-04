package wir6.rssImport.importArticle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import wir6.rssImport.database.Database;
import wir6.rssImport.server.RssReader;
import wir6.rssImport.tools.NerSucher;

public class ArticleParser extends DefaultHandler implements RssReader
{
	private Article current;
	private ArticleManager aM;
	private StringBuilder sb;
	Database db;
	NerSucher ner;

	// Konstruktor
	public ArticleParser()
	{
		db = new Database();
		System.out.println(db.testCon());
		ner = new NerSucher();
	}

	// Methoden
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		sb = new StringBuilder();
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		if (current != null && qName.equals("item"))
		{
			aM.add(current);
			current = null;
		} else if (qName.equals("url"))
		{
			current.setUrl(sb.toString());
		} else if (qName.equals("title"))
		{
			current.setTitle(sb.toString());
		} else if (qName.equals("link"))
		{
			current.setLink(sb.toString());
		} else if (qName.equals("description"))
		{
			current.setDescription(sb.toString());
		} else if (qName.equals("ExtractedText"))
		{
			current.setDocText(sb.toString());
		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		String sWert = new String(ch, start, length);
		sb.append(sWert);

	}

	public ArticleManager parse(String path)
	{
		aM = new ArticleManager();
		File file = new File(path);
		SAXParserFactory spF = SAXParserFactory.newInstance();
		SAXParser sP;
		try
		{
			sP = spF.newSAXParser();

			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			current = new Article();
			current.sucheWerte(path);
			sP.parse(is, this);
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return aM;
	}

	@Override
	public void newNews(String absoluteNewsPath)
	{
		if (db.testCon())
		{
			{
				ArticleManager man = parse(absoluteNewsPath);
				try
				{
					// db.delAll();
					if (!(man.getArticle(0).getDocText().equals(null) || man
							.getArticle(0).getDocText().equals("")))
					{
						int hID = db.addHerausgeber(man.getArticle(0)
								.getHerausgeber(), man.getArticle(0).getUrl());
						int aID = db.addArticle(man.getArticle(0), hID);

						Map<String, ArrayList<String>> tags = ner.nerParse(man
								.getArticle(0).getDocText());
						for (String tag : tags.keySet())
						{
							int tID = db.addTag(tag);
							for (String eintrag : tags.get(tag))
							{
								int eID = db.addEintrag(eintrag, tID);
								db.addEintragArticle(eID, aID);
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} else
			System.out.println("Keine Verbindung zur DB!");
	}
}

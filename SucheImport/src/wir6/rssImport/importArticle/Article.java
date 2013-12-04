package wir6.rssImport.importArticle;

import java.util.Date;
import java.util.GregorianCalendar;

public class Article
{
	private String title;
	private String description;
	private String link;
	private String docText;
	private String herausgeber;
	private Date datum;
	private String kategorie;
	private String url;

	// Getter/Setter
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getDocText()
	{
		return docText;
	}

	public void setDocText(String docText)
	{
		this.docText = docText;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getHerausgeber()
	{
		return herausgeber;
	}

	public void setHerausgeber(String herausgeber)
	{
		this.herausgeber = herausgeber;
	}

	public Date getDatum()
	{
		return datum;
	}

	public void setDatum(Date datum)
	{
		this.datum = datum;
	}

	public String getKategorie()
	{
		return kategorie;
	}

	public void setKategorie(String kategorie)
	{
		this.kategorie = kategorie;
	}

	// Methode
	public void sucheWerte(String sPfad)
	{
		String[] arr = sPfad.split("/");
		int maxIdx = arr.length - 1;

		if (!arr[maxIdx - 1].equals("timeless"))
		{
			String tag = arr[maxIdx - 1]; // Tag ist zweites Element von hinten
			String monat = arr[maxIdx - 2]; // Monat ist drittes Element von
											// hinten
			String jahr = arr[maxIdx - 3]; // Jahr ist viertes Element von
											// hinten
			herausgeber = arr[maxIdx - 4]; // Quelle ist f�nftes Element von
											// hinten
			kategorie = arr[maxIdx - 5]; // Kategorie ist sechstes Element von
											// hinten

			int dd = Integer.parseInt(tag.substring(1));
			int mm = Integer.parseInt(monat.substring(1));
			int yy = Integer.parseInt(jahr.substring(1));

			GregorianCalendar cal = new GregorianCalendar(yy, mm - 1, dd);
			datum = cal.getTime();
		} else
		{
			herausgeber = arr[maxIdx - 2]; // Quelle ist f�nftes Element von
											// hinten
			kategorie = arr[maxIdx - 3]; // Kategorie ist sechstes Element von
											// hinten
			datum = null;
		}

	}

	// ToString
	@Override
	public String toString()
	{
		return "Article [title=" + title + ", description=" + description
				+ ", link=" + link + ", datum=" + datum + ", url=" + url + "]";
	}

}

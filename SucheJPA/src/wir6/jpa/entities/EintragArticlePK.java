package wir6.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eintrag_article database table.
 * 
 */
@Embeddable
public class EintragArticlePK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int eintragid;

	private int articleid;

	public EintragArticlePK()
	{
	}

	public int getEintragid()
	{
		return this.eintragid;
	}

	public void setEintragid(int eintragid)
	{
		this.eintragid = eintragid;
	}

	public int getArticleid()
	{
		return this.articleid;
	}

	public void setArticleid(int articleid)
	{
		this.articleid = articleid;
	}

	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (!(other instanceof EintragArticlePK))
		{
			return false;
		}
		EintragArticlePK castOther = (EintragArticlePK) other;
		return (this.eintragid == castOther.eintragid)
				&& (this.articleid == castOther.articleid);

	}

	public int hashCode()
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.eintragid;
		hash = hash * prime + this.articleid;

		return hash;
	}
}
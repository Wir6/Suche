package wir6.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eintrag_article database table.
 * 
 */
@Entity
@Table(name = "eintrag_article")
public class EintragArticle implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EintragArticlePK id;

	private int hits;

	// bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumn(name = "articleid")
	private Article article;

	// bi-directional many-to-one association to Eintrag
	@ManyToOne
	@JoinColumn(name = "eintragid")
	private Eintrag eintrag;

	public EintragArticle()
	{
	}

	public EintragArticlePK getId()
	{
		return this.id;
	}

	public void setId(EintragArticlePK id)
	{
		this.id = id;
	}

	public int getHits()
	{
		return this.hits;
	}

	public void setHits(int hits)
	{
		this.hits = hits;
	}

	public Article getArticle()
	{
		return this.article;
	}

	public void setArticle(Article article)
	{
		this.article = article;
	}

	public Eintrag getEintrag()
	{
		return this.eintrag;
	}

	public void setEintrag(Eintrag eintrag)
	{
		this.eintrag = eintrag;
	}

}
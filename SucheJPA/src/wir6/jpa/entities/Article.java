package wir6.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@Table(name="article")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

    @Lob()
	private String category;

    @Temporal( TemporalType.DATE)
	private Date datum;

    @Lob()
	private String description;

    @Lob()
	private String doctext;

    @Lob()
	private String title;

    @Lob()
	private String url;

	//bi-directional many-to-one association to Herausgeber
    @ManyToOne
	@JoinColumn(name="herausgeberid")
	private Herausgeber herausgeber;

	//bi-directional many-to-one association to EintragArticle
	@OneToMany(mappedBy="article")
	private List<EintragArticle> eintragArticles;

    public Article() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDoctext() {
		return this.doctext;
	}

	public void setDoctext(String doctext) {
		this.doctext = doctext;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Herausgeber getHerausgeber() {
		return this.herausgeber;
	}

	public void setHerausgeber(Herausgeber herausgeber) {
		this.herausgeber = herausgeber;
	}
	
	public List<EintragArticle> getEintragArticles() {
		return this.eintragArticles;
	}

	public void setEintragArticles(List<EintragArticle> eintragArticles) {
		this.eintragArticles = eintragArticles;
	}
	
}
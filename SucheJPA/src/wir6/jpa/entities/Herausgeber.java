package wir6.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the herausgeber database table.
 * 
 */
@Entity
@Table(name="herausgeber")
public class Herausgeber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

    @Lob()
	private String logo;

    @Lob()
	private String name;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="herausgeber")
	private List<Article> articles;

    public Herausgeber() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
}
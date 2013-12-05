package wir6.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eintrag database table.
 * 
 */
@Entity
@Table(name="eintrag")
public class Eintrag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

    @Lob()
	private String name;

	//bi-directional many-to-one association to Tag
    @ManyToOne
	@JoinColumn(name="tagid")
	private Tag tag;

	//bi-directional many-to-one association to EintragArticle
	@OneToMany(mappedBy="eintrag")
	private List<EintragArticle> eintragArticles;

    public Eintrag() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public List<EintragArticle> getEintragArticles() {
		return this.eintragArticles;
	}

	public void setEintragArticles(List<EintragArticle> eintragArticles) {
		this.eintragArticles = eintragArticles;
	}
	
}
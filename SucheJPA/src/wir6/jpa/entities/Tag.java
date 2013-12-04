package wir6.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tag database table.
 * 
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob()
	private String name;

	// bi-directional many-to-one association to Eintrag
	@OneToMany(mappedBy = "tag")
	private List<Eintrag> eintrags;

	public Tag()
	{
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Eintrag> getEintrags()
	{
		return this.eintrags;
	}

	public void setEintrags(List<Eintrag> eintrags)
	{
		this.eintrags = eintrags;
	}

}
package wir6.jpa.service;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import wir6.jpa.entities.Article;
import wir6.jpa.entities.Eintrag;
import wir6.jpa.entities.EintragArticle;
import wir6.jpa.entities.Tag;

@Stateless
public class SuchServicePersonen
{
	private EntityManagerFactory factory;

	private EntityManagerFactory getFactory()
	{
		if (factory == null)
		{
			factory = Persistence.createEntityManagerFactory("SucheJPA");
		}
		return factory;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllArticles()
	{
		EntityManager em = getFactory().createEntityManager();
//		Logger.getLogger(SuchServicePersonen.class.getName()).log(Level.INFO, "Methode getAllArticles wurde gerufen...");
		List<Article> aListe = em.createQuery("select art from Article art").getResultList();
		return aListe;
	}
	
	public List<Article> getArticlesFulltextSearch(String begriff)
	{
		EntityManager em = getFactory().createEntityManager();
//		Logger.getLogger(SuchServicePersonen.class.getName()).log(Level.INFO, "Methode getArticlesFulltextSearch wurde gerufen...");
		
		TypedQuery<Article> query = em.createQuery( "SELECT art FROM Article art WHERE SQL('MATCH(title, doctext) AGAINST( ? )', :searchTerm)", Article.class);
		query.setParameter("searchTerm", begriff);
		List<Article> aListe = query.getResultList();
		System.out.println("Repository Test3");
		return aListe;
	}
	
	public List<Article> getArticles4Tag(String tagName, String begriff){
		
		EntityManager em = getFactory().createEntityManager();
//		Logger.getLogger(SuchServicePersonen.class.getName()).log(Level.INFO, "Methode getArticles4Tag wurde gerufen...");
		
		// Tag-ID ermitteln
		TypedQuery<Tag> query = em.createQuery("Select t from " + Tag.class.getSimpleName() + " t where t.name = :name", Tag.class);
		query.setParameter("name", tagName);
		Tag helpTag = query.getSingleResult();
		
		//Eintrag ermitteln
		TypedQuery<Eintrag> query2 = em.createQuery("Select e from " + Eintrag.class.getSimpleName() + 
													" e where e.name = :name and e.id = :tagID", Eintrag.class);
		query2.setParameter("name", begriff);
		query2.setParameter("tagID", helpTag.getId());
		Eintrag helpEintrag = query2.getSingleResult();
		
		// Eintrag_Article ermitteln
		TypedQuery<EintragArticle> query3 = em.createQuery("Select ea from " + EintragArticle.class.getSimpleName() + 
				" ea where ea.eintrag = :eintragID", EintragArticle.class);
		query3.setParameter("eintragID", helpEintrag);
		
		List<EintragArticle> helpEintragArticle = query3.getResultList();
		
		// Alle Artikel in eine Array-List stellen
		ArrayList<Article> aArtikel = new ArrayList<Article>();
		for(EintragArticle ea: helpEintragArticle){
			aArtikel.add(ea.getArticle());
		}
		
		return aArtikel;
	}
}
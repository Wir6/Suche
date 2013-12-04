package wir6.rssImport.importArticle;

import java.util.LinkedList;
import java.util.List;

public class ArticleManager
{
	private List<Article> article;

	// Konstruktor
	public ArticleManager()
	{
		article = new LinkedList<Article>();
	}

	// Team einf�gen
	public void add(Article article)
	{
		this.article.add(article);
	}

	// Team ausgeben
	public Article getArticle(int idx)
	{
		return idx > -1 && idx < getArticleCount() ? article.get(idx) : null;
	}

	// Gr��er der Liste ausgeben
	public int getArticleCount()
	{
		return article != null ? article.size() : 0;
	}

	// toString Methode
	public String toString()
	{
		String s = "";
		for (Article art : article)
		{
			s += art + "\n";
		}
		return s;
	}
}

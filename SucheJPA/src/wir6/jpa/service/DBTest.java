package wir6.jpa.service;

import java.util.List;

import wir6.jpa.entities.Article;

public class DBTest
{
	public static void main(String[] args)
	{
		SuchServicePersonen pss = new SuchServicePersonen();

		List<Article> articles = pss.getArticlesFulltextSearch("Bundeskanzlerin");
		for (Article myArticle : articles)
		{
			System.out.println(myArticle.getTitle());
			System.out.println(myArticle.getDoctext());
		}

		articles = pss.getArticles4Tag("Person", "Angela Merkel");
		for (Article myArticle : articles)
		{
			System.out.println(myArticle.getTitle());
			System.out.println(myArticle.getDoctext());
		}
	}
}
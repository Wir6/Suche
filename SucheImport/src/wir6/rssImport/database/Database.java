package wir6.rssImport.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import wir6.rssImport.importArticle.Article;

public class Database
{
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/praprodb";
	private static final String USER = "root";
	private static final String PASS = "root";

	// leerer Konstruktor
	public Database()
	{

	}

	// Verbindung testen
	public boolean testCon()
	{
		try
		{
			Driver driver = (Driver) Class.forName(DRIVER).newInstance();
			DriverManager.registerDriver(driver);
			DriverManager.getConnection(URL, USER, PASS);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	// Verbindung aufbauen
	public Connection getCon() throws Exception
	{
		Driver driver = (Driver) Class.forName(DRIVER).newInstance();
		DriverManager.registerDriver(driver);

		return DriverManager.getConnection(URL, USER, PASS);
	}

	// Article einf�gen
	public int addArticle(Article article, int hID)
	{
		try
		{
			Connection con = getCon();
			Statement stmt = con.createStatement();
			PreparedStatement pStm;
			// Article schreiben
			String sql = "INSERT INTO article(herausgeberid, title, description, url, doctext, datum, category) VALUES(?, ?, ?, ?, ?, ?, ?);";
			pStm = con.prepareStatement(sql);
			pStm.setInt(1, hID);
			pStm.setString(2, article.getTitle());
			pStm.setString(3, article.getDescription());
			pStm.setString(4, article.getLink());
			pStm.setString(5, article.getDocText());
			pStm.setDate(6, new java.sql.Date(article.getDatum().getTime()));
			pStm.setString(7, article.getKategorie());
			pStm.executeUpdate();
			stmt.close();
			con.close();
		} catch (Exception ea)
		{
			ea.printStackTrace();
			return -1;
		}
		return getMaxId();
	}

	// Herausgeber einf�gen
	public int addHerausgeber(String herausgeber, String logo)
	{
		int hID = -1;
		try
		{
			Connection con = getCon();
			PreparedStatement pStm;
			// Herausgeber suchen und wenn nicht vorhanden schreiben
			String sql = "SELECT id FROM herausgeber WHERE name = ?";
			pStm = con.prepareStatement(sql);
			pStm.setString(1, herausgeber);
			ResultSet rs = pStm.executeQuery();
			if (rs.next())
				hID = rs.getInt("id");
			else
			{
				// Tag schreiben
				sql = "INSERT INTO herausgeber(name, logo) VALUES(?, ?);";
				pStm = con.prepareStatement(sql);
				pStm.setString(1, herausgeber);
				pStm.setString(2, logo);
				pStm.executeUpdate();
				// Tag suchen
				sql = "SELECT id FROM herausgeber WHERE name = ?";
				pStm = con.prepareStatement(sql);
				pStm.setString(1, herausgeber);
				rs = pStm.executeQuery();
				if (rs.next())
					hID = rs.getInt("id");
				else
					hID = -1;
			}
			pStm.close();
			con.close();
		} catch (Exception e)
		{
			hID = -1;
		}
		return hID;
	}

	// Tag hinzuf�gen
	public int addTag(String tag)
	{
		int tagID = -1;
		try
		{
			Connection con = getCon();
			PreparedStatement pStm;
			// Tag suchen und wenn nicht vorhanden schreiben
			String sql = "SELECT id FROM tag WHERE name = ?";
			pStm = con.prepareStatement(sql);
			pStm.setString(1, tag);
			ResultSet rs = pStm.executeQuery();
			if (rs.next())
				tagID = rs.getInt("id");
			else
			{
				// Tag schreiben
				sql = "INSERT INTO tag(name) VALUES(?);";
				pStm = con.prepareStatement(sql);
				pStm.setString(1, tag);
				pStm.executeUpdate();
				// Tag suchen
				sql = "SELECT id FROM tag WHERE name = ?";
				pStm = con.prepareStatement(sql);
				pStm.setString(1, tag);
				rs = pStm.executeQuery();
				if (rs.next())
					tagID = rs.getInt("id");
				else
					tagID = -1;
			}
			pStm.close();
			con.close();
		} catch (Exception e)
		{
			tagID = -1;
		}
		return tagID;
	}

	// Eintrag schreiben
	public int addEintrag(String eintrag, int tagID)
	{
		int eID = -1;
		try
		{
			Connection con = getCon();
			PreparedStatement pStm;
			String sql = "select id FROM eintrag WHERE " + "tagid = ? "
					+ "and name = ?";
			pStm = con.prepareStatement(sql);
			pStm.setInt(1, tagID);
			pStm.setString(2, eintrag);
			ResultSet rs = pStm.executeQuery();
			if (rs.next())
			{
				eID = rs.getInt("id");
			} else
			{
				// Eintrag schreiben
				sql = "INSERT INTO eintrag(tagid, name) VALUES(?, ?);";
				pStm = con.prepareStatement(sql);
				pStm.setInt(1, tagID);
				pStm.setString(2, eintrag);
				pStm.executeUpdate();
				// Eintrag suchen
				sql = "select id FROM eintrag WHERE " + "tagid = ? "
						+ "and name = ?";
				pStm = con.prepareStatement(sql);
				pStm.setInt(1, tagID);
				pStm.setString(2, eintrag);
				rs = pStm.executeQuery();
				if (rs.next())
					eID = rs.getInt("id");
				else
					eID = -1;
			}
			pStm.close();
			con.close();
		} catch (Exception e)
		{
			eID = -1;
		}
		return eID;
	}

	// eintrag_article f�llen
	public boolean addEintragArticle(int eID, int aID)
	{
		try
		{
			Connection con = getCon();
			PreparedStatement pStm;
			// Eintrag suchen und wenn nicht vorhanden schreiben
			String sql = "SELECT hits FROM eintrag_article WHERE "
					+ "eintragid = ? " + "and articleid = ?";
			pStm = con.prepareStatement(sql);
			pStm.setInt(1, eID);
			pStm.setInt(2, aID);
			ResultSet rs = pStm.executeQuery();
			if (rs.next())
			{
				int hit = rs.getInt("hits") + 1;
				sql = "UPDATE eintrag_article SET hits =  " + hit + " WHERE "
						+ "eintragid = ? " + "AND articleid =  ?";
				pStm = con.prepareStatement(sql);
				pStm.setInt(1, eID);
				pStm.setInt(2, aID);
				pStm.executeUpdate();
			} else
			{
				// Eintrag schreiben
				sql = "INSERT INTO eintrag_article(eintragid, articleid, hits) VALUES(?, ?, ?);";
				pStm = con.prepareStatement(sql);
				pStm.setInt(1, eID);
				pStm.setInt(2, aID);
				pStm.setInt(3, 1);
				pStm.executeUpdate();
			}
			pStm.close();
			con.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// alle Tabellen leeren
	public boolean delAll() throws Exception
	{
//		int iHID = 0;
		Connection con = getCon();
		Statement stmt = con.createStatement();
		PreparedStatement pStm;
		try
		{
			// Eintrag_Article leeren
			String sql = "DELETE FROM eintrag_article;";
			pStm = con.prepareStatement(sql);
			pStm.executeUpdate();
			// Article leeren
			sql = "DELETE FROM article;";
			pStm = con.prepareStatement(sql);
			pStm.executeUpdate();
			// Herausgeber leeren
			sql = "DELETE FROM herausgeber;";
			pStm = con.prepareStatement(sql);
			pStm.executeUpdate();
			// Eintrag leeren
			sql = "DELETE FROM eintrag;";
			pStm = con.prepareStatement(sql);
			pStm.executeUpdate();
			// Tag leeren
			sql = "DELETE FROM tag;";
			pStm = con.prepareStatement(sql);
			pStm.executeUpdate();
		} catch (Exception es)
		{
			es.printStackTrace();
			return false;
		}
		stmt.close();
		con.close();
		return true;
	}

	// Artikel ID ermitteln
	public int getMaxId()
	{
		int maxID = -1;
		try
		{
			Connection con = getCon();
			Statement stmt = con.createStatement();
//			PreparedStatement pStm;
			// Artikel suchen
			String sql = "SELECT max(id) FROM article";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				maxID = rs.getInt("max(id)");
			}
			stmt.close();
			con.close();
			return maxID;
		} catch (Exception e)
		{
			return maxID;
		}
	}
}
